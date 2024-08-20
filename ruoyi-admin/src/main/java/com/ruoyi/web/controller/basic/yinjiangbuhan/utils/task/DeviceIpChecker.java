package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysDeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class DeviceIpChecker {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private ISysDeviceLogService sysDeviceLogService;

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
        // 未在线
        if (!reachable) {
            //更新设备表的配置
            device.setIsOnline(0);
            deviceService.updateDevice(device);
            //存储到设备日志表中
            SysDeviceLog sysDeviceLog = convertDeviceToLog(device);
            sysDeviceLogService.insertSysDeviceLog(sysDeviceLog);
        }
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
        log.setIsOnline(Long.valueOf(device.getIsOnline()));
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