package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.json.JSONObject;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.BroadcastAlarmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IP广播报警
 * @author hu_p
 * @date 2024/6/22
 */
@RestController
@RequestMapping("broadcastalarm")
public class BroadcastAlarmController {

    @Autowired
    BroadcastAlarmUtil broadcastAlarmUtil;

    /**
     * 获取广播报警设备
     */
    @GetMapping("device")
    public JSONObject deviceList(){
        JSONObject deviceList = broadcastAlarmUtil.getDeviceList();
        return deviceList;
    }




}
