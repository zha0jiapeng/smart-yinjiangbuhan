package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.service.IotTspService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DustDetectionData;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 扬尘检测数据
 * @author hu_p
 * @date 2024/6/21
 */
@RestController
@Slf4j
public class DustDetectionController {

    @Autowired
    RedisCache redisCache;

    @Resource
    private IotTspService iotTspService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @PostMapping("dustdetection")
    public AjaxResult uploadDustDetectionData(@RequestBody DustDetectionData dustDetectionData) {
        redisCache.setCacheObject("dustdetection", dustDetectionData);
        IotTsp iotTsp = new IotTsp();
        iotTsp.setDevId(dustDetectionData.getDeviceId());
        iotTsp.setTemperature(dustDetectionData.getTem().toString());
        iotTsp.setPmTwoFive(dustDetectionData.getPm25().toString());
        iotTsp.setPmTen(dustDetectionData.getPm10().toString());
        iotTsp.setTsp(dustDetectionData.getTsp().toString());
        iotTsp.setHumidity(dustDetectionData.getHum().toString());

        Integer wd = dustDetectionData.getWd();
        iotTsp.setWindDirection(getWindDirection(wd));

        iotTsp.setNoise(dustDetectionData.getNoise().toString());
        iotTsp.setPressure(dustDetectionData.getAtm().toString());
        iotTsp.setWindSpeed(dustDetectionData.getWs().toString());
        iotTsp.setCreatedDate(DateUtil.parse(dustDetectionData.getDatatime()));
        iotTsp.setWeather(getWeather());
        iotTspService.save(iotTsp);
        return AjaxResult.success();
    }

    private String getWeather() {
        String url = "https://restapi.amap.com/v3/weather/weatherInfo" +
                "?key=353bfa9ce5dab88691f00d2d7252df4b" +
                "&city=420626" +
                "&extensions=base";
        HttpResponse execute = HttpRequest.get(url).execute();
        String body = execute.body();
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(body);
        if( jsonObject.getInteger("status") == 1){
            JSONArray lives = jsonObject.getJSONArray("lives");
            if(lives.size()>0){
                JSONObject o = (JSONObject) lives.get(0);
                String weather = o.getString("weather");
                return weather;
            }
        }
        return null;
    }


    public String getWindDirection(int degree) {
        if ((degree >= 337.5 && degree <= 360) || (degree >= 0 && degree < 22.5)) {
            return "北风";
        } else if (degree >= 22.5 && degree < 67.5) {
            return "东北风";
        } else if (degree >= 67.5 && degree < 112.5) {
            return "东风";
        } else if (degree >= 112.5 && degree < 157.5) {
            return "东南风";
        } else if (degree >= 157.5 && degree < 202.5) {
            return "南风";
        } else if (degree >= 202.5 && degree < 247.5) {
            return "西南风";
        } else if (degree >= 247.5 && degree < 292.5) {
            return "西风";
        } else if (degree >= 292.5 && degree < 337.5) {
            return "西北风";
        } else {
            return "无效的度数";
        }
    }

    @GetMapping("dustdetection")
    public AjaxResult getDustDetectionData() {
        final DustDetectionData data = redisCache.getCacheObject("dustdetection");
        return AjaxResult.success(data);
    }



    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzk() {
        DustDetectionData dustdetection = redisCache.getCacheObject("dustdetection");
        if(dustdetection!=null) {
            Map<String, Object> data = new HashMap<>();
            data.put("deviceType", "2001000008");
            data.put("SN", "yangchen1");
            data.put("dataType", "200300002");
            data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
            data.put("deviceName", "扬尘监测设备名称");

            // 创建 values 列表
            List<Map<String, Object>> valuesList = new ArrayList<>();
            Map<String, Object> valuesObj = new HashMap<>();
            valuesObj.put("reportTs", DateUtil.current());

            // 创建 profile 对象
            Map<String, Object> profileObj = new HashMap<>();
            profileObj.put("appType", "environment");
            profileObj.put("modelId", "2055");
            profileObj.put("poiCode", "w0907001");
            profileObj.put("name", "环境监测仪");
            profileObj.put("model", "");
            profileObj.put("manufacture", "");
            profileObj.put("owner", "土建4标");
            profileObj.put("makeDate", "2020-05-22");
            profileObj.put("validYear", "2050-05-22");
            profileObj.put("state", "01");
            profileObj.put("installPosition", "出口段隧洞口");
            profileObj.put("x", 0);
            profileObj.put("y", 0);
            profileObj.put("z", 0);

            // 创建 properties 对象
            Map<String, Object> propertiesObj = new HashMap<>();
            propertiesObj.put("monitorTime", dustdetection.getDatatime());
            propertiesObj.put("pm2_5", dustdetection.getPm25());
            propertiesObj.put("pm10", dustdetection.getPm10());
            propertiesObj.put("windSpeed", dustdetection.getWs());
            String windDirection = getWindDirection(dustdetection.getWd());
            propertiesObj.put("windDirection", StrUtil.sub(windDirection, 0, windDirection.length() - 1));
            propertiesObj.put("noiseDb", dustdetection.getNoise());
            propertiesObj.put("temperature", dustdetection.getTem());
            propertiesObj.put("humidity", dustdetection.getHum());

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            valuesObj.put("events", new HashMap<>());
            valuesObj.put("services", new HashMap<>());

            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);
            data.put("values", valuesList);
            log.info("扬尘数据上报到水网智科:{}", JSON.toJSONString(data));
            swzkHttpUtils.pushIOT(data);
        }
    }

}
