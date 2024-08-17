package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.MinioUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import com.ruoyi.system.mapper.SysWorkPeopleInoutLogMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.ruoyi.web.core.config.MinioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/holePeopleAccess")
public class HolePeopleAccessController {

    @Resource
    SwzkHttpUtils swzkHttpUtils;
    @Resource
    SysWorkPeopleInoutLogMapper sysWorkPeopleInoutLogMapper;
    @Resource
    SysWorkPeopleService workPeopleService;

    @Resource
    MinioUtils minioUtils;

    @Resource
    MinioConfig minioConfig;

    @RequestMapping("/push")
    public Map<String, Object> push(@RequestBody Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        if(StringUtils.isEmpty(map.get("name").toString())) {
            result.put("result", 0);
            result.put("message", "ok");
            return result;
        }
        pushSwzk(map);
        saveLog(map);
        result.put("result", 0);
        result.put("message", "ok");
        return result;
    }

    private void saveLog(Map<String, Object> map) {
        SysWorkPeopleInoutLog sysWorkPeopleInoutLog = new SysWorkPeopleInoutLog();
        SysWorkPeople workPeople = workPeopleService.getOne(
                new LambdaQueryWrapper<SysWorkPeople>()
                        .eq(SysWorkPeople::getIdCard, map.get("idNum")));
        if(workPeople!=null ) {
            sysWorkPeopleInoutLog.setSysWorkPeopleId(workPeople.getId());
        }
        sysWorkPeopleInoutLog.setSn(map.get("SN").toString());
        sysWorkPeopleInoutLog.setIdCard(map.get("idNum").toString());
        sysWorkPeopleInoutLog.setMode(Integer.parseInt(map.get("inout").toString()));
        sysWorkPeopleInoutLog.setLogTime(map.get("time").toString());
        sysWorkPeopleInoutLog.setName(map.get("name").toString());
        sysWorkPeopleInoutLog.setPhone(map.get("telephone").toString());
        sysWorkPeopleInoutLog.setPhotoBase64(map.get("face_base64").toString());
        InputStream inputStream = minioUtils.base64ToInputStream(map.get("face_base64").toString());
        String filename = UUID.randomUUID().toString() + ".png";
        minioUtils.uploadFile(minioConfig.getPeopleAccessBucketName(), filename, inputStream);
        String presignedObjectUrl = minioConfig.getEndpoint()+"/"+minioConfig.getPeopleAccessBucketName()+"/"+filename;

        sysWorkPeopleInoutLog.setPhotoUrl(presignedObjectUrl);

        sysWorkPeopleInoutLog.setCreatedDate(new Date());
        sysWorkPeopleInoutLog.setModifyDate(new Date());
        sysWorkPeopleInoutLogMapper.insert(sysWorkPeopleInoutLog);
    }


    private void pushSwzk(Map<String, Object> map) {

        // Create the main map
        Map<String, Object> mainMap = new HashMap<>();

        // Add top-level fields
        mainMap.put("deviceType", "2001000010");
        mainMap.put("SN", map.get("SN"));
        mainMap.put("dataType", "200300003");
        mainMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        mainMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        mainMap.put("deviceName", map.get("devicename"));

        // Create the 'values' list
        List<Map<String, Object>> valuesList = new ArrayList<>();
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("reportTs", DateUtil.current());
        // Create the 'profile' map
        Map<String, Object> profileMap = new HashMap<>();
        profileMap.put("appType", "access_control");
        profileMap.put("modelId", "2053");
        profileMap.put("poiCode", "w0713001");
        profileMap.put("name", "人脸门禁");
        profileMap.put("model", "S3");
        profileMap.put("manufacture", "海康威视");
        profileMap.put("owner", "海康威视");
        profileMap.put("makeDate", "2020-05-22");
        profileMap.put("validYear", "2050-05-22");
        profileMap.put("state", "01");
        profileMap.put("installPosition", "洞口一级门禁");
        profileMap.put("x", 0);
        profileMap.put("y", 0);
        profileMap.put("z", 0);
        profileMap.put("level", "01");
        valuesMap.put("profile", profileMap);
        // Create the 'events' map
        Map<String, Object> eventsMap = new HashMap<>();
        Map<String, Object> passMap = new HashMap<>();
        passMap.put("eventType", 1);
        passMap.put("eventTs", map.get("time"));
        passMap.put("describe", "");
        passMap.put("idCardNumber", map.get("idNum"));
        passMap.put("name", map.get("name"));
        passMap.put("passTime", map.get("time"));
        passMap.put("passDirection", Integer.parseInt(map.get("inout").toString()) == 1 ? "02" : "01");
        eventsMap.put("pass", passMap);

        valuesMap.put("events", eventsMap);
        // Add empty 'properties' and 'services' maps
        valuesMap.put("properties", new HashMap<String, Object>());
        valuesMap.put("services", new HashMap<String, Object>());
        // Add the valuesMap to the valuesList
        valuesList.add(valuesMap);
        mainMap.put("values", valuesList);
        log.info("门禁推送：{}", JSON.toJSONString(mainMap));
        swzkHttpUtils.pushIOT(mainMap);
    }
}