package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.ssl.TrustAnyHostnameVerifier;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.HutoolImageToBase64;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.*;

/**
 * 设备Controller
 *
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/api/hik/person")
public class HikPersonController extends BaseController {

    @PostMapping("/request")
    public String add(@RequestBody String data) throws Exception {
        //todo 需要对web端进行同步
        System.out.println("接收到的人员信息：" + data);
        byte[] decrypted = Base64.getDecoder().decode(data);
        String jsonString = decrypt(decrypted);
        System.out.println("处理后的人员信息：" + jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);

        // 提取 staff 数组
        JSONArray staffArray = jsonObject.getJSONArray("staff");
        Map<String, String> recurrence = new HashMap<>();
        for (Object value : staffArray) {
            JSONObject staffMember = (JSONObject) value;
            DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
            Map<String, Object> request = new HashMap<>();
            // 添加键值对
            //人员名称
            request.put("personName", staffMember.getString("staffName"));
            //性别
            request.put("gender", "0");
            //所属组织标识
            request.put("orgIndexCode", "073e2fa3-30a2-4caa-aecb-72eae1da921a");
//            request.put("orgIndexCode", staffMember.getString("orgId"));
            //出生日期（非）
//            request.put("birthday", "1990-01-01");
            //手机号（非）
            request.put("phoneNo", staffMember.getString("phone"));
            //邮箱（非）
//            request.put("email", "person1@person.com");
            //证件类型（默认是中国身份证）
            request.put("certificateType", "111");
            //身份证号
            request.put("certificateNo", staffMember.getString("idCardNo"));
            //工号（非）
//            request.put("jobNo", "111111");

            // 创建嵌套的 faces 列表
            List<Map<String, Object>> faces = new ArrayList<>();
            Map<String, Object> faceData = new HashMap<>();
            //人脸图片base64编码图片
            faceData.put("faceData", staffMember.getString("photoBase64"));
            faces.add(faceData);
            // 将 faces 列表添加到主 Map
            request.put("faces", faces);
            System.out.println("人员信息添加入参：" + request);
            String url = doorFunctionApi.personSingleAdd(request);
            System.out.println("身份证号：" + staffMember.getString("idCardNo") + "人员信息添加：" + url);

            recurrence.put(staffMember.getString("idCardNo"), url);
        }

        return recurrence.toString();
    }

    private static final String AES_KEY = "A4lznykDmsB3NyiZ";

    public static byte[] encrypt(String plaintext) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plaintext.getBytes());
    }

    public String decrypt(byte[] ciphertext) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }
}
