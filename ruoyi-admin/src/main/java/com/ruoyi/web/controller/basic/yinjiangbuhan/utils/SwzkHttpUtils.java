package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.DateValidator;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.RSACryptoUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Map;

@Slf4j
@Component
public class SwzkHttpUtils {

    public static String pushIOT(Map param) {
        if (!checkLicenseValid()){
            return "授权到期";
        }
        param.put("bidCode", "YJBH-SSZGX_BD-SG-205"); //土建4标
        log.info("push tcp swzk:{}", JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        //String s = TcpClientService.sendTcpRequest(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue));
        HttpResponse execute = HttpRequest.post("http://192.168.1.205:8089/receive/pushIOT")
                .body(JSON.toJSONString(param, SerializerFeature.WriteMapNullValue), "application/json").execute();
        log.info("push tcp swzk response:{}", execute.body());
        log.info("push tcp swzk response value:{}", execute.body() + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        return execute.body();
    }

    /**
     * 检查授权是否有效
     * @return true-有效，false-无效或到期
     */
    private static boolean checkLicenseValid() {
        try {
            // 读取文件
            String licenseContent = new String(Files.readAllBytes(Paths.get("/home/user/project/license/license.txt")));
            String privateKeyStr = new String(Files.readAllBytes(Paths.get("/home/user/project/license/key/license_private.key"))).trim();

            // 验证
            PrivateKey privateKey = RSACryptoUtil.privateKeyFromString(privateKeyStr);
            String encryptedExpiryDate = DateValidator.extractEncryptedDate(licenseContent);

            ValidationResult result = DateValidator.validateExpiryDate(encryptedExpiryDate, privateKey);

            if (result.isValid()) {
                return true;
            } else {
                log.warn("授权已过期");
                return false;
            }
        } catch (Exception e) {
            log.error("授权验证失败: {}", e.getMessage());
            return false;
        }
    }

    public static String pushDevIOT(Map param) {
        if (!checkLicenseValid()){
            return "授权到期";
        }
        param.put("bidCode", "YJBH-SSZGX_BD-SG-205"); //土建4标
        log.info("push tcp swzk:{}", JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        //String s = TcpClientService.sendTcpRequest(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue));
        HttpResponse execute = HttpRequest.post("http://oa.sntsoft.com:8089/receive/pushIOT")
                .body(JSON.toJSONString(param, SerializerFeature.WriteMapNullValue), "application/json").execute();
        log.info("push tcp swzk response:{}", execute.body());
        log.info("pushDevIOT tcp swzk response value:{}", execute.body() + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        return execute.body();
    }


    //管片厂数据
    public static String pushGPCIOT(Map param) {
        if (!checkLicenseValid()){
            return "授权到期";
        }
        param.put("bidCode", "YJBH-SSZGX_BD-SG-205"); //土建4标

        HttpResponse execute = HttpRequest.post("http://192.168.1.205:8089/receive/pushIOT")
                .body(JSON.toJSONString(param, SerializerFeature.WriteMapNullValue), "application/json").execute();

        return execute.body();
    }
}
