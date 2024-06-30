package com.ruoyi.web.controller.basic.yinjiangbuhan.utils;

import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.netty.TcpClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SwzkHttpUtils {

    public String pushIOT(Map param){
        param.put("bidCode","YJBH-SSZGX_BD-SG-205"); //土建4标
        log.info("push tcp swzk:{}",JSON.toJSONString(param));
        String s = TcpClientService.sendTcpRequest(JSON.toJSONString(param));
        log.info("push tcp swzk response:{}",s);
        return s;

    }
}
