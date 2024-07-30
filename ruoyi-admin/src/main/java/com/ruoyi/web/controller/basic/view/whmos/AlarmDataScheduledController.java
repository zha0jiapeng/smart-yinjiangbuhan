//package com.ruoyi.web.controller.basic.view.whmos;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ruoyi.common.utils.DateToUtils;
//import com.ruoyi.common.utils.DateUtils;
//import com.ruoyi.system.service.AutomaticDetectionService;
//import com.ruoyi.system.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//@RestController
//@RequestMapping("alarm/scheduled")
//public class AlarmDataScheduledController {
//
//    @Autowired
//    private AutomaticDetectionService automaticDetectionService;
//
//    //深圳塘朗山项目Code
//    private String projectCode = "91d54218-eb97-4be5-b799-39d1e176062c";
//
//    /**
//     * 获取位移最新测量数据 (60分钟内)
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/getLatestDataByModule")
//    @Scheduled(cron = "0 0 0/1 * * ?")
//    public void getLatestDataByModule() throws Exception {
//
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("projectCode",projectCode);
//        //查询条件开始时间，结束时间，取最近24小时的时间
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime twentyFourHoursAgo = currentTime.minusHours(24);
//        Date startTime =  DateUtils.toDate(twentyFourHoursAgo);
//        paramMap.put("startDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,startTime));
//        paramMap.put("endDate",DateUtils.getTime());
//        automaticDetectionService.getLatestDataByModule("GetPointDataByModule", paramMap);
//    }
//
//    /**
//     * 获取最新一次测量数据的预警统计
//     * @return
//     */
//    @GetMapping("/getLatestPointAlarmStatistical")
//    @Scheduled(cron = "0 0 0/1 * * ?")
//    public void getLatestPointAlarmStatistical() throws Exception {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("projectCode",projectCode);
//        paramMap.put("pgroupId","");
//
//        //查询条件开始时间，结束时间，取最近24小时的时间
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime twentyFourHoursAgo = currentTime.minusHours(24);
//        Date startTime =  DateUtils.toDate(twentyFourHoursAgo);
//        paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,startTime));
//        paramMap.put("endTime",DateUtils.getTime());
//        automaticDetectionService.getLatestPointAlarmStatistical("GetQzyAlarmStatisticalInfoList", paramMap);
//    }
//
//    /**
//     * 根据时间段统计预警信息
//     * @return
//     */
//    @GetMapping("/getPointAlarmStatisticalByTime")
//    @Scheduled(cron = "0 0 0/1 * * ?")
//    public void getPointAlarmStatisticalByTime() throws Exception {
//
//        //按年查询一次，按时间过滤不同时间段的数据分别保存
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("projectCode",projectCode);
//        paramMap.put("type","Y");
//        getDateParam("Y",paramMap);
//        Result<JSONObject> result = automaticDetectionService.requestWhmosInterface("GetQzyAlarmInfoList", paramMap);
//        paramMap.put("type","D");
//        getDateParam("D",paramMap);
//        automaticDetectionService.getPointAlarmStatisticalByTime(result,paramMap);
//        paramMap.put("type","W");
//        getDateParam("W",paramMap);
//        automaticDetectionService.getPointAlarmStatisticalByTime(result,paramMap);
//        paramMap.put("type","M");
//        getDateParam("M",paramMap);
//        automaticDetectionService.getPointAlarmStatisticalByTime(result,paramMap);
//        paramMap.put("type","Y");
//        getDateParam("Y",paramMap);
//        automaticDetectionService.getPointAlarmStatisticalByTime(result,paramMap);
//    }
//
//    /**
//     * 根据类型判断日期参数
//     * @param type
//     * @param paramMap
//     * @return
//     */
//    private void getDateParam(String type, Map<String, String> paramMap) {
//        switch (type){
//            case "D":
//                //统计今日的，按小时统计
//                paramMap.put("startTime", DateUtils.getDoStartDay());
//                paramMap.put("endTime",DateUtils.getDoEndDay());
//                break;
//            case "W":
//                //统计本周的
//                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfWeek()));
//                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfWeek()));
//                break;
//            case "M":
//                //统计本月的
//                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfMonth()));
//                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfMonth()));
//                break;
//            case "Y":
//                //统计本年的
//                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfYear()));
//                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfYear()));
//                break;
//        }
//    }
//
//}
