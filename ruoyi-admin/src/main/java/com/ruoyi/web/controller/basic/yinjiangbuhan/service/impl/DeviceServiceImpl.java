package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.mapper.CarAccessMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.DeviceMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task.DeviceIpChecker;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * 设备Service业务层处理
 *
 * @author mashir0
 * @date 2024-06-23
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
    @Resource
    DeviceMapper deviceMapper;

    @Autowired
    private DeviceIpChecker deviceIpChecker;

    /**
     * 监听设备是否在线
     */
    @PostConstruct
    public void isDeviceOnline() {
        ScheduledTaskService service = new ScheduledTaskService();
        LambdaQueryWrapper<Device> lq = new LambdaQueryWrapper<>();
        lq.eq(Device::getYn, 1);
        List<Device> list = deviceMapper.selectList(lq);
        for (Device v : list) {
            String deviceIp = v.getDeviceIp();
            if (deviceIp != null && StringUtils.isNotEmpty(deviceIp) && !deviceIp.contains(",")) {
                service.addTask(v.getId().toString(), () -> deviceIpChecker.ping(v), 60, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public int updateDevice(Device device) {
        device.setUpdateTime(DateUtils.getNowDate());
        System.out.println(device.toString());
        return deviceMapper.updateById(device);
    }
}
