package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.time.LocalDate;

/**
 * 密钥生成工具 - 可单独执行
 * @author System
 * @date 2024-01-15
 */
public class KeyGenerator {

    /**
     * 单独执行：生成密钥对和授权文件
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== 开始生成密钥对和授权文件 ===");

            // 设置到期日期（例如：2024年12月31日）
            LocalDate expiryDate = LocalDate.of(2024, 12, 31);
            generateLicenseForDate(expiryDate, "/home/user/project/license/license");

        } catch (Exception e) {
            System.err.println("❌ 生成失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 生成指定到期日期的授权文件
     */
    public static void generateLicenseForDate(LocalDate expiryDate, String outputPath) throws Exception {
        KeyPair keyPair = RSACryptoUtil.generateKeyPair();
        String publicKeyStr = RSACryptoUtil.publicKeyToString(keyPair.getPublic());
        String privateKeyStr = RSACryptoUtil.privateKeyToString(keyPair.getPrivate());

        // 加密到期日期
        String encryptedExpiryDate = RSACryptoUtil.encryptDate(expiryDate, keyPair.getPublic());
        String licenseContent = generateLicenseContent(encryptedExpiryDate, publicKeyStr, expiryDate);

        // 创建目录（如果不存在）
        Files.createDirectories(Paths.get("/home/user/project/license"));
        Files.createDirectories(Paths.get("/home/user/project/license/key"));
        
        Files.write(Paths.get(outputPath + ".txt"), licenseContent.getBytes());
        Files.write(Paths.get("/home/user/project/license/key/license_private.key"), privateKeyStr.getBytes());

        System.out.println("✅ 授权文件生成成功: " + outputPath + ".txt");
        System.out.println("✅ 私钥文件生成成功: /home/user/project/license/key/license_private.key");
        System.out.println("📅 到期日期: " + expiryDate);
        System.out.println("🔐 加密后的到期日期: " + encryptedExpiryDate);
    }

    private static String generateLicenseContent(String encryptedExpiryDate, String publicKey, LocalDate expiryDate) {
        // 只保留加密的到期日期，去除所有明文信息以提高安全性
        return encryptedExpiryDate;
    }

    /**
     * 生成从今天开始指定有效天数的授权文件
     */
    public static void generateLicenseForDays(int validDays, String outputPath) throws Exception {
        LocalDate expiryDate = LocalDate.now().plusDays(validDays);
        generateLicenseForDate(expiryDate, outputPath);
    }
}