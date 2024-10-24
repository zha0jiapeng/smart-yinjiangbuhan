package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class TuhuguancheUtil {

    private static final String openapi_url = "http://open.tuhugc.com";
    private static final String app_key = "5508166a129cf8e1bfe2943b14e6d1dc";
    private static final String app_secret = "b05cec7978367a830ab693c0a504e103";


    private synchronized static String getToken() {
        RedisTemplate redisTemplate = RedisUtil.redis;
        Object carToken = redisTemplate.opsForValue().get("carToken");
        System.out.println("redis token :"+carToken);
        if(carToken!=null){
            return carToken.toString();
        }
        Map<String, String> paramMap = getCommonParam();
        // 私有参数_获取token
        paramMap.put("userId", "13521470746");
        paramMap.put("expiresIn", "7200");

        String sign = "";
        try {
            sign = SignUtils.signTopRequest(paramMap, app_secret, "md5");
            paramMap.put("sign", sign);
            System.out.println("签名："+sign);
        } catch (IOException e) {
            return null;
        }
        Map<String, Object> objectMap = paramMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (Object) e.getValue()));
        String body = HttpUtil.createPost(openapi_url+"/v1/token/get")
                .form(objectMap).execute().body();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject result = jsonObject.getJSONObject("result");
        Object accessToken = result.get("accessToken");
        redisTemplate.opsForValue().set("carToken",accessToken,1, TimeUnit.HOURS);
        System.out.println("token :"+accessToken);
        return accessToken.toString();
    }


    private static Map<String, String> getCommonParam() {
        Map<String, String> paramMap = new HashMap<>();
        // 公共参数
        paramMap.put("appKey", app_key);
        paramMap.put("v", "1.0");
        paramMap.put("timestamp", DateUtil.now());
        paramMap.put("signMethod", "md5");
        paramMap.put("format", "json");
        return paramMap;
    }


    public static Map getDeviceLocation() {
        RedisTemplate redisTemplate = RedisUtil.redis;
        String carLocationStr = (String) redisTemplate.opsForValue().get("carLocation");
        if(carLocationStr!=null){
            return JSON.parseObject(carLocationStr, Map.class);
        }

        String token = getToken();
        Map<String, String> paramMap2 = getCommonParam();
        paramMap2.put("userId", "13521470746");
        JSONObject jsonObject2 = getParam(token, paramMap2, "/v1/vehicle/list");
        log.info("======车辆定位：{}======", jsonObject2);
        JSONArray cars = jsonObject2.getJSONArray("result");
        List<Map> javaList = cars.toJavaList(Map.class);
        List<String> imeis = javaList.stream()
                .map(map -> (String) map.get("imei")) // 提取 "name" 的值
                .collect(Collectors.toList()); // 收集为 List
        String imeiString = String.join(",", imeis);
        Map<String, String> paramMap = getCommonParam();
        paramMap.put("mapType", "WGS84");
        paramMap.put("imeis", imeiString);
        paramMap.put("userId", "13521470746");
        JSONObject jsonObject = getParam(token, paramMap, "/v1/device/location/get");
        JSONArray locations = jsonObject.getJSONArray("result");
        List<JSONObject> list = new ArrayList<>();
        for (Object carObj : cars) {
            JSONObject car = (JSONObject) carObj;
            for (Object locationObj : locations) {
                JSONObject location = (JSONObject) locationObj;
                if (car.getString("imei").equals(location.getString("imei"))) {
                    if (location.getInteger("status") == 0) {
                        continue;
                    }
                    location.put("licensePlate", car.get("licensePlate"));
                    location.put("vehicleDriver", car.get("vehicleDriver"));
                    if (car.get("vehicleType").equals("商砼车")) {
                        location.put("vehicleType", "混凝土搅拌运输车");
                    } else {
                        location.put("vehicleType", car.get("vehicleType"));
                    }
                    list.add(location);
                    break;
                }
            }
        }
        jsonObject.remove("result");
        jsonObject.put("result",list);

        redisTemplate.opsForValue().set("carLocation", JSON.toJSONString(jsonObject), 10, TimeUnit.MINUTES);
        Map javaObject = jsonObject.toJavaObject(Map.class);
        return javaObject;
    }

    public static Map getDeviceHistoryLocation(String imei){
        String token = getToken();
        Map<String, String> paramMap = getCommonParam();
        paramMap.put("startTime", DateUtil.format(DateUtil.beginOfDay(DateUtil.date()), "yyyy-MM-dd HH:mm:ss"));
        paramMap.put("endTime",DateUtil.now());
        paramMap.put("userId", "13521470746");
        paramMap.put("imei", imei);
        JSONObject jsonObject = getParam(token, paramMap, "/v1/device/track/list");
        return jsonObject.toJavaObject(Map.class);
    }

    private static JSONObject getParam (String token, Map<String, String>paramMap,String url){
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        // 通过接口获取的accessToken
        headerMap.put("X-Access-Token", token);
//        Map<String, String> paramMap = getCommonParam();
//        paramMap.put("mapType","BAIDU");
//        paramMap.put("userId", "13521470746");
        String sign = "";
        try {
            sign = SignUtils.signTopRequest(paramMap, app_secret, "md5");
            paramMap.put("sign", sign);
        } catch (IOException e) {
            System.err.println(e);
        }
        Map<String, Object> objectMap = paramMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (Object) e.getValue()));
        url = openapi_url+url;
        String body = HttpUtil.createPost(url)
                .headerMap(headerMap,true)
                .form(objectMap).execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        return jsonObject;
    }


}
