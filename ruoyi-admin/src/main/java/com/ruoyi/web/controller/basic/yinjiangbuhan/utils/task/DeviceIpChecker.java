package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Order;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysDeviceLogService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeviceIpChecker {

    @Lazy
    @Autowired
    private IDeviceService deviceService;
    @Lazy
    @Autowired
    private RuleService ruleService;

    @Autowired
    private ISysDeviceLogService sysDeviceLogService;


    private static final Map<String, Integer> IP_TO_DEVICE_ID_MAP = new HashMap<>();

    static {
        IP_TO_DEVICE_ID_MAP.put("192.168.103.192", 79);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.191", 79);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.189", 80);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.190", 80);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.180", 81);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.183", 81);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.182", 82);
        IP_TO_DEVICE_ID_MAP.put("192.168.103.191", 82);
    }


    /**
     * 检查设备ip是否存在
     *
     * @param device
     * @return
     */
    public void ping(Device device) {
        String ipAddress = device.getDeviceIp(); // 可以替换为具体的 IP 地址或域名
        int timeout = 3000; // 超时时间设置为 3000 毫秒（3 秒）
        boolean reachable = false;
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            reachable = address.isReachable(timeout);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // 是否在线
        if (!reachable) {
            SysDeviceLog sysDeviceLog = convertDeviceToLog(device);
            sysDeviceLogService.insertSysDeviceLog(sysDeviceLog);
            device.setIsOnline(0);
        } else {
            device.setIsOnline(1);
        }
        //更新设备表的配置
        Integer targetDeviceId = IP_TO_DEVICE_ID_MAP.get(device.getDeviceIp());
        Long deviceId = device.getId();
        if (targetDeviceId != null) {
            deviceService.updateDevice(device);
            QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", targetDeviceId);
            device = deviceService.getOne(queryWrapper);
            device.setIsOnline(reachable ? 1 : 0);
        }
        Long alarmPoint = device.getId();
        String deviceArea = device.getDeviceArea();
        String deviceName = device.getDeviceName();
        if (device.getIsOnline() == 0) {
            addAlarm(deviceId, alarmPoint, deviceArea, deviceName);
        }
        // 最终更新设备状态
        deviceService.updateDevice(device);
    }

    public void addAlarm(Long deviceId, Long alarmPoint, String deviceArea, String deviceName) {
        //添加报警信息
        Order order = new Order();
        //由于当前信息跟设备表没有对应，只能手动去数据库中查找（sys_device）
        order.setDeviceId(deviceId);
        //由于当前信息跟设备表没有对应，只能手动去数据库中查找（sys_device）
        order.setAlarmPoint(alarmPoint);
        //由于当前信息跟设备表没有对应，只能手动去数据库中查找（alarm_type）
        order.setAlarmTypeId(2L);
        //由于当前信息跟设备表没有对应，只能手动去数据库中查找（alarm_type）
        order.setAlarmType("设备离线报警");
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间
        String formattedDateTime = now.format(formatter);
        order.setAlarmTime(formattedDateTime);
        order.setAlarmCapture("");
        order.setAlarmContent("区域：" + deviceArea + "；报警设备名称：" + deviceName + "；报警内容：" + order.getAlarmType() + "；");
        order.setRemark("");
        ruleService.executeSignRule(order);
    }

    public SysDeviceLog convertDeviceToLog(Device device) {
        SysDeviceLog log = new SysDeviceLog();
        log.setSysDeviceId(device.getId());
        log.setDeviceName(device.getDeviceName());
        log.setDeviceIp(device.getDeviceIp());
        log.setDeviceType(device.getDeviceType());
        log.setDevicePort(device.getDevicePort());
        log.setDeviceArea(device.getDeviceArea());
        log.setProjectName(device.getProjectName());
        log.setConfigJson(device.getConfigJson());
        log.setIsOnline(0L);
        log.setSn(device.getSn());
        log.setCameraType(device.getCameraType());
        log.setSysDeviceCreatedBy(device.getCreatedBy());
        log.setSysDeviceCreatedData(device.getCreatedDate());
        log.setSysDeviceModifyBy(device.getModifyBy());
        log.setSysDeviceModifyDate(device.getModifyDate());
        log.setSysDeviceYn(device.getYn());
        return log;
    }
}