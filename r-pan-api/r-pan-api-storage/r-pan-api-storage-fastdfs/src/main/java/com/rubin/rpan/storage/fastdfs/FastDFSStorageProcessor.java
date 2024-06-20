package com.rubin.rpan.storage.fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.DefaultAppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.rubin.rpan.cache.Cache;
import com.rubin.rpan.cache.local.LocalCache;
import com.rubin.rpan.storage.AbstractStorageProcessor;
import com.rubin.rpan.storage.exception.StorageParamException;
import com.rubin.rpan.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component(value = "fastDFSStorageProcessor")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.fastdfs.FastDFSStorageProcessor")
public class FastDFSStorageProcessor extends AbstractStorageProcessor {

    private static final Integer ZERO_INT = 0;
    private static final Integer ONE_INT = 1;
    private static final String SLASH = "/";

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private DefaultAppendFileStorageClient defaultAppendFileStorageClient;

    @Autowired
    private FastDFSClientConfig fastDFSClientConfig;

    private Cache cache = new LocalCache();

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
        StorePath storePath = fastFileStorageClient.uploadFile(fastDFSClientConfig.getGroup(), inputStream, totalSize, FileUtil.getFileExtName(suffix));
        return storePath.getFullPath();
    }

    /**
     * 文件分片存储
     * 注意：此方法不保证并发分片上传不出问题，所以使用此模式，要有序单线程分片上传
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
        String fileExtName = FileUtil.getFileExtName(suffix);
        StorePath storePath;
        String chunkUploadKey = FileUtil.generateChunkKey(identifier, userId);
        if (chunkNumber.equals(ONE_INT)) {
            storePath = defaultAppendFileStorageClient.uploadAppenderFile(fastDFSClientConfig.getGroup(), inputStream, chunkSize, fileExtName);
            cache.put(chunkUploadKey, storePath.getPath());
        } else {
            Long offset;
            if (chunkNumber == totalChunks) {
                offset = totalSize - chunkSize;
            } else {
                offset = chunkNumber * chunkSize;
            }
            defaultAppendFileStorageClient.modifyFile(fastDFSClientConfig.getGroup(), String.valueOf(cache.get(chunkUploadKey)), inputStream, chunkSize, offset);
        }
        String filePath = new StringBuffer(fastDFSClientConfig.getGroup()).append(SLASH).append(cache.get(chunkUploadKey)).toString();
        return filePath;
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
        String group = filePath.substring(ZERO_INT, filePath.indexOf(SLASH));
        String path = filePath.substring(filePath.indexOf(SLASH) + ONE_INT);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 删除文件
     *
     * @param cacheKey 缓存key 可以为null
     * @param filePath 文件路径
     * @throws IOException
     */
    @Override
    public void delete(String cacheKey, String filePath) throws IOException, StorageParamException {
        super.delete(cacheKey, filePath);
        if (StringUtils.isNotBlank(cacheKey)) {
            if (cache.get(cacheKey) != null) {
                cache.delete(cacheKey);
                fastFileStorageClient.deleteFile(filePath);
            }
        } else {
            fastFileStorageClient.deleteFile(filePath);
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
        String filePath = new StringBuffer(fastDFSClientConfig.getGroup()).append(SLASH).append(cache.get(cacheKey)).toString();
        cache.delete(cacheKey);
        return filePath;
    }

}
