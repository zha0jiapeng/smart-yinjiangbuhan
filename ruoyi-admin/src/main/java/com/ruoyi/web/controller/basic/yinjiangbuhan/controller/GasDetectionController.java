package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.enums.IndexType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Modbus4jReadUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.ModbusTcpMaster;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    SwzkHttpUtils swzkHttpUtils;
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

    @Scheduled(cron = "0 */10 * * * *")
    private void pushSwzk() {
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


        Map map1 = JSON.parseObject(body, Map.class);
        List<Object> valus = new ArrayList<>();
        List<Map<String,Object>> oxygen = (List<Map<String, Object>>) map1.get("oxygen");
        Long ts = (Long) oxygen.get(0).get("ts");
        Object value = oxygen.get(0).get("value");
        Map swzkParam = new HashMap();
        swzkParam.put("SN","youduyouhai1");
        swzkParam.put("dataType","200300025"); //有毒有害气体
        swzkParam.put("deviceType","2001000060"); //有毒有害气体
        swzkParam.put("workAreaCode","YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType","environment");
        profile.put("modelId","2055");
        profile.put("poiCode","w0907001");
        profile.put("name","8#支洞内有毒有害气体监测");
        profile.put("model","型号");
        profile.put("manufacture","");
        profile.put("owner","");
        profile.put("makeDate","2024-06-25");
        profile.put("validYear","2024-06-25");
        profile.put("status","01");
        profile.put("installPosition","");
        profile.put("x","112");
        profile.put("y","112");
        profile.put("z","110");
        map.put("profile", profile);
        Map<String,Object> properties = new HashMap<>();
        properties.put("monitorTime",DateUtil.format(DateUtil.date(ts),"yyyy-MM-dd HH:mm:ss"));

        properties.put("co",value);
        properties.put("co2",((List<Map<String, Object>>)map1.get("carbon_dioxide")).get(0).get("value"));
        properties.put("so2",((List<Map<String, Object>>)map1.get("sulfur_dioxide")).get(0).get("value"));
        properties.put("so",0); //无指标
        properties.put("ch4",((List<Map<String, Object>>)map1.get("methane")).get(0).get("value"));
        properties.put("location","1");
        properties.put("x","0");
        properties.put("y","0");
        properties.put("z","0");
        map.put("properties",properties);
        map.put("events",new Object());
        map.put("services",new Object());

        valus.add(map);
        swzkParam.put("values",valus);

        swzkHttpUtils.pushIOT(swzkParam);
    }

}
