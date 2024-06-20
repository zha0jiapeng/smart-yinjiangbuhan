package com.ruoyi.web.controller.basic.view.bim;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.basic.OpenClose;
import com.ruoyi.web.controller.socket.WebSocketController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("view/operate")
public class ViewOperateController extends BaseController {


    @Resource
    private RedisCache redisCache;


    @PostMapping("open")
    public AjaxResult open(@RequestBody OpenClose openClose) {
        try {
            if (!CollectionUtils.isEmpty(redisCache.getCacheSet("uuid"))) {
                Set<Object> cacheSet = redisCache.getCacheSet("uuid");
                cacheSet.forEach(var -> {
                    try {
                        WebSocketController.sendInfo(JSON.toJSONString(openClose), var.toString());
                    } catch (IOException e) {
                        log.error(" WebSocket 传输打开页面异常");
                    }
                });
            } else {
                WebSocketController.sendInfo(JSON.toJSONString(openClose), "connect");
            }
        } catch (Exception e) {
            log.error(" WebSocket 传输打开页面异常");
        }
        return AjaxResult.success();
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void refreshWebSocketThoroughfare() {
        log.info("=======================刷新管道信息开始====================================");

        OpenClose openClose = new OpenClose();
        openClose.setUrl("false");

        try {
            if (!CollectionUtils.isEmpty(redisCache.getCacheSet("uuid"))) {
                Set<Object> cacheSet = redisCache.getCacheSet("uuid");
                cacheSet.forEach(var -> {
                    try {
                        WebSocketController.sendInfo(JSON.toJSONString(openClose), var.toString());
                    } catch (IOException e) {
                        log.error(" WebSocket 传输打开页面异常");
                    }
                });

                // WebSocketController.sendInfo(JSON.toJSONString(openClose), redisCache.getCacheObject("uuid").toString());
            } else {
                WebSocketController.sendInfo(JSON.toJSONString(openClose), "connect");
            }
        } catch (Exception e) {
            log.error("刷新通道异常:{}", e.toString());
        }

    }

}
