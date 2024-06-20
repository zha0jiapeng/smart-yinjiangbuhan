package com.rubin.rpan.storage.local;

import com.rubin.rpan.cache.Cache;
import com.rubin.rpan.cache.local.LocalCache;
import com.rubin.rpan.storage.AbstractStorageProcessor;
import com.rubin.rpan.storage.exception.StorageParamException;
import com.rubin.rpan.storage.local.config.LocalStorageConfig;
import com.rubin.rpan.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "localStorageProcessor")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.local.LocalStorageProcessor")
public class LocalStorageProcessor extends AbstractStorageProcessor {

    @Autowired
    @Qualifier(value = "localStorageConfig")
    private LocalStorageConfig localStorageConfig;

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
        String filePath = FileUtil.generateFilePath(localStorageConfig.getRootFilePath(), suffix);
        FileUtil.writeStreamToFile(inputStream, new File(filePath), totalSize);
        return filePath;
    }

    /**
     * 文件分片存储
     * 注意：此方法可以保证并发分片上传不出问题
     *
     * @param inputStream 文件输入流
     * @param identifier  文件唯一标识
     * @param totalChunks 分片总数
     * @param chunkNumber 当前分片下标 从1开始
     * @param totalSize   文件总大小
     * @param chunkSize   分片文件大小
     * @param suffix      文件名称后缀
     * @param userId      用户ID
     * @return 分片存储路径
     * @throws IOException
     */
    @Override
    public String storeWithChunk(InputStream inputStream, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, Long chunkSize, String suffix, Long userId) throws IOException {
        String chunkFilePath = FileUtil.generateChunkFilePath(localStorageConfig.getChunksPath(), identifier, chunkNumber);
        FileUtil.writeStreamToFile(inputStream, new File(chunkFilePath), chunkSize);
        cache.put(FileUtil.generateChunkKey(identifier, userId), suffix);
        return chunkFilePath;
    }

    /**
     * 读取文件为输入流
     *
     * @param filePath     文件路径
     * @param outputStream 输出流
     * @throws IOException
     */
    @Override
    public void read2OutputStream(String filePath, OutputStream outputStream) throws IOException {
        File file = new File(filePath);
        FileUtil.writeFileToStream(new FileInputStream(file), outputStream, file.length());
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
                cache.delete(cacheKey);
            }
        }
        FileUtil.delete(filePath);
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
        List<String> chunkFilePaths = (List<String>) attachment;
        String filePath = FileUtil.generateFilePath(localStorageConfig.getRootFilePath(), String.valueOf(cache.get(cacheKey)));
        FileUtil.createFile(filePath);
        List<File> chunkFiles = chunkFilePaths.stream()
                .map(chunkFilePath -> new File(chunkFilePath))
                .sorted((chunkFile1, chunkFile2) -> {
                    String chunkFile1Name = chunkFile1.getName();
                    String chunkFile2Name = chunkFile2.getName();
                    return FileUtil.resolveChunkFileNumber(chunkFile1Name).compareTo(FileUtil.resolveChunkFileNumber(chunkFile2Name));
                })
                .collect(Collectors.toList());
        for (File chunkFile : chunkFiles) {
            FileUtil.appendWrite(Paths.get(filePath), chunkFile.toPath());
            FileUtil.delete(chunkFile.getAbsolutePath());
        }
        cache.delete(cacheKey);
        return filePath;
    }

}
