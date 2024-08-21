package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysDeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeviceIpChecker {

    @Autowired
    private IDeviceService deviceService;

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
        if (targetDeviceId != null) {
            deviceService.updateDevice(device);
            QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", targetDeviceId);
            device = deviceService.getOne(queryWrapper);
            device.setIsOnline(reachable ? 1 : 0);
        }

        // 最终更新设备状态
        deviceService.updateDevice(device);
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
        log.setCameraType(Long.valueOf(device.getCameraType()));
        log.setSysDeviceCreatedBy(device.getCreatedBy());
        log.setSysDeviceCreatedData(device.getCreatedDate());
        log.setSysDeviceModifyBy(device.getModifyBy());
        log.setModifyDate(device.getModifyDate());
        log.setSysDeviceYn(device.getYn());
        return log;
    }
}