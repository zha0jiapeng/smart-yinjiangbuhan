package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Modbus4jReadUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.ModbusTcpMaster;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通风机设备
 */
@RestController
@RequestMapping("/ventilator")
public class VentilatorController {


    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @RequestMapping("/list")
    public AjaxResult list() {
        push();
        return AjaxResult.success();
    }


    @RequestMapping("/test")
    public AjaxResult test() {
        Map<String, Object> item = new HashMap<>();
        ModbusMaster master = new ModbusTcpMaster().getSlave("192.168.103.125", 23);
        Number temp = Modbus4jReadUtil.readHoldingRegister(master, 1, 49999, DataType.TWO_BYTE_INT_UNSIGNED, "温度");
//        item.put("temp", new BigDecimal(temp.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));

        return AjaxResult.success(temp);
    }


    @Scheduled(cron = "0 */3 * * * *")
    private void pushSwzk() {
        push();
    }

    private void push() {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", "ventilator202503110001");
        swzkParam.put("dataType", "200300020"); //通风机
        swzkParam.put("deviceType", "2001000050"); //通风机
        swzkParam.put("workAreaCode", "YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType", "ventilator");
        profile.put("modelId", "2090");
        profile.put("poiCode", "w0823001");
        profile.put("name", "通风机检测");
        profile.put("model", "");
        profile.put("manufacture", "");
        profile.put("owner", "");
        profile.put("makeDate", "2024-06-25");
        profile.put("validYear", "2024-06-25");
        profile.put("status", "01");
        profile.put("installPosition", "8号洞洞口");
        profile.put("x", "0");
        profile.put("y", "0");
        profile.put("z", "0");
        map.put("profile", profile);
        Map<String, Object> properties = new HashMap<>();
        properties.put("monitorTime", DateUtil.now());

        properties.put("is_on", 1);
        properties.put("power", 0);
        properties.put("diameter", 1.2);
        properties.put("rotate_speed", 0);
        properties.put("air_output", 0);
        properties.put("wind_pressure", 0);
        map.put("properties", properties);
        map.put("events", new Object());
        map.put("services", new Object());

        valus.add(map);
        swzkParam.put("values", valus);

        swzkHttpUtils.pushIOT(swzkParam);
    }

}
