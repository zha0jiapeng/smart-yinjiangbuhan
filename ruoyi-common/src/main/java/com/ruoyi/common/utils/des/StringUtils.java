package com.ruoyi.common.utils.des;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static String isChineseToUniCode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (StringUtils.isChinese(c)) {
                sb.append(cnToUnicode(Character.toString(c)));
            } else {
                sb.append(Character.toString(c));
            }
        }
        return sb.toString();
    }

    public static String isUniCodeToChinese(String unicode) {
        List<String> list = new ArrayList<>();
        String reg = "\\\\u[0-9,a-f,A-F]{4}";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(unicode);
        while (m.find()) {
            list.add(m.group());
        }
        for (int i = 0, j = 2; i < list.size(); i++) {
            String code = list.get(i).substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            unicode = unicode.replace(list.get(i), String.valueOf(ch));
        }
        return unicode;
    }

    public static String unicodeToCn(String unicode) {
        String[] str = unicode.split("\\\\u");
        StringBuilder returnStr = new StringBuilder();
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < str.length; i++) {
            returnStr.append((char) Integer.valueOf(str[i], 16).intValue());
        }
        return returnStr.toString();
    }

    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder returnStr = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            returnStr.append("\\u").append(Integer.toString(chars[i], 16));
        }
        return returnStr.toString();
    }

    public static String getRandomString(int arg0) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arg0; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0);
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1);
        return (byte) (b0 | b1);
    }

    public static byte[] hexStr2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; ++i) {
            int m = i * 2 + 1;
            int n = m + 1;
            ret[i] = StringUtils.uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    public static String toHex(byte[] data, String digits) {
        return toHex(data, data.length, digits);
    }

    public static String toHex(byte[] data, int length, String digits) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i != length; ++i) {
            int v = data[i] & 255;
            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 15));
        }
        return buf.toString();
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static byte[] hex2byte(String s) {
        int l = s.length() / 2;
        byte[] b = new byte[l];
        for (int i = 0; i < l; ++i) {
            int m = i * 2 + 1;
            int n = m + 1;
            b[i] = StringUtils.uniteBytes(s.substring(i * 2, m), s.substring(m, n));
        }
        return b;
    }
}
