package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
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
    SwzkHttpUtils swzkHttpUtils;

    @PostMapping("dustdetection")
    public AjaxResult uploadDustDetectionData(@RequestBody DustDetectionData dustDetectionData) {
        redisCache.setCacheObject("dustdetection", dustDetectionData);
        return AjaxResult.success();
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
            profileObj.put("owner", "");
            profileObj.put("makeDate", "2020-05-22");
            profileObj.put("validYear", "2050-05-22");
            profileObj.put("state", "01");
            profileObj.put("installPosition", "出口段隧洞口");
            profileObj.put("x", 0);
            profileObj.put("y", 0);
            profileObj.put("z", 0);

            // 创建 properties 对象
            Map<String, Object> propertiesObj = new HashMap<>();
            propertiesObj.put("monitorTime", dustdetection.datatime);
            propertiesObj.put("pm2_5", dustdetection.pm25);
            propertiesObj.put("pm10", dustdetection.pm10);
            propertiesObj.put("windSpeed", dustdetection.ws);
            propertiesObj.put("windDirection",dustdetection.wd);
            propertiesObj.put("noiseDb", dustdetection.noise);
            propertiesObj.put("temperature", dustdetection.tem);
            propertiesObj.put("humidity", dustdetection.hum);

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
