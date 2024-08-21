package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.redis.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class BroadcastAlarmUtil {

    private final String serviceUrl = "http://192.168.1.201:8090";


    @Autowired
    RedisCache redisCache;

    public String getToken() {
        String token = redisCache.getCacheObject("BroadcastAlarmToken");
        if (StringUtils.isBlank(token)) {
            Map<String, String> body = new ConcurrentHashMap<>();
            body.put("username", "admin");
            body.put("password", "123456");
            try (HttpResponse resp = HttpUtil.createPost(serviceUrl+"/v1/login")
                    .body(JSONUtil.toJsonStr(body), "application/json")
                    .execute()) {

                token = JSONUtil.parseObj(resp.body()).getByPath("value.token", String.class);
                // 实际1小时过期，这里设置1800秒s
                redisCache.setCacheObject("BroadcastAlarmToken", token, 1800, TimeUnit.SECONDS);
            }
        }
        return token;
    }

    public JSONObject getDeviceList(){
        HttpResponse response = HttpUtil.createGet(serviceUrl + "/v1/device")
                .header("access_token", new BroadcastAlarmUtil().getToken()).execute();
        return JSONUtil.parseObj(response.body());
    }

    public JSONObject getFileList(){
        HttpResponse response = HttpUtil.createGet(serviceUrl + "/v1/file_group")
                .header("access_token", new BroadcastAlarmUtil().getToken()).execute();
        return JSONUtil.parseObj(response.body());
    }

    public void startTask(){
        String url = serviceUrl+"/v1/real_time_task";
        Map<String, Object> request = new HashMap<>();

        // Populate the map
        request.put("audio_source", 0);
        //播放什么
        request.put("file_ids", Arrays.asList(1));
        //那些设备要播放
        request.put("device_ids", Arrays.asList(1, 2, 3, 4));
        request.put("play_order", 0);
        request.put("loop", 0);
        request.put("volume", 100);
        HttpResponse response = HttpRequest.patch(url).header("access_token",getToken()).body(JSONUtil.toJsonStr(request), ContentType.JSON.getValue()).execute();
        Integer code = Integer.parseInt(JSONUtil.parse(response.body()).getByPath("code").toString());
        if(code == 200){
            //播放
            HttpRequest.post(url+"/start").header("access_token",getToken()).body(JSONUtil.toJsonStr(request), ContentType.JSON.getValue()).execute();
        }
    }
}
