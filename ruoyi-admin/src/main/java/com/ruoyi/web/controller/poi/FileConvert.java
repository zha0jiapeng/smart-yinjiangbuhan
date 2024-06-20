package com.ruoyi.web.controller.poi;

import com.amazonaws.util.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

public class FileConvert {

    /**
     * byte 转换为 MultipartFile
     *
     * @param bytes
     * @return
     */
    public static MultipartFile encodeToMultipartFile(byte[] bytes) {
        MultipartFile multipartFile = null;
        InputStream input = null;
        OutputStream outputStream = null;
        File tempFile = null;
        try {
            String path = "";
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) {
                path = "D:" + File.separator + "file" + File.separator;
            } else {
                path = "/home/temp" + File.separator + "file" + File.separator;
            }

            File file = new File(path);
            file.mkdirs();
            path = path + System.currentTimeMillis() + ".png";
            tempFile = new File(path);
            tempFile.createNewFile();

            // 把 byte 转换为 File 文件
            getFileByBytes(bytes, path);

            // 第一个参数 fieldName 就是文件上传的 name, 这里我写的是 uploadFile
            FileItem fileItem = new DiskFileItem("uploadFile", Files.probeContentType(tempFile.toPath()), false, tempFile.getName(), (int) tempFile.length(), tempFile.getParentFile());
            input = new FileInputStream(tempFile);
            outputStream = fileItem.getOutputStream();
            IOUtils.copy(input, outputStream);

            multipartFile = new CommonsMultipartFile(fileItem);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 删除这个 File
        if (tempFile.exists()) {
            tempFile.delete();
        }

        return multipartFile;
    }

    /**
     * byte [] 转换为 File
     *
     * @param bytes
     * @param filePath
     */
    public static void getFileByBytes(byte[] bytes, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath);

            //输出流
            fos = new FileOutputStream(file);

            //缓冲流
            bos = new BufferedOutputStream(fos);

            //将字节数组写出
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
