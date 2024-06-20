package com.rubin.rpan.storage;

import com.rubin.rpan.storage.exception.StorageParamException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 存储处理器模板类
 */
public abstract class AbstractStorageProcessor implements StorageProcessor {

    /**
     * 删除文件
     *
     * @param cacheKey 缓存key 可以为null
     * @param filePath 文件路径
     * @throws IOException
     */
    @Override
    public void delete(String cacheKey, String filePath) throws IOException, StorageParamException {
        if (StringUtils.isBlank(filePath)) {
            throw new StorageParamException("the filePath can't be blank");
        }
    }

}
