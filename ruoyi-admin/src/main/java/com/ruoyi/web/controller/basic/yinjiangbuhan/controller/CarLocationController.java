package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.TuhuguancheUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carLocation")
public class CarLocationController {

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/location")
    public Map carLocation() {
        String carLocationStr = (String) redisTemplate.opsForValue().get("carLocation");
        if (carLocationStr == null){
            return TuhuguancheUtil.getDeviceLocation();
        }else{
            return JSON.parseObject(carLocationStr, Map.class);
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzk() {
        Map deviceLocation = TuhuguancheUtil.getDeviceLocation();
        redisTemplate.opsForValue().set("carLocation", JSON.toJSONString(deviceLocation));
        List<Map<String, Object>> valuesList = new ArrayList<>();
        List<Map<String,Object>> result = (List<Map<String,Object>>)deviceLocation.get("result");
        for (Map<String, Object> itemMap : result) {
            Map<String, Object> events = new HashMap<>();
            events.put("eventType", 1);
            events.put("eventTs", DateUtil.current());
            events.put("describe", "");
            events.put("plateNumber", itemMap.get("licensePlate"));
            events.put("locateMode", "GPS");
            events.put("x", itemMap.get("lng"));
            events.put("y", itemMap.get("lat"));
            events.put("z", 0);
            //events.put("startTime", "");
            //events.put("mileage", 41);
            events.put("driver", itemMap.get("vehicleDriver"));
          //  events.put("workTime", "36");
            events.put("continuousTime", "36");
            events.put("speed", itemMap.get("speed"));
           // events.put("locationName", "");
           // events.put("load_value", "0");
           // events.put("load_percentage", "0%");

            // 创建 values 中的 properties
            Map<String, Object> properties = new HashMap<>();
            properties.put("hardware_type", "Z03.1");
            properties.put("hardware_state", "normal");
            properties.put("updated_at", DateUtil.now());

            // 创建 values 中的 profile
            Map<String, Object> profile = new HashMap<>();
            profile.put("appType", "parking");
            profile.put("modelId", "2054");
            profile.put("poiCode", "w0708003");
            profile.put("name", "途狐管车");
            profile.put("model", "");
            profile.put("manufacture", "");
            profile.put("owner", "");
            profile.put("makeDate", "2024-05-22");
            profile.put("validYear", "20");

            // 创建 values
            Map<String, Object> values = new HashMap<>();
            values.put("reportTs", DateUtil.current());
            values.put("profile", profile);
            values.put("properties", properties);
            values.put("events", events);
            values.put("services", new HashMap<>());
            valuesList.add(values);
        }


        // 创建最终的 JSON 数据
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("deviceType", "2001000014");
        jsonData.put("SN", "DSC1010000000YJB001");
        jsonData.put("dataType", "200300017");
        jsonData.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        jsonData.put("deviceName", "车辆网关");

        jsonData.put("values", valuesList);
        SwzkHttpUtils.pushIOT(jsonData);


    }
}
