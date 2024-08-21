package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;

import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Alarm;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.AlarmMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmService;
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
