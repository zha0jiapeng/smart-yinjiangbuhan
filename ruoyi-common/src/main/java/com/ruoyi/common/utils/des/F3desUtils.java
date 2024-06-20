package com.ruoyi.common.utils.des;



import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Random;

public class F3desUtils {
    private static final String Algorithm = "DESede";
    private static final char[] BASE = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final String key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTC4P+UJuVaigM\n" +
            "wZnYE/K4Zy8xSoKIX6WRn60IyZY9HLcS8jSQlUQOj4fvcTfNohLxuLsqjimpP5bK\n" +
            "l34UHNTmALgiGFImdvWNoaM4+00F25bwnl4/O7Qej1Mg/bP5JocpwVpAZjr5Hm8r\n" +
            "CAXcoMWqAAo1X8XbcwZ5Bv6fe9PGqH/UdduzfSjg3vIwLQCQExKg7kLB0iBvpV/r\n" +
            "znNfvi4SXBYvRHJT+dO5f5pMKlfSa2GbnSc8IyTOevTWAlDsMvMj1o3QhOhlXuFr\n" +
            "/r28Lc6fWXdFGnCbDD1xhtGZNfYI1plyBxBUM3blY+X6xrwon0JKv6d2TE0fDRag\n" +
            "drQmdlO7AgMBAAECggEAMw6TJUB9E122SLfBr1vMGb6k1SjwgWuaU6usofabCTfc\n" +
            "NpIwE1Z5O4iP22apmG8AZEhLx5TTEWYX5fVWhCkhfG9qHJ2DqliXOWeEZHGvhZ5F\n" +
            "WvsFVAjun4IPG2klVD507q4fVnslA0XQoTl0qZafm2wdDCKabGfchgUiLpU3e9Sw\n" +
            "/rmVlIi2SzGrO6+zwMfE/qUzFqDj6ahfiSGnmUTMISM3xeE8zvQxM8ybvYaH5tHt\n" +
            "SI1hMd/frYw0iFMXswZsfZumPWk6YoEtxQhN3nRitj2ahyvrQjLOj4KLHcXC8NCn\n" +
            "cyea2JYyuWB/Idk2ApaAqM68dRi0qsJ/pOoB9YX6oQKBgQD+2gy/7fIbiublgSwm\n" +
            "p9YwERqE6R3C5ctRXTMdWnhfvvlYQpH+rYg1LPKM5QeDwlgQXBHUx+M8b06AfdDl\n" +
            "hTwrPJ6tIG6TRiMTvcg9anGPwbeHbhBV+8EvuiMWbEj+5tpEZqgiBU71zzntxzFW\n" +
            "nOG1VMpln1VQQ8/RVeyVFigm8QKBgQDT/vA3TJo7f+rujH/qasW+sbLvwfBGkc6r\n" +
            "qzcKFe3mIF+1NDmiQvM9UOSzuVlIdLwDNI3UL4vBFLmOUL1PmKkPbk/0Tf2hxd7o\n" +
            "wSa+eGr2V61z8+8mGbKKDdn6C0+bn0mnFkTKO/PGDez+J2dZgQLQes26CSD41BV2\n" +
            "pNG7exDdawKBgGQQMVlaj+kIjMgoA7HZzFtrG8J71Vek6kO0GFG6J/FQBo8E9FXO\n" +
            "xOA8GbAAbQbq07D4tXeaVDpyaZxByjeULJ+9WYAYb9Ibg8JIjcqvdI9BPFJMgNWB\n" +
            "18RpjUVMlwyej6yzsehJtUsGZnRZ9aFQT77m3+EuwkyVk49Z8V5IKNPBAoGBAJyw\n" +
            "Jm5JAQoPLt5oHDdv+X5LqvzItEYEiOObZc/fi3l23iZ5VkvW/h+IxbEMwq/qD/iy\n" +
            "hE2bYskv/0Hv7SyCohoUp6Xjax6BuJMCCBfpuu0eusYkOtNHVQ3y1PSaHZnhbre/\n" +
            "ntJ242O0cUEccKfX0fOzgJELJEKlxsNtaNMQT9PHAoGBAMKcYK0rZxJB9VKhKF+o\n" +
            "+NtziBC2hlW3mVl87/yGM6oMnGpgaHd5PDJRhjAJ/xF2fU/QNQRIusZUL8EQl4ik\n" +
            "rd2nEf1BKxYumyWuo/O8yFU/vuqbd/y2ezkZbQi7svABabvmJ5lUdSxf1icSn1YS\n" +
            "KLOWm7c54iFIDrVyOs/2e9U/";

    /**
     * 加密
     *
     * @param data   待加密明文
     * @param strKey 传入密钥：16位数字或字母，大小写敏感（密钥长度由项目要求的，非固定长度）
     * @return 结果
     * @throws Exception 异常信息
     */
    public static String encrypt(String data, String strKey) throws Exception {
        data = data + "        ";
        byte[] bMsg = data.getBytes("gbk");
        int l = (bMsg.length / 16 + 1) * 16;
        byte[] btMsg = Arrays.copyOf(bMsg, l);
        byte[] digestOfPassword = strKey.getBytes();
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;

        for (int var8 = 16; j < 8; keyBytes[var8++] = keyBytes[j++]) {
            ;
        }

        SecretKey key = new SecretKeySpec(keyBytes, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, key);
        String rtn = StringUtils.byte2hex(c1.doFinal(btMsg));
        return rtn.substring(0, (bMsg.length / 8 + 1) * 16);
    }

    /**
     * 解密
     *
     * @param message 密文
     * @param strKey  传入密钥：16位数字或字母，大小写敏感
     * @return 结果
     * @throws Exception 异常信息
     */
    public static String decrypt(String message, String strKey) throws Exception {
        message = message + getAdd(message.length(), strKey);
        byte[] digestOfPassword = strKey.getBytes();
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;

        for (int var5 = 16; j < 8; keyBytes[var5++] = keyBytes[j++]) {
            ;
        }

        SecretKey key = new SecretKeySpec(keyBytes, Algorithm);
        Cipher decipher = Cipher.getInstance(Algorithm);
        decipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = decipher.doFinal(StringUtils.hex2byte(message));
        return new String(plainText, "gbk");
    }

    private static String getAdd(int length, String strKey) throws Exception {
        byte[] btMsg = new byte[length / 2];
        byte[] digestOfPassword = strKey.getBytes("utf-8");
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;

        for (int var6 = 16; j < 8; keyBytes[var6++] = keyBytes[j++]) {
            ;
        }

        SecretKey key = new SecretKeySpec(keyBytes, Algorithm);
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, key);
        String rtn = StringUtils.byte2hex(c1.doFinal(btMsg));
        return rtn.substring(length);
    }

    /**
     * 生成key
     *
     * @param len 长度
     * @return 结果
     */
    public static String newKey(int len) {
        Random rd = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < len; ++i) {
            sb.append(BASE[Math.abs(rd.nextInt()) % 62]);
        }
        return sb.toString();
    }

}
