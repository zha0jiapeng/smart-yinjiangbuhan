package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysEvents;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik.Event;
import com.ruoyi.web.controller.basic.yinjiangbuhan.scheduling.DoorEvent;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysEventsService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 设备Controller
 *
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/ai/events")
public class HikEventController extends BaseController {
    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Autowired
    private ISysEventsService sysEventsService;


    @PostMapping("/push")
    public void push() {
        Event event = new Event();
        event.setEventDest("http://192.168.1.153:8097/ai/events/receiveData");
        int eventTypes[] = {1417219};
        event.setEventTypes(eventTypes);
        EventApi eventApi = new EventApi();
        String url = eventApi.eventSubscriptionByEventTypes(event);
        System.out.println(url);
    }

    @RequestMapping(value = "/receiveData", method = RequestMethod.POST, consumes = "application/json")
    public void receiveData(@RequestBody String data) {


        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化输出json
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
        String prettyData = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue);
//        System.out.println(simpleDateFormat.format(date) + "收到数据:" + System.lineSeparator() + prettyData);
        com.alibaba.fastjson.JSONObject prettyDataJSON = JSON.parseObject(prettyData);
        com.alibaba.fastjson.JSONObject params = JSON.parseObject(prettyDataJSON.get("params").toString());
        //数据报告时间
        String time = params.get("sendTime").toString();
        com.alibaba.fastjson.JSONArray events = JSON.parseArray(params.get("events").toString());
        for (Object object : events) {
            SysEvents sysEvents = new SysEvents();
            com.alibaba.fastjson.JSONObject event = (com.alibaba.fastjson.JSONObject) object;
            //设备名称
            String srcName = event.get("srcName").toString();
            if (!srcName.equals("土建4标-洞内-钢筋台车【AI球机】")) {
                continue;
            }
            //摄像头名称
            sysEvents.setCameraName(srcName);
            com.alibaba.fastjson.JSONObject eventData = JSON.parseObject(event.get("data").toString());
            //图片路径
            // 提取 "safetyHelmetDetection" 数组
            com.alibaba.fastjson.JSONArray safetyHelmetDetectionArray = eventData.getJSONArray("safetyHelmetDetection");
            // 提取第一个元素的 "imageUrl"
            String imageUrl = "";
            if (safetyHelmetDetectionArray != null && !safetyHelmetDetectionArray.isEmpty()) {
                com.alibaba.fastjson.JSONObject firstElement = safetyHelmetDetectionArray.getJSONObject(0);
                imageUrl = firstElement.getString("imageUrl");
            }
            //报警信息所属的摄像头编号
            String channelID = "33";
            //摄像头编号
            sysEvents.setCameraNum(channelID);
            //报警信息所属的摄像头名称
            String channelName = "土建4标-洞内-钢筋台车【AI球机】";

            //报警信息所属的摄像头ip(eventData.get("ipAddress"))
            String ipAddress = "192.168.1.80";
            sysEvents.setCameraIp(ipAddress);

            sysEvents.setCameraState(1L);
            sysEvents.setMonitorTime(time);
            sysEvents.setAlertType("未佩戴安全帽");
            sysEvents.setBidCode("YJBH-SSZGX_BD-SG-205");
            sysEvents.setWorkAreaCode("YJBH-SSZGX_GQ-08");
            sysEvents.setImageUrl(imageUrl);
            sysEvents.setRawData(prettyData);

            sysEventsService.insertSysEvents(sysEvents);

            //只要了未带安全帽，且srcName是土建4标-洞内-钢筋台车【AI球机】
            com.alibaba.fastjson.JSONObject jsonObject1 = new com.alibaba.fastjson.JSONObject();
            jsonObject1.put("deviceType", "2001000065");////设备类型，见1.1章节
            jsonObject1.put("SN", "DS-2DF8C440WZW-HK20240621CCCHFG2651348");//设备SN号,必填
            jsonObject1.put("dataType", "200300026");
            jsonObject1.put("bidCode", "YJBH-SSZGX_BD-SG-205");//标段编码
            jsonObject1.put("workAreaCode", "YJBH-SSZGX_GQ-08");//工区编码
            jsonObject1.put("deviceName", srcName);   //srcName
            com.alibaba.fastjson.JSONArray values = new com.alibaba.fastjson.JSONArray();
            com.alibaba.fastjson.JSONObject valuesJSON = new com.alibaba.fastjson.JSONObject();
            valuesJSON.put("reportTs", time);//数据报告时间，时间戳params-events-data-dateTime
            com.alibaba.fastjson.JSONObject valuesJSONprofile = new com.alibaba.fastjson.JSONObject();
            valuesJSONprofile.put("appType", "AI_BOX");//固定值，不用改
            valuesJSONprofile.put("modelId", "3091");//固定值，不用改
            valuesJSONprofile.put("poiCode", "w2309051");//固定值，不用改
            valuesJSONprofile.put("name", "");
            valuesJSONprofile.put("model", "DS-2DF8C440WZW");//AI盒子的型号
            valuesJSONprofile.put("manufacture", "深圳市机械制造有限公司");//AI盒子的制造厂商
            valuesJSONprofile.put("owner", "江汉水网公司");////设备的产权人
            valuesJSONprofile.put("makeDate", "2020-05-22");////设备制造日期
            valuesJSONprofile.put("validYear", "2050-05-22");////设备有效期
            valuesJSONprofile.put("installPosition", "");////安装位置
            valuesJSON.put("profile", valuesJSONprofile);
            com.alibaba.fastjson.JSONObject valuesJSONproperties = new com.alibaba.fastjson.JSONObject();
            valuesJSON.put("properties", valuesJSONproperties);
            com.alibaba.fastjson.JSONObject valuesJSONservices = new com.alibaba.fastjson.JSONObject();
            valuesJSON.put("services", valuesJSONservices);
            com.alibaba.fastjson.JSONObject valuesJSONevents = new com.alibaba.fastjson.JSONObject();
            valuesJSONevents.put("state", "1");//设备状态，1在线，0离线
            valuesJSONevents.put("alert_type", "HELMET");//报警类型，抽烟、明火，未佩戴安全帽等，开发方编码定义
            valuesJSONevents.put("camera_num", channelID);//报警信息所属的摄像头编号  channelID
            valuesJSONevents.put("camera_name", channelName);//报警信息所属的摄像头名称  channelName
            valuesJSONevents.put("monitorTime", time); //格式：yyyy-MM-dd HH:mm:ss，报警时间 para   ms-events-data-dateTime
            valuesJSONevents.put("pic_name", ""); //图片名称
            valuesJSONevents.put("pic_path", imageUrl); //图片路径 imageUrl
            valuesJSONevents.put("srcpic_name", "");
            valuesJSONevents.put("srcpic_path", "");
            com.alibaba.fastjson.JSONObject valuesJSONeventsAlgorithmData = new com.alibaba.fastjson.JSONObject();
            valuesJSONeventsAlgorithmData.put("is_alert", "");
            valuesJSONeventsAlgorithmData.put("target_count", "");
            com.alibaba.fastjson.JSONArray valuesJSONeventsAlgorithmDataTargetInfo = new com.alibaba.fastjson.JSONArray();
            com.alibaba.fastjson.JSONObject valuesJSONeventsAlgorithmDataTargetInfoJSON = new com.alibaba.fastjson.JSONObject();
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("x", "");
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("y", "");
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("height", "");
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("width", "");
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("confidence", "");
            valuesJSONeventsAlgorithmDataTargetInfoJSON.put("name", "");
            valuesJSONeventsAlgorithmDataTargetInfo.add(valuesJSONeventsAlgorithmDataTargetInfoJSON);
            valuesJSONeventsAlgorithmData.put("target_info", valuesJSONeventsAlgorithmDataTargetInfo);
            valuesJSONevents.put("algorithm_data", valuesJSONeventsAlgorithmData);
            com.alibaba.fastjson.JSONObject valuesJSONeventsModelData = new com.alibaba.fastjson.JSONObject();
            com.alibaba.fastjson.JSONArray valuesJSONeventsModelDataObjects = new JSONArray();
            com.alibaba.fastjson.JSONObject valuesJSONeventsModelDataObjectsJSON = new com.alibaba.fastjson.JSONObject();
            valuesJSONeventsModelDataObjectsJSON.put("x", "");
            valuesJSONeventsModelDataObjectsJSON.put("y", "");
            valuesJSONeventsModelDataObjectsJSON.put("height", "");
            valuesJSONeventsModelDataObjectsJSON.put("width", "");
            valuesJSONeventsModelDataObjectsJSON.put("confidence", "");
            valuesJSONeventsModelDataObjectsJSON.put("name", "");
            valuesJSONeventsModelDataObjects.add(valuesJSONeventsModelDataObjectsJSON);
            valuesJSONeventsModelDataObjectsJSON = new JSONObject();
            valuesJSONeventsModelDataObjectsJSON.put("x", "");
            valuesJSONeventsModelDataObjectsJSON.put("y", "");
            valuesJSONeventsModelDataObjectsJSON.put("height", "");
            valuesJSONeventsModelDataObjectsJSON.put("width", "");
            valuesJSONeventsModelDataObjectsJSON.put("confidence", "");
            valuesJSONeventsModelDataObjectsJSON.put("name", "");
            valuesJSONeventsModelDataObjects.add(valuesJSONeventsModelDataObjectsJSON);
            valuesJSONeventsModelData.put("objects", valuesJSONeventsModelDataObjects);
            valuesJSONevents.put("model_data", valuesJSONeventsModelData);
            valuesJSON.put("events", valuesJSONevents);
            values.add(valuesJSON);
            jsonObject1.put("values", values);
            Map<String, Object> map = jsonObjectToMap(jsonObject1);
            swzkHttpUtils.pushIOT(map);
        }
    }


    public static Map<String, Object> jsonObjectToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                map.put(key, jsonObjectToMap((JSONObject) value));
            } else if (value instanceof com.alibaba.fastjson.JSONArray) {
                map.put(key, value); // 简单处理数组类型，如有需要可进一步处理
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
