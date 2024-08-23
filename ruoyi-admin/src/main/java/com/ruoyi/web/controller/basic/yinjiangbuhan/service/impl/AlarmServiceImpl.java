package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Alarm;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.AlarmMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报警Service业务层处理
 *
 * @author liang
 * @date 2024-08-21
 */
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements IAlarmService {
    @Autowired(required = false)
    private AlarmMapper alarmMapper;

    @Autowired
    private IDeviceService deviceService;

    /**
     * 查询报警
     *
     * @param id 报警主键
     * @return 报警
     */
    @Override
    public Alarm selectAlarmById(Long id) {
        return alarmMapper.selectAlarmById(id);
    }

    /**
     * 查询报警设备列表
     *
     * @param alarm 报警
     * @return 报警集合
     */
    @Override
    public List<JSONObject> selectAlarmDeviceList(Alarm alarm) {
        // Step 1: 查询每个 alarm_point 最新的 alarm_time
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.select("alarm_point", "MAX(alarm_time) as alarm_time");
        alarmQueryWrapper.eq("alarm_status", 0);
        alarmQueryWrapper.groupBy("alarm_point");
        alarmQueryWrapper.orderByAsc("alarm_point");

        List<Alarm> selectList = alarmMapper.selectList(alarmQueryWrapper);

        // Step 2: 根据每个最新的 alarm_time 获取完整记录
        List<JSONObject> devices = selectList.stream().map(subAlarm -> {
            // 获取对应的完整记录
            QueryWrapper<Alarm> finalQuery = new QueryWrapper<>();
            finalQuery.eq("alarm_point", subAlarm.getAlarmPoint());
            finalQuery.eq("alarm_time", subAlarm.getAlarmTime());
            List<Alarm> alarmValueList = alarmMapper.selectList(finalQuery);
            Alarm alarmValue = alarmValueList.get(0);
            // 获取设备信息
            Device device = deviceService.getById(alarmValue.getAlarmPoint());

            // 将设备信息和报警信息组合成 JSON 对象
            JSONObject jsonObject = (JSONObject) JSON.toJSON(device);
            jsonObject.put("alarmType", alarmValue.getAlarmType());
            jsonObject.put("alarmTime", alarmValue.getAlarmTime());
            jsonObject.put("id", jsonObject.get("id").toString());

            return jsonObject;
        }).collect(Collectors.toList());

        return devices;
    }

    /**
     * 查询报警列表
     *
     * @param alarm 报警
     * @return 报警
     */
    @Override
    public List<Alarm> selectAlarmList(Alarm alarm) {
        return alarmMapper.selectAlarmList(alarm);
    }


    /**
     * 新增报警
     *
     * @param alarm 报警
     * @return 结果
     */
    @Override
    public int insertAlarm(Alarm alarm) {
        Date nowDate = DateUtils.getNowDate();
        alarm.setCreatedDate(nowDate);
        alarm.setModifyDate(nowDate);
        return alarmMapper.insertAlarm(alarm);
    }

    /**
     * 修改报警
     *
     * @param alarm 报警
     * @return 结果
     */
    @Override
    public int updateAlarm(Alarm alarm) {
        alarm.setModifyDate(DateUtils.getNowDate());
        return alarmMapper.updateAlarm(alarm);
    }

    /**
     * 批量删除报警
     *
     * @param ids 需要删除的报警主键
     * @return 结果
     */
    @Override
    public int deleteAlarmByIds(Long[] ids) {
        return alarmMapper.deleteAlarmByIds(ids);
    }

    /**
     * 删除报警信息
     *
     * @param id 报警主键
     * @return 结果
     */
    @Override
    public int deleteAlarmById(Long id) {
        return alarmMapper.deleteAlarmById(id);
    }
}
