package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDate;

/**
 * RSA加密测试类
 *
 * @author System
 * @date 2024-01-15
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 生成指定到期日期的授权文件
        KeyGenerator.generateLicenseForDate(LocalDate.of(2024, 12, 31), "license");


        // 读取文件
        String licenseContent = new String(Files.readAllBytes(Paths.get("license.txt")));
        String privateKeyStr = new String(Files.readAllBytes(Paths.get("license_private.key"))).trim();

        // 验证
        PrivateKey privateKey = RSACryptoUtil.privateKeyFromString(privateKeyStr);
        String encryptedExpiryDate = DateValidator.extractEncryptedDate(licenseContent);

        ValidationResult result = DateValidator.validateExpiryDate(encryptedExpiryDate, privateKey);

        if (result.isValid()) {
            System.out.println("授权有效，剩余天数: " + result.getDaysRemaining());
        } else {
            System.out.println("授权已过期");
        }
    }
}