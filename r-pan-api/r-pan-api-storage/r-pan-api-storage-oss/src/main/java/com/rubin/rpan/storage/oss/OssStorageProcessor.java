package com.rubin.rpan.storage.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.rubin.rpan.cache.Cache;
import com.rubin.rpan.cache.local.LocalCache;
import com.rubin.rpan.storage.AbstractStorageProcessor;
import com.rubin.rpan.storage.exception.StorageParamException;
import com.rubin.rpan.util.DateUtil;
import com.rubin.rpan.util.FileUtil;
import com.rubin.rpan.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Component(value = "ossStorageProcessor")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.oss.OssStorageProcessor")
public class OssStorageProcessor extends AbstractStorageProcessor {

    private static final String SLASH = "/";
    private static final String TAG = "PartETag";
    private static final Integer TEN_THOUSAND_INT = 10000;
    private static final Integer ONE_INT = 1;

    @Autowired
    @Qualifier(value = "ossClient")
    private OSSClient ossClient;

    @Autowired
    private OssClientConfig ossClientConfig;

    private Cache cache = new LocalCache();

    /**
     * 分片上传缓存实体
     */
    static class ChunkUploadEntity {

        private String uploadId;
        private String filePath;
        private CopyOnWriteArrayList partETagKeys;

        public ChunkUploadEntity() {
        }

        public ChunkUploadEntity(String uploadId, String filePath, CopyOnWriteArrayList partETagKeys) {
            this.uploadId = uploadId;
            this.filePath = filePath;
            this.partETagKeys = partETagKeys;
        }

        public String getUploadId() {
            return uploadId;
        }

        public void setUploadId(String uploadId) {
            this.uploadId = uploadId;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public CopyOnWriteArrayList getPartETagKeys() {
            return partETagKeys;
        }

        public void setPartETagKeys(CopyOnWriteArrayList partETagKeys) {
            this.partETagKeys = partETagKeys;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChunkUploadEntity that = (ChunkUploadEntity) o;
            return Objects.equals(uploadId, that.uploadId) &&
                    Objects.equals(filePath, that.filePath) &&
                    Objects.equals(partETagKeys, that.partETagKeys);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uploadId, filePath, partETagKeys);
        }

        @Override
        public String toString() {
            return "ChunkUploadEntity{" +
                    "uploadId='" + uploadId + '\'' +
                    ", filePath='" + filePath + '\'' +
                    ", partETagKeys=" + partETagKeys +
                    '}';
        }

    }

    /**
     * 文件存储
     *
     * @param inputStream 文件输入流
     * @param totalSize   文件大小
     * @param suffix      文件原名后缀
     * @return 文件存储路径
     * @throws IOException
     */
    @Override
    public String store(InputStream inputStream, Long totalSize, String suffix) throws IOException {
        String filePath = getFilePath(suffix);
        ossClient.putObject(ossClientConfig.getBucketName(), filePath, inputStream);
        return filePath;
    }

    /**
     * 文件分片存储
     * 注意：分片上传支持并发随机上传分片
     *
     * @param inputStream 文件输入流
     * @param identifier  文件唯一标识
     * @param totalChunks 分片总数
     * @param chunkNumber 当前分片下标 从1开始
     * @param totalSize   文件总大小
     * @param chunkSize   分片文件大小
     * @param suffix      文件名称后缀
     * @param userId      用户ID
     * @return 文件存储路径
     * @throws IOException
     */
    @Override
    public String storeWithChunk(InputStream inputStream, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, Long chunkSize, String suffix, Long userId) throws IOException {
        if (totalChunks > TEN_THOUSAND_INT) {
            throw new RuntimeException("分片数超过了限制，分片数不得大于" + TEN_THOUSAND_INT);
        }
        // 保证只有一个线程做初始化工作
        String chunkUploadKey = FileUtil.generateChunkKey(identifier, userId);
        synchronized (chunkUploadKey) {
            if (Objects.isNull(cache.get(chunkUploadKey))) {
                InitiateMultipartUpload(suffix, chunkUploadKey);
            }
        }
        doUploadMultipart(inputStream, chunkUploadKey, chunkNumber, chunkSize);
        return ((ChunkUploadEntity) cache.get(chunkUploadKey)).getFilePath();
    }

    /**
     * 读取文件进输出流
     *
     * @param filePath     文件路径
     * @param outputStream 输出流
     * @throws IOException
     */
    @Override
    public void read2OutputStream(String filePath, OutputStream outputStream) throws IOException {
        OSSObject ossObject = ossClient.getObject(ossClientConfig.getBucketName(), filePath);
        FileUtil.writeStreamToStreamNormal(ossObject.getObjectContent(), outputStream);
    }

    /**
     * 删除文件
     *
     * @param cacheKey 缓存key
     * @param filePath 文件路径
     * @throws IOException
     */
    @Override
    public void delete(String cacheKey, String filePath) throws IOException, StorageParamException {
        super.delete(cacheKey, filePath);
        if (StringUtils.isNotBlank(cacheKey)) {
            if (cache.get(cacheKey) != null) {
                ChunkUploadEntity chunkUploadEntity = (ChunkUploadEntity) cache.get(cacheKey);
                // 取消分片上传
                AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(ossClientConfig.getBucketName(), chunkUploadEntity.getFilePath(), chunkUploadEntity.getUploadId());
                ossClient.abortMultipartUpload(abortMultipartUploadRequest);
                cache.delete(cacheKey);
            }
        } else {
            ossClient.deleteObject(ossClientConfig.getBucketName(), filePath);
        }
    }

    /**
     * 分片合并
     *
     * @param cacheKey   缓存key
     * @param attachment 附加信息
     * @return
     */
    @Override
    public String mergeChunks(String cacheKey, Object attachment) throws IOException {
        ChunkUploadEntity chunkUploadEntity = (ChunkUploadEntity) cache.get(cacheKey);
        String filePath = chunkUploadEntity.getFilePath(),
                uploadId = chunkUploadEntity.getUploadId();
        CopyOnWriteArrayList partETags = chunkUploadEntity.getPartETagKeys();
        // 在执行完成分片上传操作时，需要提供所有有效的partETags
        // OSS收到提交的partETags后，会逐一验证每个分片的有效性
        // 当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(ossClientConfig.getBucketName(), filePath, uploadId, partETags);
        // 完成上传
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        cache.delete(cacheKey);
        return filePath;
    }

    /*************************************************************私有*************************************************************/

    /**
     * 生成文件存储路径
     *
     * @param suffix
     * @return
     */
    private String getFilePath(String suffix) {
        return new StringBuffer(DateUtil.getYear())
                .append(SLASH)
                .append(DateUtil.getMonth())
                .append(SLASH)
                .append(DateUtil.getDay())
                .append(SLASH)
                .append(UUIDUtil.getUUID())
                .append(suffix)
                .toString();
    }

    /**
     * 生成文件路径缓存key
     *
     * @param identifier
     * @param filename
     * @return
     */
    private String getFilePathKey(String identifier, String filename) {
        return new StringBuffer(identifier).append(SLASH).append(filename).toString();
    }

    /**
     * 生成tag的key
     *
     * @param md5
     * @return
     */
    private String getPartETagKey(String md5) {
        return new StringBuffer(md5).append(SLASH).append(TAG).toString();
    }

    /**
     * 初始化分片请求 缓存分片id
     *
     * @param suffix
     * @param chunkUploadKey
     */
    private void InitiateMultipartUpload(String suffix, String chunkUploadKey) {
        String filePath = getFilePath(suffix);
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(ossClientConfig.getBucketName(), filePath);
        InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient.initiateMultipartUpload(request);
        ChunkUploadEntity chunkUploadEntity = new ChunkUploadEntity(initiateMultipartUploadResult.getUploadId(), filePath, new CopyOnWriteArrayList<>());
        cache.put(chunkUploadKey, chunkUploadEntity);
    }

    /**
     * 上传分片文件
     *
     * @param inputStream
     * @param chunkUploadKey
     * @param chunkNumber
     * @param chunkSize
     */
    private void doUploadMultipart(InputStream inputStream, String chunkUploadKey, Integer chunkNumber, Long chunkSize) {
        ChunkUploadEntity chunkUploadEntity = (ChunkUploadEntity) cache.get(chunkUploadKey);
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(ossClientConfig.getBucketName());
        uploadPartRequest.setKey(chunkUploadEntity.getFilePath());
        uploadPartRequest.setUploadId(chunkUploadEntity.getUploadId());
        uploadPartRequest.setInputStream(inputStream);
        // 设置分片大小，除了最后一个分片没有大小限制，其他的分片最小为100 KB
        uploadPartRequest.setPartSize(chunkSize);
        // 设置分片号，每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码
        uploadPartRequest.setPartNumber(chunkNumber);
        // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件
        UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
        // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中
        chunkUploadEntity.getPartETagKeys().add(uploadPartResult.getPartETag());
    }

}
