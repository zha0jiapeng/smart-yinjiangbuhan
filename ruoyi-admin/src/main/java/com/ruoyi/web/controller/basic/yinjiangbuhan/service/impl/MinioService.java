package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.specialWorkerFileBucketName}")
    private String bucketName;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public List<Map<String, Object>> listFilesAndFolders(String prefix) throws Exception {
        List<Map<String, Object>> fileList = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(false) // 只获取当前目录下的文件和文件夹
                            .build());

            for (Result<Item> result : results) {
                Item item = result.get();
                String fullName = item.objectName();
                Map<String, Object> fileInfo = new HashMap<>();
                // 过滤掉当前文件夹本身
                if (fullName.equals(prefix)) {
                    continue;  // 跳过当前文件夹自身
                }
                String displayName = fullName.substring(prefix.length());  // 只获取当前文件名或文件夹名

                fileInfo.put("name", fullName);
                fileInfo.put("displayName", displayName);

                // 判断是否为文件夹
                if (item.objectName().endsWith("/")) {
                    fileInfo.put("type", "folder");
                } else {
                    fileInfo.put("type", "file");
                }
                fileList.add(fileInfo);
            }
        } catch (MinioException e) {
            throw new Exception("Error while listing objects in MinIO", e);
        }
        return fileList;
    }

    // 上传文件到指定的文件夹
    public String uploadFile(MultipartFile file, String folderPath) throws Exception {
        String filename = file.getOriginalFilename();

        // 确保文件夹路径以 "/" 结尾
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }

        // 拼接文件夹路径和文件名
        String filePath = folderPath + filename;

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)  // 文件路径 = 文件夹路径 + 文件名
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while uploading file to MinIO: " + e.getMessage(), e);
        }
        return "File uploaded successfully to " + filePath;
    }


    // 上传文件到指定的文件夹
    public String uploadFileBucketName(MultipartFile file, String folderPath) throws Exception {
        String filename = file.getOriginalFilename();

        // 确保文件夹路径以 "/" 结尾
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }

        // 拼接文件夹路径和文件名
        String filePath = folderPath + filename;

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)  // 文件路径 = 文件夹路径 + 文件名
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while uploading file to MinIO: " + e.getMessage(), e);
        }
        return "http://192.168.1.204:9000/pecialworker-file/" + filePath;
    }


    // 下载文件
    public InputStream downloadFile(String filename) throws Exception {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while downloading file from MinIO", e);
        }
    }

    // 删除文件或文件夹
    public String deleteObject(String objectPath) throws Exception {
        try {
            // 检查路径是否是文件夹，如果是则递归删除
            if (objectPath.endsWith("/")) {
                List<String> objectsToDelete = listAllObjects(objectPath);
                for (String objectName : objectsToDelete) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .build()
                    );
                }
                return "Folder and its contents deleted successfully: " + objectPath;
            } else {
                // 这是一个文件，直接删除
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectPath)
                                .build()
                );
                return "File deleted successfully: " + objectPath;
            }
        } catch (MinioException e) {
            throw new Exception("Error while deleting object in MinIO: " + e.getMessage(), e);
        }
    }


    // 新建文件夹
    public String createFolder(String folderName) throws Exception {
        if (!folderName.endsWith("/")) {
            folderName = folderName + "/";
        }
        try (InputStream emptyContent = new ByteArrayInputStream(new byte[0])) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(folderName)
                            .stream(emptyContent, 0, -1)
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while creating folder in MinIO", e);
        }
        return "Folder created successfully: " + folderName;
    }

    // 重命名文件
    public String renameObject(String oldName, String newName) throws Exception {
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucketName)
                            .source(CopySource.builder()
                                    .bucket(bucketName)
                                    .object(oldName)
                                    .build())
                            .object(newName)
                            .build());

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(oldName)
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while renaming object in MinIO", e);
        }
        return "Object renamed successfully from " + oldName + " to " + newName;
    }

    // 重命名文件夹（递归复制和删除原文件夹）
    public String renameFolder(String oldPrefix, String newPrefix) throws Exception {
        try {
            // 获取文件夹下的所有文件和子文件夹
            List<String> objectsToRename = listAllObjects(oldPrefix);

            // 递归复制文件夹及内容到新路径
            for (String objectName : objectsToRename) {
                String newObjectName = objectName.replaceFirst(oldPrefix, newPrefix);

                // 复制每个文件/文件夹到新路径
                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucketName)
                                .source(CopySource.builder()
                                        .bucket(bucketName)
                                        .object(objectName)
                                        .build())
                                .object(newObjectName)
                                .build()
                );
            }

            // 删除原始文件夹及其内容
            for (String objectName : objectsToRename) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build()
                );
            }

        } catch (MinioException e) {
            System.err.println("Error renaming folder: " + e.getMessage());
            throw new Exception("Error while renaming folder in MinIO", e);
        }

        return "Folder renamed successfully from " + oldPrefix + " to " + newPrefix;
    }

    // 列出某个文件夹下的所有文件和文件夹（递归）
    private List<String> listAllObjects(String prefix) throws Exception {
        List<String> objectList = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(true) // 递归获取所有文件和文件夹
                            .build());

            for (Result<Item> result : results) {
                Item item = result.get();
                objectList.add(item.objectName());
            }
        } catch (MinioException e) {
            throw new Exception("Error while listing objects in MinIO", e);
        }
        return objectList;
    }

    // 移动文件或文件夹
    public String moveObject(String sourcePath, String targetPath) throws Exception {
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucketName)
                            .source(CopySource.builder()
                                    .bucket(bucketName)
                                    .object(sourcePath)
                                    .build())
                            .object(targetPath)
                            .build());

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(sourcePath)
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while moving object in MinIO", e);
        }
        return "Object moved successfully from " + sourcePath + " to " + targetPath;
    }

    // 复制文件或文件夹
    public String copyObject(String sourcePath, String targetPath) throws Exception {
        try {
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucketName)
                            .source(CopySource.builder()
                                    .bucket(bucketName)
                                    .object(sourcePath)
                                    .build())
                            .object(targetPath)
                            .build());
        } catch (MinioException e) {
            throw new Exception("Error while copying object in MinIO", e);
        }
        return "Object copied successfully from " + sourcePath + " to " + targetPath;
    }
}
