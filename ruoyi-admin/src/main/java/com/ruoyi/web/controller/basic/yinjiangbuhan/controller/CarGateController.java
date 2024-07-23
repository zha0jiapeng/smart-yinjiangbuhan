package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/car")
public class CarGateController {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SwzkHttpUtils swzkHttpUtils;



    @PostMapping("/carAccess")
    public Map<String,Object> carAccess(Map<String,Object> request) {
        log.info("carAccess:{}",JSON.toJSONString(request));
        Object type = request.get("type");
        if(!"online".equals(type.toString())) return null;
        redisTemplate.opsForValue().set("carAccess",JSON.toJSONString(request));
        String dateKey = DateUtil.format(new Date(Long.parseLong(request.get("start_time").toString())), "yyyy-MM-dd");
        // Generate a unique hash key for the record, e.g., using the timestamp
        String hashKey = DateUtil.now();
        redisTemplate.opsForHash().put(dateKey, hashKey, dateKey);
        return request;
    }

    private void pushCarAccess(Map<String, Object> request) {
        // Root map.
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("deviceType", "2001000011");
        rootMap.put("SN", "DSC1010000000YJB002");
        rootMap.put("dataType", "200300004");
        rootMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        rootMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        rootMap.put("deviceName", "8#洞车辆门禁");

        // Values list with one item
        Map<String, Object> valuesItem = new HashMap<>();
        valuesItem.put("reportTs", DateUtil.current());

        // Profile map
        Map<String, Object> profile = new HashMap<>();
        profile.put("appType", "parking");
        profile.put("modelId", "2054");
        profile.put("poiCode", "w0708003");
        profile.put("name", "车辆门禁");
        profile.put("model", "Y500");
        profile.put("manufacture", "机械制造公司");
        profile.put("owner", "江汉水网集团");
        profile.put("makeDate", "2020-05-22");
        profile.put("validYear", "2050");
        profile.put("state", "01");
        profile.put("installPosition", "出口段隧洞口100米处");
        profile.put("x", 0);
        profile.put("y", 0);
        profile.put("z", 0);
        valuesItem.put("profile", profile);

        // Properties map (empty)
        Map<String, Object> properties = new HashMap<>();
        valuesItem.put("properties", properties);

        // Events map with pass event
        Map<String, Object> events = new HashMap<>();
        Map<String, Object> pass = new HashMap<>();
        pass.put("eventType", 1);
        pass.put("eventTs", DateUtil.current());
        pass.put("describe", "");
        pass.put("plateNumber", request.get("plate_num"));
        String formattedDate = DateUtil.format(new Date(Long.parseLong(request.get("start_time").toString())), "yyyy-MM-dd HH:mm:ss");
        pass.put("passTime", formattedDate);
        pass.put("passDirection", "in".equals(request.get("vdc_type")) ? "02" : "01");
        pass.put("passPic", "");
        events.put("pass", pass);
        valuesItem.put("events", events);

        // Services map (empty)
        Map<String, Object> services = new HashMap<>();
        valuesItem.put("services", services);

        // Adding valuesItem to the values list
        rootMap.put("values", new Map[]{valuesItem});

        // Now rootMap contains the structured data
        swzkHttpUtils.pushIOT(rootMap);

    }


}
