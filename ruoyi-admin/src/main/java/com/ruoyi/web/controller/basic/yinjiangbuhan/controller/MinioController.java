package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl.MinioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/minio")
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    // 获取当前目录下的文件和文件夹
    @GetMapping("/list")
    public AjaxResult listFilesAndFolders(@RequestParam(value = "prefix", required = false, defaultValue = "") String prefix) {
        try {
            return AjaxResult.success(minioService.listFilesAndFolders(prefix));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 上传文件接口，允许用户指定文件夹路径
    @PostMapping("/upload")
    public AjaxResult uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderPath", defaultValue = "") String folderPath) {
        try {
            return AjaxResult.success(minioService.uploadFile(file, folderPath));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error uploading file: " + e.getMessage());
        }
    }

    // 下载文件接口
    @GetMapping("/download")
    public void downloadFile(@RequestParam("filename") String filename, HttpServletResponse response) {
        try (InputStream inputStream = minioService.downloadFile(filename)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // 删除文件接口
    @PostMapping("/delete")
    public AjaxResult deleteFile(@RequestParam("path") String path) {
        try {
            return AjaxResult.success(minioService.deleteObject(path));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error deleting file: " + e.getMessage());
        }
    }

    // 创建文件夹接口
    @PostMapping("/create-folder")
    public AjaxResult createFolder(@RequestParam("folderName") String folderName) {
        try {
            return AjaxResult.success(minioService.createFolder(folderName));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error creating folder: " + e.getMessage());
        }
    }

    // 重命名文件或文件夹接口
    @PostMapping("/rename")
    public AjaxResult renameObject(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName) {
        try {
            if(oldName.endsWith("/"))
                return AjaxResult.success(minioService.renameFolder(oldName, newName));
            else
                return AjaxResult.success(minioService.renameObject(oldName, newName));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error renaming object: " + e.getMessage());
        }
    }

    // 移动文件或文件夹接口
    @PostMapping("/move")
    public AjaxResult moveObject(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath) {
        try {
            return AjaxResult.success(minioService.moveObject(sourcePath, targetPath));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error moving object: " + e.getMessage());
        }
    }

    // 复制文件或文件夹接口
    @PostMapping("/copy")
    public AjaxResult copyObject(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath) {
        try {
            return AjaxResult.success(minioService.copyObject(sourcePath, targetPath));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("Error copying object: " + e.getMessage());
        }
    }
}
