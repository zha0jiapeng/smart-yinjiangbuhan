//package com.ruoyi;
//
//import cn.hutool.core.date.DateUtil;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ruoyi.common.core.redis.RedisCache;
//import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
//import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
//import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class Springtetst {
//
//    @Resource
//    private IDeviceService deviceService;
//
//    @Resource
//    RedisCache redisCache;
//
//    @Test
//    public void aa(){
//        Date time = DateUtil.date();
//        Map<String,Object> map = new HashMap<>();
//        map.put("pageNo",1);
//        map.put("pageSize",1000);
//        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
//        String cameraList = doorFunctionApi.getCameraList(map);
//        JSONObject jsonObject = JSONObject.parseObject(cameraList);
//        JSONObject data = jsonObject.getJSONObject("data");
//        JSONArray list = data.getJSONArray("list");
//        for (Object datum : list) {
//            JSONObject item = (JSONObject) datum;
//            String cameraIndexCode = item.getString("indexCode");
//            Map<String, Object> cameraData = new HashMap<>();
//            cameraData.put("indexCode", cameraIndexCode);
//            cameraData.put("streamType", 0);
//            cameraData.put("protocol", "ws");
//            cameraData.put("transmode", 1);
//            cameraData.put("expand", "transcode=0");
//            cameraData.put("expireTime", -1);
//            cameraData.put("streamform", "rtp");
//            String body = doorFunctionApi.previewURLs(cameraData);
//            com.alibaba.fastjson2.JSONObject jsonObject2 = com.alibaba.fastjson2.JSONObject.parseObject(body);
//            System.out.println(jsonObject2);
//            String url = jsonObject2.getJSONObject("data").getString("url");
//            Device device = new Device();
//            device.setCameraType(1);
//            device.setDeviceType("CAMERA");
//            device.setConfigJson("{\"rtsp\":\""+url+"\"}");
//            device.setDeviceArea(item.getString("regionName"));
//            device.setDeviceName(item.getString("name"));
//            device.setYn(1l);
//            device.setCreatedDate(time);
//            deviceService.save(device);
//        }
//
//
//    }
//
//
//
//}
