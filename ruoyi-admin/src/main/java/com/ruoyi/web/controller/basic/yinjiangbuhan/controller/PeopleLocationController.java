package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.TuhuguancheUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peopleLocation")
public class PeopleLocationController {

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/inTunnelLocation")
    public Map peopleLocation(@RequestBody Map request){
        Map parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","mapId必传");
            return parse;
        }
        HttpResponse execute = HttpRequest.post("192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        parse = JSONObject.parseObject(body, Map.class);
        List<Map<String,Object>> data = (List<Map<String, Object>>) parse.get("data");
        redisTemplate.opsForValue().set("peopleLocation",JSON.toJSONString(data));
        return parse;
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void pushSwzk() {
        List<Map<String,Object>> peopleLocation = (List<Map<String, Object>>) redisTemplate.opsForValue().get("peopleLocation");
        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000040");
        data.put("SN", "");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();
        for (Map<String, Object> itemMap : peopleLocation) {
            // 创建 values 列表

            Map<String, Object> valuesObj = new HashMap<>();
            valuesObj.put("reportTs", DateUtil.current());

            // 创建 profile 对象
            Map<String, Object> profileObj = new HashMap<>();
            profileObj.put("appType", "life");
            profileObj.put("modelId", "200017");
            profileObj.put("poiCode", "w0907005");
            profileObj.put("deviceType", "2001000040");

            // 创建 properties 对象
            Map<String, Object> propertiesObj = new HashMap<>();
            propertiesObj.put("ringCode", "");
            propertiesObj.put("icCode", "");
            propertiesObj.put("heartRate", "");
            propertiesObj.put("electric", itemMap.get("bat"));
            propertiesObj.put("time", DateUtil.format(DateUtil.date(((Long)itemMap.get("time"))), DatePattern.NORM_DATETIME_PATTERN));
            propertiesObj.put("sos", "0");
            propertiesObj.put("type", "01");
            propertiesObj.put("stationX", 0);
            propertiesObj.put("stationY", 0);
            propertiesObj.put("stationZ", 0);
            propertiesObj.put("humanX", 0);
            propertiesObj.put("humanY", 0);
            propertiesObj.put("humanZ", 0);
            propertiesObj.put("stationDistance", 2939+(Integer)itemMap.get("result_x"));
            propertiesObj.put("holeDistance", 0);
            propertiesObj.put("idCardNumber", "");
            propertiesObj.put("name", ((Map<String,Object>)itemMap.get("user_info")).get("user_name"));
            propertiesObj.put("locateMode", "GPS");

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);

        }
        data.put("values", valuesList);
        SwzkHttpUtils.pushIOT(data);
    }


}
