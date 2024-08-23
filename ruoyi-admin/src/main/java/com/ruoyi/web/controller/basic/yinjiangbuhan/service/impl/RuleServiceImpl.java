package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Alarm;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AlarmType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Order;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.RuleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.BroadcastAlarmUtil;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangqinghua on 2022/2/26.
 */

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService {

    private final KieBase kieBase;

    @Autowired
    private IAlarmService alarmService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private BroadcastAlarmUtil broadcastAlarmUtil;


    /**
     * 通过规则引擎处理
     *
     * @return
     */
    @Override
    public void executeSignRule(Order order) {
        //去设备表中查出相对应的设备信息
        Device device = deviceService.getById(order.getDeviceId());
        if (device != null) {
            KieSession kieSession = kieBase.newKieSession();
            kieSession.insert(device);
            kieSession.insert(order);
            Alarm alarm = new Alarm();
            kieSession.insert(alarm);
            AlarmType alarmType = new AlarmType();
            kieSession.insert(alarmType);
            kieSession.fireAllRules();
            kieSession.dispose();


            // 查询最新的一条数据
            QueryWrapper<Alarm> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("device_id", alarm.getDeviceId())
                    .orderByDesc("id")
                    .last("LIMIT 1");
            Alarm latestAlarm = alarmService.getOne(queryWrapper);
            boolean shouldInsert = false;
            if (latestAlarm == null || latestAlarm.getAlarmStatus() == 2) {
                shouldInsert = true;
            } else if (latestAlarm.getAlarmStatus() == 0 && !alarm.getAlarmContent().equals(latestAlarm.getAlarmContent())) {
                shouldInsert = true;
            }
            if (shouldInsert) {
                try {
                    //ip广播
//                    if (alarm.getAlarmTypeId() == 1){
//                        broadcastAlarmUtil.startTask(1);
//                    }
                    alarmService.insertAlarm(alarm);
                } catch (Exception e) {
                    System.err.println("插入报警信息时发生异常：" + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("未找到设备信息");
            }
        }
    }
}
