package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

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

    @Resource
    SwzkHttpUtils swzkHttpUtils;


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
            Map<String,Object> map = new HashMap<>();
            JSONObject jsonObject = requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetUseEnergy?PrivAddr=%252Findex.html");
            map.put("useEnergy",jsonObject);
            map.put("data",JSON.parseArray(data));
            return AjaxResult.success(map);
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void cron() {
        HttpRequest request = HttpUtil.createPost("http://nhapi.yunjichaobiao.com/api/Device/AmmeterData_Summary")
                .bearerAuth(getToken()).body("{\n" +
                        "    \"areaID\": \"51733\",\n" +
                        "    \"ammeterID\": \"98261\",\n" +
                        "    \"PrivAddr\": \"%2FEquipment%2Faqgz.html\"\n" +
                        "}");
        HttpResponse resp = request.execute();
        final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
        if (Objects.isNull(data)) {
            refreshToken();
            return;
        }
        JSONArray array = JSON.parseArray(data);
        List<Map> javaList = array.toJavaList(Map.class);
        Map<String,Object> map = new HashMap<>();
        javaList.forEach(item -> {
             if(item.get("Keyword").equals("Power")){
                 map.put("power", item.get("ValueAP"));
             }
            if(item.get("Keyword").equals("V")){
                map.put("voltageA", item.get("ValueA"));
                map.put("voltageB", item.get("ValueB"));
                map.put("voltageC", item.get("ValueC"));
            }
            if(item.get("Keyword").equals("A")){
                map.put("currentA", item.get("ValueA"));
                map.put("currentB", item.get("ValueB"));
                map.put("currentC", item.get("ValueC"));
            }
        });
        pushSwzk(map);
    }

    private void pushSwzk(Map map) {
        // Creating the nested map structure
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("deviceType", "2001000013");
        rootMap.put("SN", "DSC1010000000YJB003");
        rootMap.put("dataType", "200300009");
        rootMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        rootMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        rootMap.put("deviceName", "施工配电箱");
        List<Map<String, Object>> valuesList = new ArrayList<>();
        // Creating the values list with a single value map
        Map<String, Object> valuesMap = new HashMap<>();
        // Creating the profile map
        Map<String, Object> profileMap = new HashMap<>();
        profileMap.put("appType", "");
        profileMap.put("modelId", "2405");
        profileMap.put("poiCode", "w0104000");
        profileMap.put("name", "施工配电箱");
        profileMap.put("model", "设备型号");
        profileMap.put("manufacture", "云集抄表");
        profileMap.put("owner", "江汉水网公司");
        profileMap.put("makeDate", "2020-05-23");
        profileMap.put("validYear", "2050");
        profileMap.put("state", "01");
        profileMap.put("installPosition", "04");
        profileMap.put("distributeLevel", "01");
        profileMap.put("ratedVoltage", "30");
        profileMap.put("ratedPower", "30");
        profileMap.put("installedArea", "30");
        profileMap.put("tempAlmLim", "30");
        profileMap.put("leakCurrAlmLim", "30");
        profileMap.put("x", 112.002224);
        profileMap.put("y", 112.002224);
        profileMap.put("z", 1.2);

        // Creating the properties map
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("monitorTime", DateUtil.now());
        propertiesMap.put("voltA", map.get("voltageA"));
        propertiesMap.put("voltB", map.get("voltageB"));
        propertiesMap.put("voltC", map.get("voltageC"));
        propertiesMap.put("currA", map.get("currentA"));
        propertiesMap.put("currB", map.get("currentB"));
        propertiesMap.put("currC", map.get("currentC"));
        propertiesMap.put("activeEnergy", map.get("power"));
        propertiesMap.put("tempA", null);
        propertiesMap.put("tempB", null);
        propertiesMap.put("tempC", null);
        propertiesMap.put("cabinetTemp", null);
        propertiesMap.put("leakCurr", null);
        propertiesMap.put("cableTempAlm", null);
        propertiesMap.put("leakCurrAlm", null);
        valuesMap.put("reportTs", DateUtil.current());
        valuesMap.put("profile", profileMap);
        valuesMap.put("properties", propertiesMap);
        valuesMap.put("events", new HashMap<>());
        valuesMap.put("services", new HashMap<>());
        valuesList.add(valuesMap);

        rootMap.put("values", valuesList);
        swzkHttpUtils.pushIOT(rootMap);
    }

    /**
     * 获取指标曲线总数
     * @param dataType 15m 15分钟; D 日; M 月
     */
    @GetMapping("getIndexCurveTotal")
    public AjaxResult getIndexCurveTotal(@RequestParam String dataType) {
        return AjaxResult.success(requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetIndexCurveTotal?dateType=" + dataType + "&PrivAddr=%252Findex.html"));
    }

    /**
     * 获取电量使用情况
     * @return
     */
    @GetMapping("getUseEnergy")
    public AjaxResult getUseEnergy() {
        return AjaxResult.success(requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetUseEnergy?PrivAddr=%252Findex.html"));
    }

    private JSONObject requestUrl(String url) {
        HttpRequest request = HttpUtil.createGet(url)
                .bearerAuth(getToken());
        try (HttpResponse resp = request.execute()) {
            final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
            if (Objects.isNull(data)) {
                refreshToken();
                requestUrl(url);
                //return AjaxResult.error("token is expired");
            }
            return JSON.parseObject(data);
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
