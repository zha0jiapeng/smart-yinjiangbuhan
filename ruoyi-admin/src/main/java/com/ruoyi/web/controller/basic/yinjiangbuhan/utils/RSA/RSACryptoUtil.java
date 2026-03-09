package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * RSA加密工具类
 * @author System
 * @date 2024-01-15
 */
public class RSACryptoUtil {
    
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    
    /**
     * 生成RSA密钥对
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEY_SIZE);
        return keyGen.generateKeyPair();
    }
    
    /**
     * 用公钥加密日期
     */
    public static String encryptDate(LocalDate date, PublicKey publicKey) throws Exception {
        String dateStr = date.format(DateTimeFormatter.ISO_DATE);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        byte[] encryptedBytes = cipher.doFinal(dateStr.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    /**
     * 用私钥解密日期
     */
    public static LocalDate decryptDate(String encryptedDate, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedDate);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        String dateStr = new String(decryptedBytes, StandardCharsets.UTF_8);
        
        return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
    }
    
    /**
     * 将公钥转换为字符串
     */
    public static String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    
    /**
     * 将私钥转换为字符串
     */
    public static String privateKeyToString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    
    /**
     * 从字符串还原公钥
     */
    public static PublicKey publicKeyFromString(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }
    
    /**
     * 从字符串还原私钥
     */
    public static PrivateKey privateKeyFromString(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
}