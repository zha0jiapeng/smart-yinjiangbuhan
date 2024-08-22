package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.DeviceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component // bean注解
@Slf4j
public class StartUp implements ApplicationRunner {


    @Resource
    DeviceMapper deviceMapper;

    @Autowired
    private DeviceIpChecker deviceIpChecker;

    @Override
    public void run(ApplicationArguments args) {
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
        service.printScheduledTasks();
    }

}
