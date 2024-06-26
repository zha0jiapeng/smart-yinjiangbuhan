package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.WorkDateStorage;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.WorkDateStorageService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik.AccessControllerEvent;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik.AccessControllerEventWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/receive/hik")
public class HikAttendanceController {
    //DS-K1T673M 考勤数据接入

    @Resource
    private WorkDateStorageService workDateStorageService;

    @Resource
    private ISysUserService userService;

    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping("/event")
    public Map event(@RequestParam(value = "Picture", required = false) MultipartFile file
            , @RequestParam("event_log")String eventLog){
        HashOperations hashOperations = redisTemplate.opsForHash();
        log.info("event_log requestParam:{}",eventLog);
        HttpResponse execute = HttpRequest.post("http://223.76.159.54:8089/receive/hik/event")
                .form("event_log", eventLog).execute();

        log.info("event_log responseBody:{}",execute.body());

        AccessControllerEventWrapper acew = JSON.parseObject(eventLog, AccessControllerEventWrapper.class);
        log.info("acew:{}",JSON.toJSONString(acew));
        AccessControllerEvent accessControllerEvent = acew.getAccessControllerEvent();
        if(accessControllerEvent.getLabel().equals("下班") || accessControllerEvent.getLabel().equals("上班")){
            String idCard = accessControllerEvent.getEmployeeNoString();
            List<AccessControllerEventWrapper> list = (List<AccessControllerEventWrapper>)hashOperations.get("event_log", idCard);
            if(list==null)  list = new ArrayList<>();
            list.add(acew);
            hashOperations.put("event_log", idCard,JSON.toJSONString(list));
        }

        return null;
    }
    //@Scheduled(cron = "0 0 0 * * *")
    public void scheduled(){
        DateTime dateTime = new DateTime();
        Map<String,Object> eventLog = redisTemplate.opsForHash().entries("event_log");
        Set<Map.Entry<String, Object>> entries = eventLog.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();    //idCard
            List<AccessControllerEventWrapper> value = (List<AccessControllerEventWrapper>) entry.getValue();//List<>
            List<AccessControllerEventWrapper> collect = value.stream()
                    .sorted(Comparator.comparing(bean -> OffsetDateTime.parse(bean.getDateTime())))
                    .collect(Collectors.toList());
            log.info("collect:{}",collect);
        }
    }
}
