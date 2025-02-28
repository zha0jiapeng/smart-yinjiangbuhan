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

@RestController
@RequestMapping("/gasDetection")
public class GasDetectionController {

    @Resource
    IDeviceService deviceService;
    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @RequestMapping("/list")
    public AjaxResult list(String sn) {
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getSn, sn).eq(Device::getDeviceType, "GASDETECTOR").eq(Device::getYn, 1));
        Map<String, Object> item = new HashMap<>();
        item.put("sn", device.getSn());
        ModbusMaster master = new ModbusTcpMaster().getSlave(device.getDeviceIp(), device.getDevicePort());
        Number temp = Modbus4jReadUtil.readHoldingRegister(master, 1, 0, DataType.TWO_BYTE_INT_UNSIGNED, "温度");
        Number humi = Modbus4jReadUtil.readHoldingRegister(master, 1, 1, DataType.TWO_BYTE_INT_UNSIGNED, "湿度");
        Number dust = Modbus4jReadUtil.readHoldingRegister(master, 1, 5, DataType.TWO_BYTE_INT_UNSIGNED, "粉尘");
        Number o2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 10, DataType.TWO_BYTE_INT_UNSIGNED, "氧气");
        Number ch4 = Modbus4jReadUtil.readHoldingRegister(master, 1, 11, DataType.TWO_BYTE_INT_UNSIGNED, "甲烷");
        Number co = Modbus4jReadUtil.readHoldingRegister(master, 1, 12, DataType.TWO_BYTE_INT_UNSIGNED, "一氧化碳");
        Number h2s = Modbus4jReadUtil.readHoldingRegister(master, 1, 13, DataType.TWO_BYTE_INT_UNSIGNED, "硫化氢");
        Number co2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 14, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化碳");
        Number so2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 15, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化硫");
        Number nh3 = Modbus4jReadUtil.readHoldingRegister(master, 1, 16, DataType.TWO_BYTE_INT_UNSIGNED, "氨气");
        Number no2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 21, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化氮");
        item.put("temp", new BigDecimal(temp.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("humi", new BigDecimal(humi.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("dust", dust.doubleValue());
        item.put("o2", new BigDecimal(o2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("ch4", new BigDecimal(ch4.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("co", new BigDecimal(co.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("h2s", new BigDecimal(h2s.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("co2", co2.doubleValue());
        item.put("so2", new BigDecimal(so2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("nh3", new BigDecimal(nh3.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
        item.put("no2", new BigDecimal(no2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));

        return AjaxResult.success(item);
    }


    @Autowired
    RedisCache redisCache;

    @Scheduled(cron = "0 */1 * * * *")
    private void pushSwzk() {
        List<Device> gasdetector = deviceService.list(new LambdaUpdateWrapper<Device>().eq(Device::getDeviceType, "GASDETECTOR"));
        for (Device device : gasdetector) {

            String ipAddress = device.getDeviceIp(); // 可以替换为具体的 IP 地址或域名
            int timeout = 3000; // 超时时间设置为 3000 毫秒（3 秒）
            try {
                InetAddress address = InetAddress.getByName(ipAddress);
                boolean reachable = address.isReachable(timeout);
                if (!reachable) {
                    System.out.println("气体检测仪设备网络不通：" + ipAddress);
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            ModbusMaster master = new ModbusTcpMaster().getSlave(device.getDeviceIp(), device.getDevicePort());
            Map<String, Object> item = new HashMap<>();
            Number temp = Modbus4jReadUtil.readHoldingRegister(master, 1, 0, DataType.TWO_BYTE_INT_UNSIGNED, "温度");
            Number humi = Modbus4jReadUtil.readHoldingRegister(master, 1, 1, DataType.TWO_BYTE_INT_UNSIGNED, "湿度");
            Number dust = Modbus4jReadUtil.readHoldingRegister(master, 1, 5, DataType.TWO_BYTE_INT_UNSIGNED, "粉尘");
            Number o2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 10, DataType.TWO_BYTE_INT_UNSIGNED, "氧气");
            Number ch4 = Modbus4jReadUtil.readHoldingRegister(master, 1, 11, DataType.TWO_BYTE_INT_UNSIGNED, "甲烷");
            Number co = Modbus4jReadUtil.readHoldingRegister(master, 1, 12, DataType.TWO_BYTE_INT_UNSIGNED, "一氧化碳");
            Number h2s = Modbus4jReadUtil.readHoldingRegister(master, 1, 13, DataType.TWO_BYTE_INT_UNSIGNED, "硫化氢");
            Number co2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 14, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化碳");
            Number so2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 15, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化硫");
            Number nh3 = Modbus4jReadUtil.readHoldingRegister(master, 1, 16, DataType.TWO_BYTE_INT_UNSIGNED, "氨气");
            Number no2 = Modbus4jReadUtil.readHoldingRegister(master, 1, 21, DataType.TWO_BYTE_INT_UNSIGNED, "二氧化氮");
            item.put("temp", new BigDecimal(temp.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("humi", new BigDecimal(humi.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            if (device.getId() == 52) {
                redisCache.setCacheObject("temp", item.get("temp"));
                redisCache.setCacheObject("humi", item.get("humi"));
            } else {
                item.put("temp", redisCache.getCacheObject("temp"));
                item.put("humi", redisCache.getCacheObject("humi"));
            }

            item.put("dust", dust.doubleValue());
            item.put("o2", new BigDecimal(o2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("ch4", new BigDecimal(ch4.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("co", new BigDecimal(co.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("h2s", new BigDecimal(h2s.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("co2", co2.doubleValue());
            item.put("so2", new BigDecimal(so2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("nh3", new BigDecimal(nh3.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            item.put("no2", new BigDecimal(no2.doubleValue()).multiply(new BigDecimal(0.1)).setScale(1, RoundingMode.HALF_UP));
            push(item, device);
        }

    }

    private void push(Map<String, Object> item, Device device) {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", device.getSn());
        swzkParam.put("dataType", "200300025"); //有毒有害气体
        swzkParam.put("deviceType", "2001000060"); //有毒有害气体
        swzkParam.put("workAreaCode", "YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType", "environment");
        profile.put("modelId", "2055");
        profile.put("poiCode", "w0907001");
        profile.put("name", device.getDeviceName());
        profile.put("model", "");
        profile.put("manufacture", "");
        profile.put("owner", "");
        profile.put("makeDate", "2024-06-25");
        profile.put("validYear", "2024-06-25");
        profile.put("status", "01");
        profile.put("installPosition", device.getDeviceArea());
        profile.put("x", "0");
        profile.put("y", "0");
        profile.put("z", "0");
        map.put("profile", profile);
        Map<String, Object> properties = new HashMap<>();
        properties.put("monitorTime", DateUtil.now());

        properties.put("CO", item.get("co"));
        properties.put("CO2", item.get("co2"));
        properties.put("SO2", item.get("so2"));
        properties.put("SO", item.get("so"));
        properties.put("CH4", item.get("ch4"));
        properties.put("O2", item.get("o2"));
        properties.put("S2H", item.get("h2s"));
        properties.put("TEMPERATURE", item.get("temp"));
        properties.put("HUMIDNESS", item.get("humi"));
        properties.put("location", "1");
        properties.put("x", "0");
        properties.put("y", "0");
        properties.put("z", "0");
        map.put("properties", properties);
        map.put("events", new Object());
        map.put("services", new Object());

        valus.add(map);
        swzkParam.put("values", valus);

        swzkHttpUtils.pushIOT(swzkParam);
    }

}
