package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SwzkHttpUtils {

    public String pushIOT(Map param){
        param.put("bidCode","YJBH-SSZGX_BD-SG-205"); //土建4标
        log.info("push tcp swzk:{}",JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        //String s = TcpClientService.sendTcpRequest(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue));
        HttpResponse execute = HttpRequest.post("http://58.48.101.155:8089/receive/pushIOT")
                .body(JSON.toJSONString(param,SerializerFeature.WriteMapNullValue), "application/json").execute();
        log.info("push tcp swzk response:{}",execute.body());
        return execute.body();

    }
}
