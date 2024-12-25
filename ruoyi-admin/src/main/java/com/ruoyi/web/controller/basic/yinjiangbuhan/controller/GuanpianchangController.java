package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/guanpianchang")
public class GuanpianchangController {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SwzkHttpUtils swzkHttpUtils;



    @PostMapping("/pushData")
    public Map<String,Object> banhezhan(@RequestBody Map<String,Object> request) {
        //redisTemplate.opsForValue().set("banhezhan_request",JSON.toJSONString(request));
        log.info("swzk guanpianchang push request:{}",JSON.toJSONString(request));
        //String returnMsg = swzkHttpUtils.pushIOT(request);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","Data sent successfully !");
        return map;
    }


}
