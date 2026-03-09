package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.DateValidator;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.KeyGenerator;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.RSACryptoUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.RSA.ValidationResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.*;

/**
 * 设备Controller
 *
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/rsa")
public class RSAController extends BaseController {
    /**
     * 生成授权文件
     * @param request 到期日期 (格式: yyyy-MM-dd)
     * @return
     */
    @PostMapping("/generateLicense")
    public String generateLicense(@RequestBody Map<String, String> request) throws Exception {
        String expiryDateStr = request.get("expiryDate");
        LocalDate expiryDate;
        
        if (expiryDateStr != null && !expiryDateStr.isEmpty()) {
            expiryDate = LocalDate.parse(expiryDateStr);
        } else {
            // 默认到期日期为一年后
            expiryDate = LocalDate.now().plusYears(1);
        }
        
        // 生成指定到期日期的授权文件
        KeyGenerator.generateLicenseForDate(expiryDate, "/home/user/project/license/license");
        
        return "授权文件生成成功，到期日期: " + expiryDate.toString();
    }


    /**
     * 检查授权文件
     * @return
     */
    @GetMapping("/checkLicense")
    public String checkLicense() throws Exception {
        // 读取文件
        String licenseContent = new String(Files.readAllBytes(Paths.get("/home/user/project/license/license.txt")));
        String privateKeyStr = new String(Files.readAllBytes(Paths.get("/home/user/project/license/key/license_private.key"))).trim();

        // 验证
        PrivateKey privateKey = RSACryptoUtil.privateKeyFromString(privateKeyStr);
        String encryptedExpiryDate = DateValidator.extractEncryptedDate(licenseContent);

        ValidationResult result = DateValidator.validateExpiryDate(encryptedExpiryDate, privateKey);

        if (result.isValid()) {
            return "授权有效，剩余天数: " + result.getDaysRemaining();
        } else {
            return "授权已过期";
        }
    }

}
