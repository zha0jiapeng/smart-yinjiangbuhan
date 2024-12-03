package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.IotTbm;
import com.ruoyi.system.service.IotTbmService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysVentilatorMonitorService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class TBMController {


    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Resource
    IotTbmService tbmService;

    @Autowired
    private ISysVentilatorMonitorService sysVentilatorMonitorService;

    @RequestMapping("/getTbm6")
    public AjaxResult getTbm6(){
        IotTbm tbm = tbmService.getOne(new LambdaQueryWrapper<IotTbm>().eq(IotTbm::getDeviceCode, "CREC1463").orderByDesc(IotTbm::getCreatedDate), false);
        return AjaxResult.success(tbm);
    }

    @RequestMapping("/getTbm5")
    public AjaxResult getTbm5(){
        IotTbm tbm = tbmService.getOne(new LambdaQueryWrapper<IotTbm>().eq(IotTbm::getDeviceCode, "CREC1463").orderByDesc(IotTbm::getCreatedDate), false);
        return AjaxResult.success(tbm);
    }


    private static final String url = "https://trans.tbmcloud.com.cn/trans/transGetRealData/ex/getMappingData";

    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        Map<String,Object> request = new HashMap<>();
        request.put("secretKey","z7kZ9eEsFXfIEDsCXfNCrI2coYPYkIawOWIScmi0gC1ywtVm0uKCmQgye+xuRnnWhhAjAs3AUe6F547z1FgkbQ==");
        request.put("tbmCode","CREC1463");
        request.put("name","shibajukjb");
        HttpResponse execute = HttpRequest.post(url)
                .body(JSONObject.toJSONString(request))
                .execute();
        System.out.println(execute.body());
        JSONObject result = JSONObject.parseObject(execute.body());
        JSONObject data = result.getJSONObject("data");

        zuzhuang(data);
    }



    public void zuzhuang(JSONObject jsonObject){

        String timeStr = jsonObject.getString("time_str");
        String tbmCode = jsonObject.getString("tbmCode");
        Long timestamp = jsonObject.getLong("timestamp");
        JSONArray array = jsonObject.getJSONArray("values");
        Map<String, Map<String, Object>> resultMap = array.stream()
                .map(obj -> (JSONObject) obj) // 将每个元素转换为 JSONObject
                .collect(Collectors.toMap(
                        jsonObj -> jsonObj.getString("key"), // 以 "key" 作为 Map 的键
                        JSONObject::getInnerMap             // 将 JSONObject 转为 Map 作为值
                ));


        IotTbm tbm = new IotTbm();
        tbm.setDeviceCode(tbmCode);
        tbm.setCutterTorque(resultMap.get("cutterTorque").get("val").toString());
        tbm.setCutterRev(resultMap.get("cutterRpm").get("val").toString());
        tbm.setStatus(Integer.parseInt(resultMap.get("shieldState").get("val").toString()));
        tbm.setTotalThrustOne(resultMap.get("thrust").get("val").toString());
        tbm.setTotalThrustTwo(resultMap.get("advnPrs").get("val").toString());
        tbm.setCreatedDate(new Date());
        tbmService.save(tbm);



        //气体检测
        pushGasMonitor(resultMap);

        //风机
        saveVentilator(resultMap);
    }

    private void pushGasMonitor(Map<String, Map<String, Object>> resultMap) {
        Map<String, Object> item = new HashMap<>();
       // item.put("dust", dust.doubleValue());
        item.put("o2", new BigDecimal((Double)resultMap.get("mainBeltO2").get("val")));
        item.put("ch4", new BigDecimal((Double)resultMap.get("mainBeltCH4").get("val")));
        item.put("co", new BigDecimal((Double)resultMap.get("mainBeltCO").get("val")));
        item.put("h2s", new BigDecimal((Double)resultMap.get("mainBeltH2S").get("val")));
        item.put("co2", new BigDecimal((Double)resultMap.get("mainBeltCO2").get("val")));
        item.put("so2", new BigDecimal((Double)resultMap.get("mainBeltSO2").get("val")));
        //item.put("nh3", new BigDecimal((Double)resultMap.get("mainBeltO2").get("val")));
        item.put("no2", new BigDecimal((Double)resultMap.get("mainBeltNO2").get("val")));
        item.put("no", new BigDecimal((Double)resultMap.get("mainBeltNO").get("val")));
        item.put("cl2", new BigDecimal((Double)resultMap.get("mainBeltCL2").get("val")));
        push(item);
    }
    private void push(Map<String, Object> item) {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", "" );
        swzkParam.put("dataType","200300025"); //有毒有害气体
        swzkParam.put("deviceType","2001000060"); //有毒有害气体
        swzkParam.put("workAreaCode","YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType","environment");
        profile.put("modelId","2055");
        profile.put("poiCode","w0907001");
        profile.put("name", "主皮带机气体检测");
        profile.put("model","");
        profile.put("manufacture","");
        profile.put("owner","");
        profile.put("makeDate","2024-06-25");
        profile.put("validYear","2024-06-25");
        profile.put("status","01");
        profile.put("installPosition","TBM6主皮带机");
        profile.put("x","0");
        profile.put("y","0");
        profile.put("z","0");
        map.put("profile", profile);
        Map<String,Object> properties = new HashMap<>();
        properties.put("monitorTime",DateUtil.now());

        properties.put("CO",item.get("co"));
        properties.put("CO2",item.get("co2"));
        properties.put("SO2",item.get("so2"));
        properties.put("SO", item.get("so"));
        properties.put("CH4", item.get("ch4"));
        properties.put("O2",item.get("o2"));
        properties.put("S2H",item.get("h2s"));
        properties.put("TEMPERATURE",item.get("temp"));
        properties.put("HUMIDNESS",item.get("humi"));
        properties.put("location","1");
        properties.put("x","0");
        properties.put("y","0");
        properties.put("z","0");
        map.put("properties",properties);
        map.put("events",new Object());
        map.put("services",new Object());

        valus.add(map);
        swzkParam.put("values",valus);

        swzkHttpUtils.pushIOT(swzkParam);
    }


    private void saveVentilator(Map<String, Map<String, Object>> resultMap) {
        SysVentilatorMonitor ventilatorMonitor = new SysVentilatorMonitor();
        ventilatorMonitor.setSn("");
        ventilatorMonitor.setDeviceId(91L);
        ventilatorMonitor.setYn(1L);
        boolean funIsOn1 = (Boolean) resultMap.get("funIsOn1").get("val");
        boolean funIsOn2 = (Boolean) resultMap.get("funIsOn2").get("val");
        ventilatorMonitor.setIsOpen(funIsOn1 || funIsOn2);
        Double funPower1 = (Double) resultMap.get("funPower1").get("val");

        ventilatorMonitor.setPower(new BigDecimal(funPower1));
        ventilatorMonitor.setDiameter(1250L);
        Double funRotateSpeed1 = (Double) resultMap.get("funRotateSpeed1").get("val");
        ventilatorMonitor.setSpeed(new BigDecimal(funRotateSpeed1));
        Double funAirOutput = (Double) resultMap.get("funAirOutput").get("val");
        ventilatorMonitor.setAirSupply(new BigDecimal(funAirOutput));
        ventilatorMonitor.setCreatedDate(new Date());
        sysVentilatorMonitorService.save(ventilatorMonitor);
    }

}


