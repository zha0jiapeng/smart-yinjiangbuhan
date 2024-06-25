package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.TuhuguancheUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peopleLocation")
public class PeopleLocationController {


    @PostMapping("/inTunnelLocation")
    public Map carLocation(@RequestBody Map request){
        Map parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","mapId必传");
            return null;
        }
        HttpResponse execute = HttpRequest.post("192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        parse = JSONObject.parseObject(body, Map.class);
        return parse;
    }
}
