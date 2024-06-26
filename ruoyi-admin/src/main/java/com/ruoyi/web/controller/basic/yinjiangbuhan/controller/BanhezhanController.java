package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/banhezhan")
public class BanhezhanController {

    @RequestMapping("/pullData")
    public Map banhezhan(@RequestBody Map<String,Object> request) {
        String url = "http://192.168.1.205:8090/receive/pushIOT";
        log.info("swzk banhezhan push request:{}",JSON.toJSONString(request));
        HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(request), "application/json").execute();
        log.info("swzk banhezhan push response:{}",execute.body());
        return JSON.parseObject(execute.body(),Map.class);
    }

}
