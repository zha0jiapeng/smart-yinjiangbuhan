package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SwzkParam;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SwzkHttpUtils {

    public static void pushIOT(Map param){
        param.put("bidCode","YJBH-SSZGX_BD-SG-205"); //土建4标
        String url = "http://192.168.1.205:8090/receive/pushIOT";
        HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(param), "application/json").execute();
        log.info("swzk response:{}",execute.body());

    }
}
