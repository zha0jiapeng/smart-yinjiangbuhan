package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/holePeopleAccess")
public class HolePeopleAccessController {
    @RequestMapping("/push")
    public Map<String,Object> push(@RequestBody Map<String,String> map){
        log.info("收到请求,map:{}",map);
        Map<String,Object> result =new HashMap<>();
        result.put("result",0);
        return result;
    }

}
