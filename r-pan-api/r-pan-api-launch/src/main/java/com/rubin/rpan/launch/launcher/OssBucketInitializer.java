package com.rubin.rpan.launch.launcher;

import com.aliyun.oss.OSSClient;
import com.rubin.rpan.storage.oss.OssClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component(value = "ossBucketInitializer")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.oss.OssStorageProcessor")
public class OssBucketInitializer implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(OssBucketInitializer.class);

    @Autowired
    private OssClientConfig ossClientConfig;

    @Autowired
    @Qualifier(value = "ossClient")
    private OSSClient ossClient;

    @Override
    public void run(String... args) throws Exception {
        boolean bucketExist = ossClient.doesBucketExist(ossClientConfig.getBucketName());
        if (!bucketExist && ossClientConfig.getAutoCreateBucket()) {
            ossClient.createBucket(ossClientConfig.getBucketName());
        }
        if (!bucketExist && !ossClientConfig.getAutoCreateBucket()) {
            throw new RuntimeException("the bucket " + ossClientConfig.getBucketName() + " is not available!");
        }
        log.info("阿里云OSS bucket初始化完成！");
    }

}
