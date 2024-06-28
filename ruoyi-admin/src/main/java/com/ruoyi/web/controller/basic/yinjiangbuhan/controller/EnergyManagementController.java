package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
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
                String rootDirectory = System.getProperty("user.dir");
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(rootDirectory + "/python/venv/bin/python", rootDirectory + "/python/ddddocr_work.py");
                log.info("refresh token");
                final Process process = processBuilder.start();
                process.waitFor();
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

    public static void main(String[] args) throws TesseractException {
        String base64Str = "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"; // 这里应该是你的Base64编码字符串

        byte[] imageBytes = Base64.getDecoder().decode(base64Str);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            bis.close();
            Tesseract tesseract = new Tesseract();
            tesseract.setLanguage("eng");
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            tesseract.setDatapath("./tessdata");
            String result = tesseract.doOCR(image);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void convertBase64ToImage(String base64Str, String path) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Str);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bis);
            bis.close();
            File outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
