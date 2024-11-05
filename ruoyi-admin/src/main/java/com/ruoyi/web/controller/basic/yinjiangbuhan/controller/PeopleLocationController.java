package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import com.ruoyi.system.mapper.SysWorkPeopleInoutLogMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysConstructionProgressLogService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peopleLocation")
@Slf4j
public class PeopleLocationController {


    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Resource
    SysWorkPeopleService sysWorkPeopleService;

    @Autowired
    private PeopleController peopleController;

    @Autowired
    private SysWorkPeopleInoutLogMapper sysWorkPeopleInoutLogMapper;

    @Autowired
    private ISysConstructionProgressLogService sysConstructionProgressLogService;

    @Autowired
    private IAlarmService alarmService;

    @PostMapping("/inTunnelLocation")
    public Map<String,Object> peopleLocation(@RequestBody Map request){
        Map<String,Object> parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","map_id必传");
            return parse;
        }
        HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        return JSONObject.parseObject(body, Map.class);
    }


    @PostMapping("/outTunnelLocation")
    public Map<String,Object> outPeopleLocation(@RequestBody Map request){
        Map<String,Object> parse = new HashMap();
        if(!request.containsKey("map_id")){
            parse.put("code",500);
            parse.put("msg","map_id必传");
            return parse;
        }
        HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                .body(JSON.toJSONString(request), "application/json")
                .execute();
        String body = execute.body();
        return JSONObject.parseObject(body, Map.class);
    }




    @Scheduled(fixedRate = 2000)
    private void pushSwzkIn() {
        log.info("=========人员定位推送=============");
        String now = DateUtil.now();
        HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                .body(JSON.toJSONString(new Object()), "application/json")
                .execute();
        String body = execute.body();
        Map parse = JSONObject.parseObject(body, Map.class);
        List<Map<String, Object>> datee = (List<Map<String, Object>>) parse.get("data");

        //电子围栏
//        List<Map<String, Object>> sum = sysConstructionProgressLogService.getSum(1, null);
//        BigDecimal zhangzimian = BigDecimal.ZERO;
//        for (Map<String, Object> entry : sum) {
//            zhangzimian = (BigDecimal) entry.get("total_excavation");
//        }
//        int peopleNum = 0;
//        BigDecimal start = zhangzimian.add(new BigDecimal(-5)).setScale(2, RoundingMode.HALF_UP);
//        log.info("掌子面聚集人数判断 开始:{},结束:{}",start, zhangzimian);
//        for (Map<String, Object> map : datee) {
//            BigDecimal resultX = new BigDecimal(2939).add(new BigDecimal(map.get("result_x").toString()));
//            if(resultX.compareTo(start)>0){
//                peopleNum++;
//                log.info("掌子面聚集人数判断 {},距洞口距离:{},peopleNum:{}",((Map<String, Object>) map.get("user_info")).get("user_name").toString(), resultX,peopleNum);
//            }else {
//                log.info("掌子面聚集人数判断 {},距洞口距离:{}", ((Map<String, Object>) map.get("user_info")).get("user_name").toString(), resultX);
//            }
//        }
//        if(peopleNum>9){
//            //TODO 报警
//            Alarm alarm = new Alarm();
//            alarm.setAlarmTypeId(11l);
//            alarm.setAlarmType("人员聚集报警");
//            alarm.setAlarmTime(DateUtil.now());
//            alarm.setAlarmStatus(0);
//            alarm.setYn(1l);
//            alarmService.insertAlarm(alarm);
//        }


        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000040");
        data.put("SN", "renyuandingwei1");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();
        for (Map<String, Object> itemMap : datee) {

            SysWorkPeople one = sysWorkPeopleService.getOne(new LambdaUpdateWrapper<SysWorkPeople>().eq(SysWorkPeople::getIdCard, ((Map<String, Object>) itemMap.get("user_info")).get("number")));



            // 创建 values 列表
            Map<String, Object> valuesObj = new HashMap<>();
            valuesObj.put("reportTs", DateUtil.current());

            // 创建 profile 对象
            Map<String, Object> profileObj = new HashMap<>();
            profileObj.put("appType", "life");
            profileObj.put("modelId", "200017");
            profileObj.put("poiCode", "w0907005");
            profileObj.put("deviceType", "2001000040");

            // 创建 properties 对象
            Map<String, Object> propertiesObj = new HashMap<>();
            propertiesObj.put("ringCode",itemMap.get("tid") );
            //propertiesObj.put("icCode", "");
            //propertiesObj.put("heartRate", "");
            propertiesObj.put("electric", itemMap.get("bat"));
            propertiesObj.put("time", now);
            propertiesObj.put("sos", "0");


            if(one!=null &&one.getWorkType()!=null){
                switch (one.getWorkType()){
                    case "劳务人员": propertiesObj.put("type", "04");
                    case "管理人员": propertiesObj.put("type", "03");
                    default: propertiesObj.put("type", "04");
                }
            }else {
                propertiesObj.put("type", "04");
            }
            propertiesObj.put("stationX", 0);
            propertiesObj.put("stationY", 0);
            propertiesObj.put("stationZ", 0);
            propertiesObj.put("humanX", itemMap.get("result_x"));
            propertiesObj.put("humanY", itemMap.get("result_y"));
            propertiesObj.put("humanZ", itemMap.get("result_z"));
            propertiesObj.put("stationDistance",0);
            //新逻辑 实名制通道 到 洞口距离 推正数 20
            BigDecimal holeDistance = new BigDecimal(2939).add(new BigDecimal(itemMap.get("result_x").toString()));
            if(holeDistance.compareTo(new BigDecimal(-20))>0){
                holeDistance = holeDistance.abs();
            }

            //洞口距离负数的时候 如果没出洞 手动出洞
            if(holeDistance.compareTo(BigDecimal.ZERO)<0){
                if(one!=null) {
                    LambdaQueryWrapper<SysWorkPeopleInoutLog> queryMapper = new LambdaQueryWrapper<>();
                    queryMapper.eq(SysWorkPeopleInoutLog::getIdCard,one.getIdCard());
                    queryMapper.orderByDesc(SysWorkPeopleInoutLog::getLogTime);
                    queryMapper.last("limit 1");
                    SysWorkPeopleInoutLog sysWorkPeopleInoutLog = sysWorkPeopleInoutLogMapper.selectOne(queryMapper);
                    if(sysWorkPeopleInoutLog!=null && sysWorkPeopleInoutLog.getMode()==1) {
                        peopleController.simulationOut(one.getId(),"automatic");
                    }
                }
            }
            propertiesObj.put("holeDistance", holeDistance);
            Object idcard = ((Map<String, Object>) itemMap.get("user_info")).get("number");
            if(idcard.toString().startsWith("GAS") || idcard.toString().startsWith("CAR") ){
                propertiesObj.put("type", "06");
            }
            propertiesObj.put("idCardNumber", idcard);
//                SysWorkPeople one = workPeopleService.getOne(new LambdaQueryWrapper<SysWorkPeople>().eq(SysWorkPeople::getName, ((Map<String, Object>) itemMap.get("user_info")).get("user_name")), false);
//                if(one!=null)
//                    propertiesObj.put("idCardNumber", one.getIdCard());

            propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
            propertiesObj.put("locateMode", "GPS");

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);

        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


    @Scheduled(cron = "0 */1 * * * *")
    private void pushSwzkOut() {
        String now = DateUtil.now();
        HttpResponse execute = HttpRequest.post("http://192.168.1.206:9501/push/list")
                .body(JSON.toJSONString(new Object()), "application/json")
                .execute();
        String body = execute.body();
        Map parse = JSONObject.parseObject(body, Map.class);
        List<Map<String,Object>> datae = (List<Map<String, Object>>) parse.get("data");

        // 创建主数据结构
        Map<String, Object> data = new HashMap<>();
        data.put("deviceType", "2001000041");
        data.put("SN", "renyuandingwei2");
        data.put("dataType", "200300015");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        data.put("workSurface", "YJBH-SSZGX_JGB2_BD-SG-205_ZYM-BT-ZB-08");
        data.put("tunnCode", "100001");
        data.put("deviceName", "洞外人员定位基站");
        List<Map<String, Object>> valuesList = new ArrayList<>();
        for (Map<String, Object> itemMap : datae) {
            // 创建 values 列表

            Map<String, Object> valuesObj = new HashMap<>();
            valuesObj.put("reportTs", DateUtil.current());

            // 创建 profile 对象
            Map<String, Object> profileObj = new HashMap<>();
            profileObj.put("appType", "life");
            profileObj.put("modelId", "200017");
            profileObj.put("poiCode", "w0907005");
            profileObj.put("deviceType", "2001000040");

            // 创建 properties 对象
            Map<String, Object> propertiesObj = new HashMap<>();
//                propertiesObj.put("ringCode", "");
//                propertiesObj.put("icCode", "");
//                propertiesObj.put("heartRate", "");
            propertiesObj.put("electric", itemMap.get("bat"));
            propertiesObj.put("time", now);
            propertiesObj.put("sos", "0");
            propertiesObj.put("type", "01");
            propertiesObj.put("stationX", 0);
            propertiesObj.put("stationY", 0);
            propertiesObj.put("stationZ", 0);
            propertiesObj.put("humanX", 0);
            propertiesObj.put("humanY", 0);
            propertiesObj.put("humanZ", 0);
            propertiesObj.put("stationDistance", 100);
            propertiesObj.put("holeDistance", 0);
            propertiesObj.put("idCardNumber", ((Map<String, Object>) itemMap.get("user_info")).get("number"));
            propertiesObj.put("name", ((Map<String, Object>) itemMap.get("user_info")).get("user_name"));
            propertiesObj.put("locateMode", "GPS");

            // 将 profile 和 properties 对象放入 values 对象中
            valuesObj.put("profile", profileObj);
            valuesObj.put("properties", propertiesObj);
            // 将 values 对象放入 values 列表中
            valuesList.add(valuesObj);

        }
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);
    }


}
