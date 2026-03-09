package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期验证工具 - 可单独执行
 * @author System
 * @date 2024-01-15
 */
public class DateValidator {

    private static final Pattern ENCRYPTED_DATE_PATTERN =
            Pattern.compile("[A-Za-z0-9+/=]{100,}");

    /**
     * 单独执行：验证授权文件
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("用法: java DateValidator <授权文件路径> <私钥文件路径>");
            System.out.println("示例: java DateValidator license.txt license_private.key");
            return;
        }

        try {
            String licenseFile = args[0];
            String privateKeyFile = args[1];

            System.out.println("=== 开始验证授权文件 ===");
            System.out.println("授权文件: " + licenseFile);
            System.out.println("私钥文件: " + privateKeyFile);

            // 1. 读取授权文件
            String licenseContent = new String(Files.readAllBytes(Paths.get(licenseFile)));
            String encryptedExpiryDate = extractEncryptedDate(licenseContent);

            // 2. 读取私钥
            String privateKeyStr = new String(Files.readAllBytes(Paths.get(privateKeyFile))).trim();
            PrivateKey privateKey = RSACryptoUtil.privateKeyFromString(privateKeyStr);

            // 3. 验证日期
            ValidationResult result = validateExpiryDate(encryptedExpiryDate, privateKey);

            // 4. 输出结果
            printValidationResult(result);

        } catch (Exception e) {
            System.err.println("❌ 验证失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 验证到期日期
     */
    public static ValidationResult validateExpiryDate(String encryptedExpiryDate, PrivateKey privateKey) {
        try {
            LocalDate expiryDate = RSACryptoUtil.decryptDate(encryptedExpiryDate, privateKey);
            LocalDate currentDate = LocalDate.now();

            // 判断当前日期是否小于等于到期日期
            boolean isValid = !currentDate.isAfter(expiryDate);
            long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(currentDate, expiryDate);

            return new ValidationResult(isValid, expiryDate, currentDate, daysRemaining);

        } catch (Exception e) {
            return new ValidationResult(false, null, LocalDate.now(), -1);
        }
    }

    /**
     * 从文本中提取加密的日期
     */
    public static String extractEncryptedDate(String text) {
        Matcher matcher = ENCRYPTED_DATE_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new IllegalArgumentException("未找到加密的日期数据");
    }

    /**
     * 打印验证结果
     */
    private static void printValidationResult(ValidationResult result) {
        System.out.println("\n=== 验证结果 ===");
        System.out.println("有效性: " + (result.isValid() ? "✅ 有效" : "❌ 无效"));

        if (result.getExpiryDate() != null) {
            System.out.println("到期日期: " + result.getExpiryDate());
            System.out.println("当前日期: " + result.getCurrentDate());

            if (result.isValid()) {
                if (result.getDaysRemaining() > 0) {
                    System.out.println("剩余天数: " + result.getDaysRemaining() + " 天");
                } else {
                    System.out.println("状态: 今天到期");
                }
            } else {
                System.out.println("已过期: " + (-result.getDaysRemaining()) + " 天");
            }
        } else {
            System.out.println("错误: 无法解析到期日期");
        }
    }
}