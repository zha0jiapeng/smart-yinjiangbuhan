package com.ruoyi.web.controller.basic.yinjiangbuhan.scheduling;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * 门禁通行事件
 * event.put("orgname", doorevenDTO.getOrgName());
 * event.put("doorname", doorevenDTO.getDoorName());
 * event.put("owndoor",doorevenDTO.getDevName());
 * event.put("indecodex",doorevenDTO.getDoorIndexCode());
 */


@Component
public class DoorEvent {


    private static final int WELINK_MODEL_ID = 200362;
    private static final Logger logger = LogManager.getLogger(DoorEvent.class);

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Scheduled(cron = "0 */10 * * * ?")
    public void execute() {
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pageNo", "1");
        map.put("pageSize", "400");
        JSONObject returnJson = doorFunctionApi.doorList(map);
        logger.info("...门禁列表:{}", returnJson);
        if(!"0".equals(returnJson.get("code").toString())){
            return;
        }
        JSONObject data = (JSONObject)returnJson.get("data");
        JSONArray list1 = (JSONArray)data.get("list");
        logger.info("...门禁列表list:{}", list1);
        DateTime date = DateUtil.date();
        String now = DateUtil.formatDateTime(date);
        Date date1 = DateUtil.offsetMinute(date, -10);
        String pre = DateUtil.formatDateTime(date1);
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
        pushSwzk(list);

        // });
    }


   private void pushSwzk(JSONArray list){

       // Create the main map
       Map<String, Object> mainMap = new HashMap<>();

       // Add top-level fields
       mainMap.put("deviceType", "2001000010");
       mainMap.put("SN", "DS-K1T673M20231018V031000CHAZ5978013");
       mainMap.put("dataType", "200300003");
       mainMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
       mainMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
       mainMap.put("deviceName", "项目部考勤机");

       // Create the 'values' list
       List<Map<String, Object>> valuesList = new ArrayList<>();
       Map<String, Object> valuesMap = new HashMap<>();
       valuesMap.put("reportTs", DateUtil.current());
       for (int i = 0; i < list.size(); i++) {
           JSONObject jsonObject = list.getJSONObject(i);
           Object personName = jsonObject.get("personName");
           if(personName == null) continue;
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
           profileMap.put("installPosition", "项目部大门");
           profileMap.put("x", 0);
           profileMap.put("y", 0);
           profileMap.put("z", 0);
           profileMap.put("level", "01");
           valuesMap.put("profile", profileMap);
           // Create the 'events' map
           Map<String, Object> eventsMap = new HashMap<>();
           Map<String, Object> passMap = new HashMap<>();
           DateTime eventTime = DateUtil.parse(getDateStrFromISO8601Timestamp(jsonObject.get("eventTime").toString()));
           passMap.put("eventType", 1);
           passMap.put("eventTs", eventTime.getTime());
           passMap.put("describe", "");
           passMap.put("idCardNumber", jsonObject.get("certNo"));
           passMap.put("name", jsonObject.get("personName"));
           passMap.put("passTime", eventTime);
           passMap.put("passDirection",jsonObject.get("inAndOutType").toString().equals("1") ? "01" : "00");
           eventsMap.put("pass", passMap);

           valuesMap.put("events", eventsMap);
           // Add empty 'properties' and 'services' maps
           valuesMap.put("properties", new HashMap<String, Object>());
           valuesMap.put("services", new HashMap<String, Object>());
           // Add the valuesMap to the valuesList
           valuesList.add(valuesMap);
       }
       mainMap.put("values", valuesList);
       logger.info("门禁推送：{}",JSON.toJSONString(mainMap));
       swzkHttpUtils.pushIOT(mainMap);
   }

    public static void main(String[] args) {
        //2024-07-19T12:35:44+08:00
        System.out.println(DateUtil.parse(getDateStrFromISO8601Timestamp("2024-07-19T12:35:44+08:00")));
    }

    public static String getISO8601TimestampFromDateStr(String timestamp){
        java.time.format.DateTimeFormatter dtf1 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(timestamp,dtf1);
        ZoneOffset offset = ZoneOffset.of("+08:00");
        OffsetDateTime date = OffsetDateTime.of(ldt ,offset);
        java.time.format.DateTimeFormatter dtf2 = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        return date.format(dtf2 );
    }

    public static String getDateStrFromISO8601Timestamp(String ISOdate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(ISOdate, formatter);

        return offsetDateTime.toString();
    }

}


