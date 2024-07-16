package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peopleLocation")
public class PeopleLocationController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private SysWorkPeopleService workPeopleService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @PostMapping("/inTunnelLocation")
    public Map<String,Object> peopleLocation(@RequestBody Map request){
        Map<String,Object> parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","map_id必传");
            return parse;
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) redisTemplate.opsForHash().get("peopleInLocation", request.get("map_id")+"");
        if(list==null || list.size()==0) {
            HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                    .body(JSON.toJSONString(request), "application/json")
                    .execute();
            String body = execute.body();
            parse = JSONObject.parseObject(body, Map.class);
        }else{
            parse.put("msg","success");
            parse.put("code",200);
            parse.put("data",list);
        }
        return parse;
    }


    @PostMapping("/outTunnelLocation")
    public Map<String,Object> outPeopleLocation(@RequestBody Map request){
        Map<String,Object> parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","map_id必传");
            return parse;
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) redisTemplate.opsForHash().get("peopleOutLocation", request.get("map_id")+"");
        if(list==null || list.size()==0) {
            HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                    .body(JSON.toJSONString(request), "application/json")
                    .execute();
            String body = execute.body();
            parse = JSONObject.parseObject(body, Map.class);
        }else{
            parse.put("msg","success");
            parse.put("code",200);
            parse.put("data",list);
        }
        return parse;
    }




    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzkIn() {
        Map<String,List<Map<String,Object>>> peopleLocation = (Map<String, List<Map<String,Object>>>) redisTemplate.opsForHash().entries("peopleInLocation");
        if(peopleLocation==null || peopleLocation.size()==0){
            HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                    .body(JSON.toJSONString(new Object()), "application/json")
                    .execute();
            String body = execute.body();
            Map parse = JSONObject.parseObject(body, Map.class);
            List<Map<String,Object>> data = (List<Map<String, Object>>) parse.get("data");
            if(data==null) return;
            Map<String, List<Map<String, Object>>> groupedByMapId = data.stream()
                    .collect(Collectors.groupingBy(map ->map.get("map_id").toString()));
            Set<Map.Entry<String, List<Map<String, Object>>>> entries = groupedByMapId.entrySet();
            for (Map.Entry<String, List<Map<String, Object>>> entry : entries) {
                redisTemplate.opsForHash().put("peopleInLocation",entry.getKey(),entry.getValue());
            }
        }
        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000040");
        data.put("SN", "renyuandingwei1");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();

        Set<Map.Entry<String, List<Map<String, Object>>>> entries = peopleLocation.entrySet();
        for (Map.Entry<String, List<Map<String, Object>>> entry : entries) {
            List<Map<String, Object>> value = entry.getValue();
            for (Map<String, Object> itemMap : value) {
                // 创建 values 列表

                Map<String, Object> valuesObj = new HashMap<>();
                valuesObj.put("reportTs", DateUtil.current());

                // 创建 profile 对象
                Map<String, Object> profileObj = new HashMap<>();
                profileObj.put("appType", "life");
                profileObj.put("modelId", "200017");
                profileObj.put("poiCode", "w0907005");
                profileObj.put("deviceType", "2001000040");

                // 创建 properties 对象
                Map<String, Object> propertiesObj = new HashMap<>();
                //propertiesObj.put("ringCode", "");
                //propertiesObj.put("icCode", "");
                //propertiesObj.put("heartRate", "");
                propertiesObj.put("electric", itemMap.get("bat"));
                propertiesObj.put("time", DateUtil.format(DateUtil.date(((Integer) itemMap.get("time"))), DatePattern.NORM_DATETIME_PATTERN));
                propertiesObj.put("sos", "0");
                propertiesObj.put("type", "01");
                propertiesObj.put("stationX", 0);
                propertiesObj.put("stationY", 0);
                propertiesObj.put("stationZ", 0);
                propertiesObj.put("humanX", 0);
                propertiesObj.put("humanY", 0);
                propertiesObj.put("humanZ", 0);
                propertiesObj.put("stationDistance", new BigDecimal(2939).add((BigDecimal) itemMap.get("result_x")));
                propertiesObj.put("holeDistance", 0);
                SysWorkPeople one = workPeopleService.getOne(new LambdaQueryWrapper<SysWorkPeople>().eq(SysWorkPeople::getName, ((Map<String, Object>) itemMap.get("user_info")).get("user_name")), false);
                if(one!=null)
                    propertiesObj.put("idCardNumber", one.getIdCard());

                propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
                propertiesObj.put("locateMode", "GPS");

                // 将 profile 和 properties 对象放入 values 对象中
                valuesObj.put("profile", profileObj);
                valuesObj.put("properties", propertiesObj);
                // 将 values 对象放入 values 列表中
                valuesList.add(valuesObj);

            }
        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzkOut() {
        Map<String,List<Map<String,Object>>> peopleLocation = (Map<String, List<Map<String,Object>>>) redisTemplate.opsForHash().entries("peopleOutLocation");
        if(peopleLocation==null || peopleLocation.size()==0){
            HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                    .body(JSON.toJSONString(new Object()), "application/json")
                    .execute();
            String body = execute.body();
            Map parse = JSONObject.parseObject(body, Map.class);
            List<Map<String,Object>> data = (List<Map<String, Object>>) parse.get("data");
            if(data==null) return;
            Map<String, List<Map<String, Object>>> groupedByMapId = data.stream()
                    .collect(Collectors.groupingBy(map ->map.get("map_id").toString()));
            Set<Map.Entry<String, List<Map<String, Object>>>> entries = groupedByMapId.entrySet();
            for (Map.Entry<String, List<Map<String, Object>>> entry : entries) {
                redisTemplate.opsForHash().put("peopleOutLocation",entry.getKey(),entry.getValue());
            }
        }
        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000040");
        data.put("SN", "renyuandingwei2");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "洞外人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();

        Set<Map.Entry<String, List<Map<String, Object>>>> entries = peopleLocation.entrySet();
        for (Map.Entry<String, List<Map<String, Object>>> entry : entries) {
            List<Map<String, Object>> value = entry.getValue();
            for (Map<String, Object> itemMap : value) {
                // 创建 values 列表

                Map<String, Object> valuesObj = new HashMap<>();
                valuesObj.put("reportTs", DateUtil.current());

                // 创建 profile 对象
                Map<String, Object> profileObj = new HashMap<>();
                profileObj.put("appType", "life");
                profileObj.put("modelId", "200017");
                profileObj.put("poiCode", "w0907005");
                profileObj.put("deviceType", "2001000040");

                // 创建 properties 对象
                Map<String, Object> propertiesObj = new HashMap<>();
                propertiesObj.put("ringCode", "");
                propertiesObj.put("icCode", "");
                propertiesObj.put("heartRate", "");
                propertiesObj.put("electric", itemMap.get("bat"));
                propertiesObj.put("time", DateUtil.format(DateUtil.date(((Integer) itemMap.get("time"))), DatePattern.NORM_DATETIME_PATTERN));
                propertiesObj.put("sos", "0");
                propertiesObj.put("type", "01");
                propertiesObj.put("stationX", 0);
                propertiesObj.put("stationY", 0);
                propertiesObj.put("stationZ", 0);
                propertiesObj.put("humanX", 0);
                propertiesObj.put("humanY", 0);
                propertiesObj.put("humanZ", 0);
                propertiesObj.put("stationDistance", 100);
                propertiesObj.put("holeDistance", 0);
                propertiesObj.put("idCardNumber", "");
                propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
                propertiesObj.put("locateMode", "GPS");

                // 将 profile 和 properties 对象放入 values 对象中
                valuesObj.put("profile", profileObj);
                valuesObj.put("properties", propertiesObj);
                // 将 values 对象放入 values 列表中
                valuesList.add(valuesObj);

            }
        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


}
