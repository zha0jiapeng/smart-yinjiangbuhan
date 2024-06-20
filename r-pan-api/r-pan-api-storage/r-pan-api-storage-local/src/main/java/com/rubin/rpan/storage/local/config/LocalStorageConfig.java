package com.rubin.rpan.storage.local.config;

import com.rubin.rpan.util.FileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 文件基础路径配置类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Component(value = "localStorageConfig")
@ConfigurationProperties(prefix = "rpan.storage.local")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.local.LocalStorageProcessor")
public class LocalStorageConfig {

    /**
     * 实际存放路径前缀
     */
    private String rootFilePath = FileUtil.generateDefaultRootFolderPath();

    /**
     * 临时分片文件存放路径前缀
     */
    private String chunksPath = FileUtil.generateChunksFolderPath(rootFilePath);

    public LocalStorageConfig() {
    }

    public String getRootFilePath() {
        return rootFilePath;
    }

    public void setRootFilePath(String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }

    public String getChunksPath() {
        return chunksPath;
    }

    public void setChunksPath(String chunksPath) {
        this.chunksPath = chunksPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalStorageConfig that = (LocalStorageConfig) o;
        return Objects.equals(rootFilePath, that.rootFilePath) &&
                Objects.equals(chunksPath, that.chunksPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootFilePath, chunksPath);
    }

    @Override
    public String toString() {
        return "LocalStorageConfig{" +
                "rootFilePath='" + rootFilePath + '\'' +
                ", chunksPath='" + chunksPath + '\'' +
                '}';
    }

}
