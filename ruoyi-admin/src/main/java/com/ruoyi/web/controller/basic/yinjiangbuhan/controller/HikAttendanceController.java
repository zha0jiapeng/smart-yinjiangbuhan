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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

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
        log.info("event_log requestParam:{}",eventLog);
        HttpResponse execute = HttpRequest.post("http://223.76.159.54:8089/receive/hik/event")
                .form("event_log", eventLog).execute();

        log.info("event_log responseBody:{}",execute.body());

        AccessControllerEventWrapper acew = JSON.parseObject(execute.body(), AccessControllerEventWrapper.class);
        log.info("acew:{}",acew);
        AccessControllerEvent accessControllerEvent = acew.getAccessControllerEvent();

        redisTemplate.opsForHash().put("event_log", DateUtil.now(),acew);
        return null;
    }
//    @Scheduled(cron = "")
//    public void scheduled(){
//        DateTime dateTime = new DateTime();
//        Map<String,Object> eventLog = redisTemplate.opsForHash().entries("event_log");
//        //0点更新
//        if(dateTime.getHours()==0){
//            redisTemplate.opsForHash().delete("event_log");
//        }
//        Set<Map.Entry<String, Object>> entries = eventLog.entrySet();
//        for (Map.Entry<String, Object> entry : entries) {
//            String key = entry.getKey();
//            AccessControllerEventWrapper value = (AccessControllerEventWrapper) entry.getValue();
//            SysUser sysUser = userService.selectUserByIdCard(key);
//            if(sysUser!=null){
//                WorkDateStorage workDateStorage = new WorkDateStorage();
//                workDateStorage.setName(sysUser.getNickName());
//                workDateStorage.setPhone(sysUser.getPhonenumber());
//                //workDateStorage.set
//            }
//        }
//
//
//
//    }
}
