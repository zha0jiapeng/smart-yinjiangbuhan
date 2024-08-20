package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class DeviceIpChecker {

    @Autowired
    private IDeviceService deviceService;

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
            device.setIsOnline(0);
            deviceService.updateDevice(device);
        }

    }
}