package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/holePeopleAccess")
public class HolePeopleAccessController {

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @RequestMapping("/push")
    public Map<String, Object> push(@RequestBody Map<String, String> map) {
        log.info("收到请求,map:{}", map);
        pushSwzk(map);


        Map<String, Object> result = new HashMap<>();
        result.put("result", 0);
        return result;
    }

    private void pushSwzk(Map<String, String> map) {

        // Create the main map
        Map<String, Object> mainMap = new HashMap<>();

        // Add top-level fields
        mainMap.put("deviceType", "2001000010");
        mainMap.put("SN", map.get("SN"));
        mainMap.put("dataType", "200300003");
        mainMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        mainMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        mainMap.put("deviceName", map.get("devicename"));

        // Create the 'values' list
        List<Map<String, Object>> valuesList = new ArrayList<>();
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("reportTs", DateUtil.current());
        // Create the 'profile' map
        Map<String, Object> profileMap = new HashMap<>();
        profileMap.put("appType", "access_control");
        profileMap.put("modelId", "2053");
        profileMap.put("poiCode", "w0713001");
        profileMap.put("name", "人脸门禁");
        profileMap.put("model", "S3");
        profileMap.put("manufacture", "海康威视");
        profileMap.put("owner", "海康威视");
        profileMap.put("makeDate", "2020-05-22");
        profileMap.put("validYear", "2050-05-22");
        profileMap.put("state", "01");
        profileMap.put("installPosition", "洞口一级门禁");
        profileMap.put("x", 0);
        profileMap.put("y", 0);
        profileMap.put("z", 0);
        profileMap.put("level", "01");
        valuesMap.put("profile", profileMap);
        // Create the 'events' map
        Map<String, Object> eventsMap = new HashMap<>();
        Map<String, Object> passMap = new HashMap<>();
        passMap.put("eventType", 1);
        passMap.put("eventTs", map.get("time"));
        passMap.put("describe", "");
        passMap.put("idCardNumber", map.get("idNum"));
        passMap.put("name", map.get("name"));
        passMap.put("passTime", map.get("time"));
        passMap.put("passDirection", map.get("devicename").toString().contains("出") ? "01" : "02");
        eventsMap.put("pass", passMap);

        valuesMap.put("events", eventsMap);
        // Add empty 'properties' and 'services' maps
        valuesMap.put("properties", new HashMap<String, Object>());
        valuesMap.put("services", new HashMap<String, Object>());
        // Add the valuesMap to the valuesList
        valuesList.add(valuesMap);
        mainMap.put("values", valuesList);
        log.info("门禁推送：{}", JSON.toJSONString(mainMap));
        swzkHttpUtils.pushIOT(mainMap);
    }
}