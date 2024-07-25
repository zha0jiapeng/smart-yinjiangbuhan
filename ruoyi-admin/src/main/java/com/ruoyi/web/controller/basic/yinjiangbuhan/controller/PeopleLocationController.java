package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peopleLocation")
public class PeopleLocationController {

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
        HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        return JSONObject.parseObject(body, Map.class);
    }


    @PostMapping("/outTunnelLocation")
    public Map<String,Object> outPeopleLocation(@RequestBody Map request){
        Map<String,Object> parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","map_id必传");
            return parse;
        }
        HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        return JSONObject.parseObject(body, Map.class);
    }




    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzkIn() {
        String now = DateUtil.now();
        HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(new Object()), "application/json")
                .execute();
        String body = execute.body();
        Map parse = JSONObject.parseObject(body, Map.class);
        List<Map<String, Object>> datee = (List<Map<String, Object>>) parse.get("data");
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
        for (Map<String, Object> itemMap : datee) {
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
            propertiesObj.put("time", now);
            propertiesObj.put("sos", "0");
            propertiesObj.put("type", "01");
            propertiesObj.put("stationX", 0);
            propertiesObj.put("stationY", 0);
            propertiesObj.put("stationZ", 0);
            propertiesObj.put("humanX", 0);
            propertiesObj.put("humanY", 0);
            propertiesObj.put("humanZ", 0);
            propertiesObj.put("stationDistance", new BigDecimal(2939).add(new BigDecimal(itemMap.get("result_x").toString())));
            propertiesObj.put("holeDistance", 0);
            propertiesObj.put("idCardNumber", ((Map<String, Object>) itemMap.get("user_info")).get("number"));
//                SysWorkPeople one = workPeopleService.getOne(new LambdaQueryWrapper<SysWorkPeople>().eq(SysWorkPeople::getName, ((Map<String, Object>) itemMap.get("user_info")).get("user_name")), false);
//                if(one!=null)
//                    propertiesObj.put("idCardNumber", one.getIdCard());

            propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
            propertiesObj.put("locateMode", "GPS");

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);

        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzkOut() {
        String now = DateUtil.now();
        HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                .body(JSON.toJSONString(new Object()), "application/json")
                .execute();
        String body = execute.body();
        Map parse = JSONObject.parseObject(body, Map.class);
        List<Map<String,Object>> datae = (List<Map<String, Object>>) parse.get("data");

        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000041");
        data.put("SN", "renyuandingwei2");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "洞外人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();
        for (Map<String, Object> itemMap : datae) {
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
//                propertiesObj.put("ringCode", "");
//                propertiesObj.put("icCode", "");
//                propertiesObj.put("heartRate", "");
            propertiesObj.put("electric", itemMap.get("bat"));
            propertiesObj.put("time", now);
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
            propertiesObj.put("idCardNumber", ((Map<String, Object>) itemMap.get("user_info")).get("number"));
            propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
            propertiesObj.put("locateMode", "GPS");

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);

        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


}
