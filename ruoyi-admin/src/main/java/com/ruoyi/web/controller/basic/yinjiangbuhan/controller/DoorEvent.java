package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import com.ruoyi.system.mapper.SysWorkPeopleInoutLogMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 门禁通行事件
 * event.put("orgname", doorevenDTO.getOrgName());
 * event.put("doorname", doorevenDTO.getDoorName());
 * event.put("owndoor",doorevenDTO.getDevName());
 * event.put("indecodex",doorevenDTO.getDoorIndexCode());
 */


@RestController
public class DoorEvent {


    private static final int WELINK_MODEL_ID = 200362;
    private static final Logger logger = LogManager.getLogger(DoorEvent.class);

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    SysWorkPeopleInoutLogMapper sysWorkPeopleInoutLogMapper;

    @Resource
    SysWorkPeopleService workPeopleService;

    @Resource
    IDeviceService deviceService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        Object doorLastTime = redisTemplate.opsForValue().get("door_last_time");
        if (doorLastTime == null) {
            doorLastTime = DateUtil.offsetMinute(new Date(), -10);
        }
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
        DateTime date = DateUtil.date();
        String now = DateUtil.formatDateTime(date);
        Date date1 = DateUtil.parse(doorLastTime.toString());
        String pre = DateUtil.formatDateTime(date1);
        logger.info("=========门禁通行事件===========");

        EventsRequest eventsRequest = new EventsRequest(); //查询门禁事件
        eventsRequest.setPageNo(1); // 显示最后一个人
        eventsRequest.setPageSize(1000);
        eventsRequest.setStartTime(getISO8601TimestampFromDateStr(pre));
        eventsRequest.setEndTime(getISO8601TimestampFromDateStr(now));
        logger.info("...门禁事件入参{}", JSON.toJSONString(eventsRequest));
        String doorcount = doorFunctionApi.events(eventsRequest);//查询门禁事件V2
        JSONObject jsonObject = JSONObject.parseObject(doorcount);
        JSONArray list = (JSONArray) ((JSONObject) jsonObject.get("data")).get("list");
        if (list.size() == 0) return;
        List<Map<String, Object>> lists = jsonArrayToList(list);
        pushSwzk(lists, true, "");
        redisTemplate.opsForValue().set("door_last_time", DateUtil.offsetMinute(date, -10));
    }

    @RequestMapping("/door/pushHik")
    public void execute2(String startTime, String endTime, String sn) {
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
        DateTime date = DateUtil.date();
        String now = DateUtil.formatDateTime(date);
        if (endTime != null) now = endTime;
        Date date1 = DateUtil.offsetMinute(date, -10);
        String pre = DateUtil.formatDateTime(date1);
        if (startTime != null) pre = startTime;
        logger.info("=========门禁通行事件===========");

        EventsRequest eventsRequest = new EventsRequest(); //查询门禁事件
        eventsRequest.setPageNo(1); // 显示最后一个人
        eventsRequest.setPageSize(400);
        eventsRequest.setStartTime(getISO8601TimestampFromDateStr(pre));
        eventsRequest.setEndTime(getISO8601TimestampFromDateStr(now));
        logger.info("...门禁事件入参{}", JSON.toJSONString(eventsRequest));
        String doorcount = doorFunctionApi.events(eventsRequest);//查询门禁事件V2
        JSONObject jsonObject = JSONObject.parseObject(doorcount);
        JSONArray list = (JSONArray) ((JSONObject) jsonObject.get("data")).get("list");
        List<Map<String, Object>> lists = jsonArrayToList(list);
        pushSwzk(lists, true, sn);
    }

    private void pushSwzk(List<Map<String, Object>> list, boolean flag, String snDevIndexCode) {

        // 根据devIndexCode字段分组
        Map<String, List<Map<String, Object>>> groupedByDevIndexCode = list.stream()
                .collect(Collectors.groupingBy(map -> (String) map.get("devIndexCode")));

        for (Map.Entry<String, List<Map<String, Object>>> stringListEntry : groupedByDevIndexCode.entrySet()) {
            String devIndexCode = stringListEntry.getKey();
            if (snDevIndexCode != null && !snDevIndexCode.equals("")) {
                if (!snDevIndexCode.equals(devIndexCode)) {
                    continue;
                }
            }
            List<Map<String, Object>> value = stringListEntry.getValue();
            logger.info("devIndexCode:{} list:{}", devIndexCode, JSON.toJSONString(value));
            JSONObject door = getDoor(devIndexCode);


            // Create the main map
            Map<String, Object> mainMap = new HashMap<>();

            // Add top-level fields
            mainMap.put("deviceType", "2001000010");
            String sn = door.get("devSerialNum").toString();
            sn = sn.substring(sn.length() - 9);
            mainMap.put("SN", sn);
            QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
            deviceQueryWrapper.like("sn", sn);
            Device one = deviceService.getOne(deviceQueryWrapper);
            if (one != null) {
//               Integer cameraType = one.getCameraType();
//               if(cameraType == 1){
//                   continue;
//               }
                if (StringUtils.isNotEmpty(one.getModifyBy())) {
                    mainMap.put("SN", one.getModifyBy());
                } else {
                    sn = one.getSn();
                    mainMap.put("SN", sn);
                }
            } else {

            }

            mainMap.put("dataType", "200300003");
            mainMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
            mainMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
            mainMap.put("deviceName", door.get("name"));

            // Create the 'values' list
            List<Map<String, Object>> valuesList = new ArrayList<>();


            for (Map<String, Object> map : value) {
                Map<String, Object> valuesMap = new HashMap<>();
                valuesMap.put("reportTs", DateUtil.current());
                Object personName = map.get("personName");
                if (personName == null) continue;
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
                profileMap.put("installPosition", "");
                profileMap.put("x", 0);
                profileMap.put("y", 0);
                profileMap.put("z", 0);
                profileMap.put("level", "01");
                valuesMap.put("profile", profileMap);
                // Create the 'events' map
                Map<String, Object> eventsMap = new HashMap<>();
                Map<String, Object> passMap = new HashMap<>();
                DateTime eventTime = DateUtil.parse(getDateStrFromISO8601Timestamp(map.get("eventTime").toString()));
                passMap.put("eventType", 1);
                passMap.put("eventTs", eventTime.getTime());
                passMap.put("describe", "");
                passMap.put("idCardNumber", map.get("certNo").toString().trim().toUpperCase());
                passMap.put("name", map.get("personName"));
                passMap.put("passTime", DateUtil.formatDateTime(eventTime));
                passMap.put("passDirection", map.get("inAndOutType").toString().equals("1") ? "02" : "01");
                eventsMap.put("pass", passMap);

                valuesMap.put("events", eventsMap);
                // Add empty 'properties' and 'services' maps
                valuesMap.put("properties", new HashMap<String, Object>());
                valuesMap.put("services", new HashMap<String, Object>());
                // Add the valuesMap to the valuesList
                valuesList.add(valuesMap);
                if (flag)
                    insertInOutLog(door, map, eventTime);


            }
            mainMap.put("values", valuesList);
            logger.info("门禁推送：{}", JSON.toJSONString(mainMap));
            swzkHttpUtils.pushIOT(mainMap);

        }
    }

    private void insertInOutLog(JSONObject door, Map<String, Object> jsonObject, DateTime eventTime) {
        SysWorkPeopleInoutLog sysWorkPeopleInoutLog = new SysWorkPeopleInoutLog();
        Integer certNo = sysWorkPeopleInoutLogMapper.selectCount(
                new LambdaQueryWrapper<SysWorkPeopleInoutLog>()
                        .eq(SysWorkPeopleInoutLog::getIdCard, jsonObject.get("certNo").toString())
                        .eq(SysWorkPeopleInoutLog::getLogTime, DateUtil.formatDateTime(eventTime))
        );
        if (certNo >= 1) {
            return;
        }
        String sn = door.get("devSerialNum").toString();
        sysWorkPeopleInoutLog.setSn(sn);
        sysWorkPeopleInoutLog.setIdCard(jsonObject.get("certNo").toString());
        sysWorkPeopleInoutLog.setMode(Integer.parseInt(jsonObject.get("inAndOutType").toString()));
        sysWorkPeopleInoutLog.setLogTime(DateUtil.formatDateTime(eventTime));
        sysWorkPeopleInoutLog.setName(jsonObject.get("personName").toString());
        if (jsonObject.get("picUri") != null) {
            sysWorkPeopleInoutLog.setPhotoUrl("http://192.168.1.207" + jsonObject.get("picUri").toString());
        }
        sysWorkPeopleInoutLog.setCreatedDate(new Date());
        sysWorkPeopleInoutLog.setModifyDate(new Date());
        sysWorkPeopleInoutLogMapper.insert(sysWorkPeopleInoutLog);
    }

    private static JSONObject getDoor(String devIndexCode) {
        // 创建根Map
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("pageNo", 1);
        rootMap.put("pageSize", 1);

        // 创建expressions列表
        List<Map<String, Object>> expressionsList = new ArrayList<>();

        // 创建expression Map
        Map<String, Object> expressionMap = new HashMap<>();
        expressionMap.put("key", "indexCode");
        expressionMap.put("operator", 0);

        // 创建values列表
        List<String> valuesList2 = new ArrayList<>();
        valuesList2.add(devIndexCode);

        // 将values列表添加到expression Map中
        expressionMap.put("values", valuesList2);

        // 将expression Map添加到expressions列表中
        expressionsList.add(expressionMap);

        // 将expressions列表添加到根Map中
        rootMap.put("expressions", expressionsList);
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();

        JSONObject JSONObject = doorFunctionApi.search(rootMap);
        JSONArray objects = (JSONArray) ((JSONObject) JSONObject.get("data")).get("list");
        JSONObject door = (JSONObject) objects.get(0);
        return door;
    }

    private static List<Map<String, Object>> jsonArrayToList(JSONArray jsonArray) {
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> map = jsonObject.toJavaObject(Map.class);
            list.add(map);
        }

        return list;
    }

    public static String getISO8601TimestampFromDateStr(String timestamp) {
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(timestamp, dtf1);
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(ldt, offset);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return date.format(dtf2);
    }


//    public static String getDateStrFromISO8601Timestamp(String ISOdate){
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//        OffsetDateTime offsetDateTime = OffsetDateTime.parse(ISOdate, formatter);
//
//        return offsetDateTime.toString();
//    }

    public static String getDateStrFromISO8601Timestamp(String dateTimeStr) {
        // 检查并补全不完整的时间字符串
        if (dateTimeStr.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}\\+\\d{2}:\\d{2}$")) {
            // 如果只有小时信息，补充 :00:00
            dateTimeStr = dateTimeStr.replaceFirst("(\\d{2})\\+(\\d{2}:\\d{2})$", "$1:00:00+$2");
        } else if (dateTimeStr.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\+\\d{2}:\\d{2}$")) {
            // 如果没有秒信息，补充 :00
            dateTimeStr = dateTimeStr.replaceFirst("(\\d{2}:\\d{2})\\+(\\d{2}:\\d{2})$", "$1:00+$2");
        }

        try {
            // 尝试将输入字符串解析为 OffsetDateTime
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr);

            // 将 OffsetDateTime 转换为 LocalDateTime
            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

            // 确保格式为 yyyy-MM-dd HH:mm:ss
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 如果时间中缺少秒或者分钟，需要补充0
            LocalDateTime normalizedDateTime = localDateTime
                    .withSecond(localDateTime.getSecond() == 0 ? 0 : localDateTime.getSecond())
                    .truncatedTo(ChronoUnit.SECONDS); // 去除毫秒

            return normalizedDateTime.format(outputFormatter);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date time format.");
        }
    }


}


