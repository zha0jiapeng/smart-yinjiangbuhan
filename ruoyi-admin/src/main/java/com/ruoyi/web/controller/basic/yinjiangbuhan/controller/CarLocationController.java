package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.TuhuguancheUtil;
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
    SwzkHttpUtils swzkHttpUtils;

    @RequestMapping("/location")
    public Map carLocation() {
       return TuhuguancheUtil.getDeviceLocation();
    }

    @GetMapping("/historyLocation")
    public Map historyLocation(String imei) {
        return TuhuguancheUtil.getDeviceHistoryLocation(imei);
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzk() {
        Map deviceLocation = TuhuguancheUtil.getDeviceLocation();
      //  redisTemplate.opsForValue().set("carLocation", JSON.toJSONString(deviceLocation));
        List list = (List)deviceLocation.get("result");
        for (Object iobj : list) {
            List<Map<String, Object>> valuesList = new ArrayList<>();
            com.alibaba.fastjson.JSONObject itemMap = (com.alibaba.fastjson.JSONObject) iobj;
            Map<String, Object> events = new HashMap<>();
            Map<String, Object> pass = new HashMap<>();
            pass.put("eventType", 1);
            pass.put("eventTs", DateUtil.current());
            pass.put("describe", "");
            pass.put("plateNumber", itemMap.get("licensePlate"));
            pass.put("locateMode", "GPS");
            pass.put("x", itemMap.get("lng"));
            pass.put("y", itemMap.get("lat"));
            pass.put("z", 0);
            //pass.put("startTime", "");
            //pass.put("mileage", 41);
            pass.put("driver", itemMap.get("vehicleDriver"));
          //  pass.put("workTime", "36");
            pass.put("continuousTime", "36");
            pass.put("speed", itemMap.get("speed"));
           // pass.put("locationName", "");
           // pass.put("load_value", "0");
           // pass.put("load_percentage", "0%");
            events.put("pass",pass);
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
            profile.put("validYear", 20);

            // 创建 values
            Map<String, Object> values = new HashMap<>();
            values.put("reportTs", DateUtil.current());
            values.put("profile", profile);
            values.put("properties", properties);
            values.put("events", events);
            values.put("services", new HashMap<>());
            valuesList.add(values);
            // 创建最终的 JSON 数据
            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("deviceType", "2001000014");
            jsonData.put("SN", itemMap.get("imei"));
            jsonData.put("dataType", "200300017");
            jsonData.put("workAreaCode", "YJBH-SSZGX_GQ-08");
            jsonData.put("deviceName", "车辆网关");

            jsonData.put("values", valuesList);
            swzkHttpUtils.pushIOT(jsonData);
        }


    }
}
