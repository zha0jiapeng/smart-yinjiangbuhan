package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Objects;

/**
 * @author hu_p
 * @date 2024/6/25
 */
@RestController
@RequestMapping("energyManagement")
@AllArgsConstructor
@Slf4j
public class EnergyManagementController {

    @Autowired
    RedisCache redisCache;

    @Value("${em.path}")
    String rootPath;

    @Value("${em.python}")
    String pythonPath;


    @GetMapping("ammeterDataSummary")
    public AjaxResult ammeterDataSummary() {
        HttpRequest request = HttpUtil.createPost("http://nhapi.yunjichaobiao.com/api/Device/AmmeterData_Summary")
                .bearerAuth(getToken()).body("{\n" +
                        "    \"areaID\": \"51733\",\n" +
                        "    \"ammeterID\": \"98261\",\n" +
                        "    \"PrivAddr\": \"%2FEquipment%2Faqgz.html\"\n" +
                        "}");
        try (HttpResponse resp = request.execute()) {
            final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
            if (Objects.isNull(data)) {
                refreshToken();
                return AjaxResult.error("token is expired");
            }
            return AjaxResult.success(JSON.parseArray(data));
        }
    }

    /**
     * 获取指标曲线总数
     * @param dataType 15m 15分钟; D 日; M 月
     */
    @GetMapping("getIndexCurveTotal")
    public AjaxResult getIndexCurveTotal(@RequestParam String dataType) {
        return requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetIndexCurveTotal?dateType=" + dataType + "&PrivAddr=%252Findex.html");
    }

    /**
     * 获取电量使用情况
     * @return
     */
    @GetMapping("getUseEnergy")
    public AjaxResult getUseEnergy() {
        return requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetUseEnergy?PrivAddr=%252Findex.html");
    }

    private AjaxResult requestUrl(String url) {
        HttpRequest request = HttpUtil.createGet(url)
                .bearerAuth(getToken());
        try (HttpResponse resp = request.execute()) {
            final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
            if (Objects.isNull(data)) {
                refreshToken();
                return AjaxResult.error("token is expired");
            }
            return AjaxResult.success(JSON.parseObject(data));
        }
    }

    void refreshToken() {
        if (StringUtils.isNotBlank(redisCache.getCacheObject("energyManagementGetToken"))) {
            return;
        }
        new Thread(() -> {
            try {
                // 防止重复调用获取token
                redisCache.setCacheObject("energyManagementGetToken", "startGetToken");
                String token = getLoginToken();
                redisCache.setCacheObject("energyManagementToken", token);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            } finally {
                redisCache.deleteObject("energyManagementGetToken");
            }
        }).start();
    }

    String getToken() {
        String token = redisCache.getCacheObject("energyManagementToken");
        if (StringUtils.isBlank(token)) {
            refreshToken();
            throw new IllegalArgumentException("token is expired");
        }
        return token;
    }

    /**
     * UserID:N11649
     * Password:123456
     * KeyStr:ONAPUZFS9TB2
     * Code:t864
     * Language:cn
     * client:0
     */
    String getLoginToken() {
        String token = "";
        int count = 0;
        do {
            String url = "http://nhapi.yunjichaobiao.com/api/Account/Login";
            final HttpRequest req = HttpUtil.createPost(url);
            req.form("UserID", "N11649");
            req.form("Password", "123456");
            req.form("KeyStr", "ONAPUZFS9TB2");
            req.form("Code", getCaptcha());
            try (HttpResponse resp = req.execute()) {
                token = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Token");
                log.info("token: {}", token);
            }
            if (++count > 5) {
                break;
            }
        } while (StringUtils.isBlank(token));
        return token;
    }

    String getCaptcha() {
        String url = "http://nhapi.yunjichaobiao.com/api/Account/GetCaptcha?keyStr=ONAPUZFS9TB2";
        try (HttpResponse resp = HttpUtil.createPost(url).execute()) {
            try {
                final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data").replaceAll("\"", "");
                byte[] imageBytes = Base64.getDecoder().decode(data);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(bis);
                bis.close();
                File outputFile = new File(rootPath+"/captcha.png");
                ImageIO.write(image, "png", outputFile);

                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(pythonPath, rootPath + "/ocr.py");
                final Process process = processBuilder.start();
                // 获取进程的输出流
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                // 读取输出结果
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                process.waitFor();
                final String captcha = output.substring(output.lastIndexOf(":") + 1);
                log.info("captcha: {}", captcha);
                return captcha;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }
        return StringUtils.EMPTY;
    }

}
