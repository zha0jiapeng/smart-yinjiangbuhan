package com.ruoyi.web.controller.basic.yinjiangbuhan.scheduling;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.ThreadPool;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;


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
    private RedisTemplate redisTemplate;

   //@Scheduled(cron = "0 */10 * * * ?")
    public void execute() {

       ThreadPool.executorService.submit(() -> {
           DateTime date = DateUtil.date();
           String now = DateUtil.formatDateTime(date);
           Date date1 = DateUtils.addMinutes(date, -10);
           String pre = DateUtil.formatDateTime(date1);
           String dateKey = DateUtil.format(date1, "yyyyMMdd");
           logger.info("=========门禁通行事件===========");
           DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
           EventsRequest eventsRequest = new EventsRequest(); //查询门禁事件
           eventsRequest.setPageNo(1); // 显示最后一个人
           eventsRequest.setPageSize(400);
           eventsRequest.setStartTime(getISO8601TimestampFromDateStr(pre));
           eventsRequest.setEndTime(getISO8601TimestampFromDateStr(now));
//           ArrayList<String> indexcodList = new ArrayList<String>();
//           indexcodList.add("ec8d96058dcb4dcca04468080c9570aa");
//           eventsRequest.setDoorIndexCodes(indexcodList); // 所有门禁标识
           logger.info("...门禁事件入参{}",JSON.toJSONString(eventsRequest));
           String doorcount = doorFunctionApi.events(eventsRequest);//查询门禁事件V2

           JSONObject jsonObject = JSONObject.parseObject(doorcount);
           JSONArray list = (JSONArray) ((JSONObject) jsonObject.get("data")).get("list");
           for (int i = 0; i < list.size(); i++) {
               JSONObject object = (JSONObject) list.get(i);
               object.put("picUri","https://192.168.1.207"+object.get("picUri"));
               logger.info("...门禁事件返回值{}",JSON.toJSONString(object));
               ListOperations<String, String> listOps = redisTemplate.opsForList();
               listOps.rightPush(dateKey, object.toJSONString());
//               Map<String, Object> map = new HashMap<>();
//               map.put("portal_id", "1751847977770553345");
//               map.put("device_code", object.get("doorIndexCode").toString());//
//               map.put("device_status", "在线");
//               String url = "http://192.168.30.151" + object.get("picUri");
//               map.put("record_Image_file", url);
//               map.put("id_card", object.get("certNo"));
//               map.put("in_out_direction", object.get("inAndOutType").toString().equals("1") ? "进" : "出");
//               map.put("attendance_out_time","");
//               map.put("attendance_in_time","");
//               if (object.get("inAndOutType").toString().equals("1")) {
//                   map.put("attendance_in_time", getDateStrFromISO8601Timestamp(object.get("receiveTime").toString()));
//               } else {
//                   map.put("attendance_out_time", getDateStrFromISO8601Timestamp(object.get("receiveTime").toString()));
//               }
//               map.put("type", "1");
//               map.put("push_time", now);
   //            listt.add(map);
           }
//           request.put("values", listt);
//           logger.info("...推送业主入参{}",JSON.toJSONString(request));
//           if (listt.size() == 0) return;
//           String url = "http://10.0.100.23:18080/sdata/rest/dataservice/rest/standard/a01fa438-65cf-4da3-9bad-88a7878d0910";
//           HttpResponse execute = HttpRequest.put(url).body(JSON.toJSONString(request), "application/json").execute();
//           String body1 = execute.body();
//           logger.info("...返回值{}",JSON.toJSONString(body1));
       });
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
        DateTimeFormatter dtf1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        org.joda.time.DateTime dt= dtf1.parseDateTime(ISOdate);
        DateTimeFormatter dtf2= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dt.toString(dtf2);
    }

    public static void main(String[] args) {
        DateTime date = DateUtil.date();
        String now = DateUtil.formatDateTime(date);
        Date date1 = DateUtils.addMinutes(date, 1);
        String pre = DateUtil.formatDateTime(date1);
        System.out.println(now);
        System.out.println(pre);
        System.out.println(getISO8601TimestampFromDateStr(DateUtil.now()));
        System.out.println(getDateStrFromISO8601Timestamp("2024-06-17T20:15:01.111+08:00"));
    }
}


