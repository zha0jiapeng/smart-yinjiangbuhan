package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.redis.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * IP广播报警
 * @author hu_p
 * @date 2024/6/22
 */
@RestController
@RequestMapping("broadcastalarm")
public class BroadcastAlarmController {

    @Autowired
    RedisCache redisCache;

    /**
     * 获取广播报警设备
     */
    @GetMapping("device")
    public JSONObject deviceList(@RequestParam(required = false) Integer id,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer limit,
                                 @RequestParam(required = false) String keyword){
        HttpRequest request = HttpUtil.createGet("http://192.168.103.100:8080/v1/device")
                .header("access_token", getToken());
        Optional.ofNullable(id).ifPresent(i -> request.form("id", i));
        Optional.ofNullable(page).ifPresent(p -> request.form("page", p));
        Optional.ofNullable(limit).ifPresent(l -> request.form("limit", l));
        Optional.ofNullable(keyword).ifPresent(k -> request.form("keyword", k));
        try (HttpResponse resp = request.execute()) {
            return JSONUtil.parseObj(resp.body());
        }
    }

    String getToken() {
        String token = redisCache.getCacheObject("token");
        if (StringUtils.isBlank(token)) {
            Map<String, String> body = new ConcurrentHashMap<>();
            body.put("username", "admin");
            body.put("password", "123456");
            try (HttpResponse resp = HttpUtil.createPost("http://192.168.103.100:8080/v1/login")
                    .body(JSONUtil.toJsonStr(body), "application/json")
                    .execute()) {

                token = JSONUtil.parseObj(resp.body()).getByPath("value.token", String.class);
                System.out.println("ip:token..."+token);
                // 实际1小时过期，这里设置1800秒s
                redisCache.setCacheObject("token", token, 1800, TimeUnit.SECONDS);
            }
        }
        return token;
    }

}
