package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HutoolImageToBase64 {

    /**
     * 转换动态图片URL为Base64（使用Hutool）
     * @param imageUrl 图片URL
     * @param timeout 超时时间（毫秒）
     * @return Base64字符串
     */
    public static String convertToBase64(String imageUrl, int timeout) {
        try {
            // 使用HttpRequest设置超时
            HttpResponse response = HttpRequest.get(imageUrl)
                    .timeout(timeout)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .setFollowRedirects(true)
                    .setSSLSocketFactory(createTrustAllSSLSocketFactory())
                    .setHostnameVerifier(createTrustAllHostnameVerifier())
                    .execute();

            if (!response.isOk()) {
                throw new RuntimeException("HTTP请求失败: " + response.getStatus() + ", URL: " + imageUrl);
            }

            byte[] imageData = response.bodyBytes();
            return Base64.encode(imageData);

        } catch (Exception e) {
            throw new RuntimeException("图片转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 转换动态图片URL为Data URL
     * @param imageUrl 图片URL
     * @param timeout 超时时间（毫秒）
     * @return Data URL字符串
     */
    public static String convertToDataUrl(String imageUrl, int timeout) {
        try {
            HttpResponse response = HttpRequest.get(imageUrl)
                    .timeout(timeout)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .setFollowRedirects(true)
                    .setSSLSocketFactory(createTrustAllSSLSocketFactory())
                    .setHostnameVerifier(createTrustAllHostnameVerifier())
                    .execute();

            if (!response.isOk()) {
                throw new RuntimeException("HTTP请求失败: " + response.getStatus() + ", URL: " + imageUrl);
            }

            byte[] imageData = response.bodyBytes();
            String base64 = Base64.encode(imageData);

            // 获取图片类型
            String imageType = detectImageType(imageUrl, imageData);

            return "data:image/" + imageType + ";base64," + base64;

        } catch (Exception e) {
            throw new RuntimeException("图片转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 带自定义请求头的转换方法
     */
    public static String convertWithHeaders(String imageUrl, int timeout) {
        try {
            HttpResponse response = HttpRequest.get(imageUrl)
                    .timeout(timeout)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .setFollowRedirects(true)
                    .setSSLSocketFactory(createTrustAllSSLSocketFactory())
                    .setHostnameVerifier(createTrustAllHostnameVerifier())
                    .execute();

            if (!response.isOk()) {
                throw new RuntimeException("HTTP请求失败: " + response.getStatus() + ", URL: " + imageUrl);
            }

            byte[] imageData = response.bodyBytes();
            return Base64.encode(imageData);

        } catch (Exception e) {
            throw new RuntimeException("图片转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 简单的转换方法（使用HttpUtil，但无法设置超时）
     * 仅适用于不需要超时控制的场景
     */
    public static String convertSimple(String imageUrl) {
        try {
            byte[] imageData = HttpUtil.downloadBytes(imageUrl);
            return Base64.encode(imageData);
        } catch (Exception e) {
            throw new RuntimeException("图片转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 检测图片类型
     */
    private static String detectImageType(String imageUrl, byte[] imageData) {
        // 尝试从URL中推断类型
        if (imageUrl.contains("jpeg") || imageUrl.contains("jpg")) {
            return "jpeg";
        } else if (imageUrl.contains("png")) {
            return "png";
        } else if (imageUrl.contains("gif")) {
            return "gif";
        }

        // 通过魔术数字判断图片类型
        if (imageData.length > 4) {
            // JPEG: FF D8 FF
            if ((imageData[0] & 0xFF) == 0xFF && (imageData[1] & 0xFF) == 0xD8) {
                return "jpeg";
            }
            // PNG: 89 50 4E 47
            if ((imageData[0] & 0xFF) == 0x89 && (imageData[1] & 0xFF) == 0x50 &&
                    (imageData[2] & 0xFF) == 0x4E && (imageData[3] & 0xFF) == 0x47) {
                return "png";
            }
            // GIF: 47 49 46 38
            if ((imageData[0] & 0xFF) == 0x47 && (imageData[1] & 0xFF) == 0x49 &&
                    (imageData[2] & 0xFF) == 0x46 && (imageData[3] & 0xFF) == 0x38) {
                return "gif";
            }
        }

        return "jpeg"; // 默认类型
    }

    /**
     * 创建信任所有SSL证书的SSLSocketFactory
     */
    private static SSLSocketFactory createTrustAllSSLSocketFactory() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // 信任所有客户端证书
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // 信任所有服务器证书
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("创建SSL上下文失败", e);
        }
    }

    /**
     * 创建信任所有主机名的HostnameVerifier
     */
    private static HostnameVerifier createTrustAllHostnameVerifier() {
        return (hostname, session) -> true;
    }

    /**
     * 下载图片到指定目录并转换为Base64
     * @param imageUrl 图片URL
     * @param downloadDir 下载目录
     * @param timeout 超时时间（毫秒）
     * @return Base64字符串
     */
    public static String downloadAndConvertToBase64(String imageUrl, String downloadDir, int timeout) {
        try {
            // 确保下载目录存在
            File dir = new File(downloadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 下载图片 - 简化配置，只关闭SSL验证
            HttpResponse response = HttpRequest.get(imageUrl)
                    .timeout(timeout)
                    .setFollowRedirects(true)
                    .setSSLSocketFactory(createTrustAllSSLSocketFactory())
                    .setHostnameVerifier(createTrustAllHostnameVerifier())
                    .execute();

            if (!response.isOk()) {
                throw new RuntimeException("HTTP请求失败: " + response.getStatus() + ", URL: " + imageUrl);
            }

            byte[] imageData = response.bodyBytes();
            
            // 检测图片类型并确定文件扩展名
            String imageType = detectImageType(imageUrl, imageData);
            String fileName = "image." + imageType;
            String filePath = downloadDir + File.separator + fileName;

            // 保存图片到本地
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageData);
            }

            // 转换为Base64
            String base64 = Base64.encode(imageData);
            
            System.out.println("图片已下载到: " + filePath);
            return base64;

        } catch (IOException e) {
            throw new RuntimeException("文件操作失败: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("图片下载和转换失败: " + e.getMessage(), e);
        }
    }

    /**
     * 下载图片到指定目录并转换为Base64（使用默认超时时间）
     * @param imageUrl 图片URL
     * @param downloadDir 下载目录
     * @return Base64字符串
     */
    public static String downloadAndConvertToBase64(String imageUrl, String downloadDir) {
        return downloadAndConvertToBase64(imageUrl, downloadDir, 30000); // 默认30秒超时
    }
}