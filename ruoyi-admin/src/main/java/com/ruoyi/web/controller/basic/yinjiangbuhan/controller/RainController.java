package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rain")
public class RainController {

    @RequestMapping
    public AjaxResult getRain(){
        String loginUrl = "";
        HttpResponse execute = HttpRequest.get(loginUrl).execute();
        String body = execute.body();
        JSONObject bodyJson = JSON.parseObject(body);
        Object token = bodyJson.get("token");

        return AjaxResult.success();
    }

}
