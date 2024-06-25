package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.enums.IndexType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Modbus4jReadUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.ModbusTcpMaster;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gasDetection")
public class GasDetectionController {

    @RequestMapping("/list")
    public List<Map<String,Object>> getGasGasDetection(){
        ModbusMaster master = new ModbusTcpMaster().getSlave("192.168.103.178", 6066);
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i =0; i <18;i++) {
            Map<String,Object> map = new HashMap<>();
            Number number = Modbus4jReadUtil.readHoldingRegister(master, 1, i, DataType.TWO_BYTE_INT_UNSIGNED, "");
            Integer flagByType = IndexType.getFlagByType(i);
            String name = IndexType.getNameByType(i);
            Integer value = number.intValue();
            if(flagByType ==0){
                value = new BigDecimal(value).divide(new BigDecimal(10),0, RoundingMode.HALF_UP).intValue();
            }
            map.put(name,value);
        }
        return list;
    }
    @Autowired
    RedisCache redisCache;
    @RequestMapping("/listByThingsBoard")
    public Map getGasGasDetection2(){
        Object thingsboardToken = redisCache.getCacheObject("thingsboard_token");
        if(thingsboardToken==null) {
            String url = "192.168.1.201:8080/api/auth/login";
            Map<String, Object> map = new HashMap();
            map.put("username", "1939291579@qq.com");
            map.put("password", "zhao521a.");
            HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(map), "application/json").execute();
            JSONObject jsonObject = JSON.parseObject(execute.body());
            Object token = jsonObject.get("token");
            redisCache.setCacheObject("thingsboard_token",token,2, TimeUnit.HOURS);
        }
        String url = "http://192.168.1.201:8080/api/plugins/telemetry/DEVICE/915b16e0-3069-11ef-b890-e5136757558e/values/timeseries";
        HttpResponse execute = HttpRequest.get(url).bearerAuth(thingsboardToken.toString()).execute();
        String body = execute.body();
        return JSON.parseObject(body,Map.class);
    }

}
