package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.BroadcastAlarmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        HttpRequest request = HttpUtil.createGet("http://192.168.1.201:8090/v1/device")
                .header("access_token", new BroadcastAlarmUtil().getToken());
        Optional.ofNullable(id).ifPresent(i -> request.form("id", i));
        Optional.ofNullable(page).ifPresent(p -> request.form("page", p));
        Optional.ofNullable(limit).ifPresent(l -> request.form("limit", l));
        Optional.ofNullable(keyword).ifPresent(k -> request.form("keyword", k));
        try (HttpResponse resp = request.execute()) {
            return JSONUtil.parseObj(resp.body());
        }
    }




}
