package com.rubin.rpan.services.common.config;

import com.rubin.rpan.services.common.constant.CommonConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

/**
 * 文件系统公用配置类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Component(value = "rPanServicesConfig")
@ConfigurationProperties(prefix = "rpan")
@EnableTransactionManagement
@MapperScan("com.rubin.rpan.services.modules.**.dao")
@ServletComponentScan(basePackages = "com.rubin")
public class RPanServicesConfig {

    /**
     * 分享访问路径前缀
     */
    private String sharePrefix = CommonConstant.DEFAULT_SHARE_PREFIX;

    /**
     * 分片过期时间（天）
     */
    private Integer chunkFileExpirationDays = CommonConstant.ONE_INT;

    public RPanServicesConfig() {
    }

    public String getSharePrefix() {
        return sharePrefix;
    }

    public void setSharePrefix(String sharePrefix) {
        this.sharePrefix = sharePrefix;
    }

    public Integer getChunkFileExpirationDays() {
        return chunkFileExpirationDays;
    }

    public void setChunkFileExpirationDays(Integer chunkFileExpirationDays) {
        this.chunkFileExpirationDays = chunkFileExpirationDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPanServicesConfig that = (RPanServicesConfig) o;
        return Objects.equals(sharePrefix, that.sharePrefix) &&
                Objects.equals(chunkFileExpirationDays, that.chunkFileExpirationDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sharePrefix, chunkFileExpirationDays);
    }

    @Override
    public String toString() {
        return "RPanServicesConfig{" +
                "sharePrefix='" + sharePrefix + '\'' +
                ", chunkFileExpirationDays=" + chunkFileExpirationDays +
                '}';
    }

}
