package com.ruoyi.web.core.config;

import com.ruoyi.common.core.domain.AjaxResult;
import io.minio.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class MinioConfig {

    private MinioClient minioClient;


    /**
     * 访问地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 默认存储桶
     */
    @Value("${minio.carAccessBucketName}")
    private String carAccessBucketName;

    @Value("${minio.peopleAccessBucketName}")
    private String peopleAccessBucketName;

    @Value("${minio.fileControlBucketName}")
    private String fileControlBucketName;

    @Value("${minio.specialWorkerFileBucketName}")
    private String specialWorkerFileBucketName;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }


    public boolean createBucket(String bucketName) {
        try {
            // 检查 bucket 是否已存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                // 创建 bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created successfully.");
                setPublicBucketPolicy(bucketName);
                return true;
            } else {
                System.out.println("Bucket already exists.");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return false;
        }
    }



    private void setPublicBucketPolicy(String bucketName) {
        try {
            String policy = String.format(
                    "{\n" +
                            "   \"Version\":\"2012-10-17\",\n" +
                            "   \"Statement\":[\n" +
                            "      {\n" +
                            "         \"Effect\":\"Allow\",\n" +
                            "         \"Principal\":\"*\",\n" +
                            "         \"Action\":[\"s3:GetObject\"],\n" +
                            "         \"Resource\":[\"arn:aws:s3:::%s/*\"]\n" +
                            "      }\n" +
                            "   ]\n" +
                            "}", bucketName);

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build()
            );
        } catch (Exception e) {
            System.err.println("Failed to set bucket policy: " + e.getMessage());
        }
    }



    /**
     * 上传文件到 MinIO
     * @param file 文件对象
     * @return 文件 URL
     */
    public AjaxResult uploadFile(MultipartFile file, String bucketName) {
        System.out.println("桶名："+bucketName);
        boolean bucket = createBucket(bucketName);
        if(!bucket) return AjaxResult.error("创建bucket失败");
        String fileName = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            Map<String,Object> map = new HashMap();
            map.put("fileUrl", endpoint + "/" + bucketName + "/" + fileName);
            map.put("fileName",fileName );
            return AjaxResult.success("上传成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("上传文件失败");
        }
    }
}