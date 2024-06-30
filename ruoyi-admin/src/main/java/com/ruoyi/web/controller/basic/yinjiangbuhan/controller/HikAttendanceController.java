//package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//@RequestMapping("/receive/hik")
//public class HikAttendanceController {
//    //DS-K1T673M 考勤数据接入
//
////    @Resource
////    private WorkDateStorageService workDateStorageService;
////
////    @Resource
////    private ISysUserService userService;
////
////    @Resource
////    RedisTemplate redisTemplate;
//
////    @RequestMapping("/event")
////    public Map event(@RequestParam(value = "Picture", required = false) MultipartFile file
////            , @RequestParam("event_log")String eventLog){
////        HashOperations hashOperations = redisTemplate.opsForHash();
////        log.info("event_log requestParam:{}",eventLog);
////        HttpResponse execute = HttpRequest.post("http://192.168.1.205:8090/receive/hik/event")
////                .form("event_log", eventLog).execute();
////
////        log.info("event_log responseBody:{}",execute.body());
////
////        return null;
////    }
//}