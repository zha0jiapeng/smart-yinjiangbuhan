package com.rubin.rpan.storage;

import com.rubin.rpan.storage.exception.StorageParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 存储管理器
 */
@Component(value = "storageManager")
public class StorageManager implements StorageProcessor {

    @Autowired
    @Qualifier(value = "storageProcessorSelector")
    private StorageProcessorSelector storageProcessorSelector;

    /**
     * 文件存储
     *
     * @param inputStream 文件输入流
     * @param totalSize   文件大小
     * @param suffix      文件名后缀
     * @return 文件存储路径
     * @throws IOException
     */
    @Override
    public String store(InputStream inputStream, Long totalSize, String suffix) throws IOException {
        return storageProcessorSelector.select().store(inputStream, totalSize, suffix);
    }

    /**
     * 文件分片存储
     *
     * @param inputStream 文件输入流
     * @param identifier  文件唯一标识
     * @param totalChunks 分片总数
     * @param chunkNumber 当前分片下标 从1开始
     * @param totalSize   文件总大小
     * @param chunkSize   分片文件大小
     * @param suffix      文件名称后缀
     * @param suffix      用户ID
     * @return 文件存储路径
     * @throws IOException
     */
    @Override
    public String storeWithChunk(InputStream inputStream, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, Long chunkSize, String suffix, Long userId) throws IOException {
        return storageProcessorSelector.select().storeWithChunk(inputStream, identifier, totalChunks, chunkNumber, totalSize, chunkSize, suffix, userId);
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
        storageProcessorSelector.select().read2OutputStream(filePath, outputStream);
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
        storageProcessorSelector.select().delete(cacheKey, filePath);
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
        return storageProcessorSelector.select().mergeChunks(cacheKey, attachment);
    }

}
