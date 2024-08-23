package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements IAlarmService
{
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
    public Alarm selectAlarmById(Long id)
    {
        return alarmMapper.selectAlarmById(id);
    }

    /**
     * 查询报警设备列表
     * 
     * @param alarm 报警
     * @return 报警
     */
    @Override
    public List<JSONObject> selectAlarmDeviceList(Alarm alarm)
    {
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.select("alarm_point");
        alarmQueryWrapper.eq("alarm_status", 0);
        alarmQueryWrapper.orderByAsc("alarm_point");
        alarmQueryWrapper.groupBy("alarm_point");
        List<Alarm> selectList = alarmMapper.selectList(alarmQueryWrapper);
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> devices = new ArrayList<>();
        for (Alarm alarmValue : selectList) {
            Device device = deviceService.getById(alarmValue.getAlarmPoint());
            jsonObject = (JSONObject) JSON.toJSON(device);
            jsonObject.putOpt("alarmType",alarmValue.getAlarmType());
            jsonObject.putOpt("alarmTime",alarmValue.getAlarmTime());
            jsonObject.putOpt("id",String.valueOf(alarmValue.getId()));
            devices.add(jsonObject);
        }
        return jsonObject;
    }

    /**
     * 查询报警列表
     *
     * @param alarm 报警
     * @return 报警
     */
    @Override
    public List<Alarm> selectAlarmList(Alarm alarm)
    {
        return alarmMapper.selectAlarmList(alarm);
    }



    /**
     * 新增报警
     * 
     * @param alarm 报警
     * @return 结果
     */
    @Override
    public int insertAlarm(Alarm alarm)
    {
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
    public int updateAlarm(Alarm alarm)
    {
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
    public int deleteAlarmByIds(Long[] ids)
    {
        return alarmMapper.deleteAlarmByIds(ids);
    }

    /**
     * 删除报警信息
     * 
     * @param id 报警主键
     * @return 结果
     */
    @Override
    public int deleteAlarmById(Long id)
    {
        return alarmMapper.deleteAlarmById(id);
    }
}
