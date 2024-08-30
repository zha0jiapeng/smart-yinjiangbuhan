package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.enums.IndexType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Modbus4jReadUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.ModbusTcpMaster;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
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
    IDeviceService deviceService;
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

    @RequestMapping("/listByThingsBoard/{deviceId}")
    public Map getGasGasDetection2(@PathVariable String deviceId){
        Object thingsboardToken = redisCache.getCacheObject("thingsboard_token");
        if(thingsboardToken==null) {
            String url = "192.168.1.201:8080/api/auth/login";
            Map<String, Object> map = new HashMap<>();
            map.put("username", "1939291579@qq.com");
            map.put("password", "zhao521a.");
            HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(map), "application/json").execute();
            JSONObject jsonObject = JSON.parseObject(execute.body());
            Object token = jsonObject.get("token");
            redisCache.setCacheObject("thingsboard_token",token,2, TimeUnit.HOURS);
        }
        //915b16e0-3069-11ef-b890-e5136757558e
        String url = "http://192.168.1.201:8080/api/plugins/telemetry/DEVICE/"+deviceId+"/values/timeseries";
        HttpResponse execute = HttpRequest.get(url).bearerAuth(thingsboardToken.toString()).execute();
        String body = execute.body();
        return JSON.parseObject(body,Map.class);
    }

    @RequestMapping("/getCurve/{id}")
    public Map getCurve(@PathVariable String id){
        Object thingsboardToken = redisCache.getCacheObject("thingsboard_token");
        if(thingsboardToken==null) {
            String url = "192.168.1.201:8080/api/auth/login";
            Map<String, Object> map = new HashMap<>();
            map.put("username", "1939291579@qq.com");
            map.put("password", "zhao521a.");
            HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(map), "application/json").execute();
            JSONObject jsonObject = JSON.parseObject(execute.body());
            Object token = jsonObject.get("token");
            redisCache.setCacheObject("thingsboard_token",token,2, TimeUnit.HOURS);
        }
        Device byId = deviceService.getById(id);
        if(byId==null) return null;
        String url = "http://192.168.1.201:8080/api/plugins/telemetry/DEVICE/"+byId.getSn()+"/values/timeseries";
        HttpResponse execute = HttpRequest.get(url).bearerAuth(thingsboardToken.toString()).execute();
        String body = execute.body();
        return JSON.parseObject(body,Map.class);
    }

    @Scheduled(cron = "0 */1 * * * *")
    private void pushSwzk() {
        Object thingsboardToken = redisCache.getCacheObject("thingsboard_token");
        if(thingsboardToken==null) {
            String url = "192.168.1.201:8080/api/auth/login";
            Map<String, Object> map = new HashMap<>();
            map.put("username", "1939291579@qq.com");
            map.put("password", "zhao521a.");
            HttpResponse execute = HttpRequest.post(url).body(JSON.toJSONString(map), "application/json").execute();
            JSONObject jsonObject = JSON.parseObject(execute.body());
            Object token = jsonObject.get("token");
            redisCache.setCacheObject("thingsboard_token",token,2, TimeUnit.HOURS);
        }
        List<Device> gasdetector = deviceService.list(new LambdaUpdateWrapper<Device>().eq(Device::getDeviceType, "GASDETECTOR"));
        for (Device device : gasdetector) {
            String url = "http://192.168.1.201:8080/api/plugins/telemetry/DEVICE/"+device.getSn()+"/values/timeseries";
            HttpResponse execute = HttpRequest.get(url).bearerAuth(thingsboardToken.toString()).execute();
            String body = execute.body();
            System.out.println("gas:"+body);
            push(body,device);
        }

    }

    private void push(String body,Device device) {
        Map jsonMap = JSON.parseObject(body, Map.class);

        List<Object> valus = new ArrayList<>();
        List<Map<String,Object>> co = (List<Map<String, Object>>) jsonMap.get("co");
        Long ts = (Long) co.get(0).get("ts");
        Object value = co.get(0).get("value");
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", device.getSn() );
        swzkParam.put("dataType","200300025"); //有毒有害气体
        swzkParam.put("deviceType","2001000060"); //有毒有害气体
        swzkParam.put("workAreaCode","YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType","environment");
        profile.put("modelId","2055");
        profile.put("poiCode","w0907001");
        profile.put("name",device.getDeviceName());
        profile.put("model","");
        profile.put("manufacture","");
        profile.put("owner","");
        profile.put("makeDate","2024-06-25");
        profile.put("validYear","2024-06-25");
        profile.put("status","01");
        profile.put("installPosition",device.getDeviceArea());
        profile.put("x","0");
        profile.put("y","0");
        profile.put("z","0");
        map.put("profile", profile);
        Map<String,Object> properties = new HashMap<>();
        properties.put("monitorTime",DateUtil.format(DateUtil.date(ts),"yyyy-MM-dd HH:mm:ss"));

        properties.put("CO",value);
        properties.put("CO2",jsonMap.containsKey("co2") ? ((List<Map<String, Object>>) jsonMap.get("co2")).get(0).get("value") : "");
        properties.put("SO2",jsonMap.containsKey("so2") ? ((List<Map<String, Object>>)jsonMap.get("so2")).get(0).get("value") : "");
        properties.put("SO", jsonMap.containsKey("so") ? ((List<Map<String, Object>>)jsonMap.get("so")).get(0).get("value") : "");
        properties.put("CH4", jsonMap.containsKey("ch4") ? ((List<Map<String, Object>>)jsonMap.get("ch4")).get(0).get("value") : "");
        properties.put("O2",jsonMap.containsKey("o2") ? ((List<Map<String, Object>>)jsonMap.get("o2")).get(0).get("value") : "");
        properties.put("S2H",jsonMap.containsKey("h2s") ? ((List<Map<String, Object>>)jsonMap.get("h2s")).get(0).get("value") : "");
        properties.put("TEMPERATURE",jsonMap.containsKey("temperature") ? ((List<Map<String, Object>>)jsonMap.get("temperature")).get(0).get("value") : "");
        properties.put("HUMIDNESS",jsonMap.containsKey("humidity") ? ((List<Map<String, Object>>)jsonMap.get("humidity")).get(0).get("value") : "");
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
