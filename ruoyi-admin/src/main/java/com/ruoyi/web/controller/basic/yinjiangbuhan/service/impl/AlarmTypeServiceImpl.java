package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AlarmType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.AlarmTypeMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报警类型表Service业务层处理
 * 
 * @author liang
 * @date 2024-08-21
 */
@Service
public class AlarmTypeServiceImpl extends ServiceImpl<AlarmTypeMapper, AlarmType> implements IAlarmTypeService
{
    @Autowired(required = false)
    private AlarmTypeMapper alarmTypeMapper;

    /**
     * 查询报警类型表
     * 
     * @param id 报警类型表主键
     * @return 报警类型表
     */
    @Override
    public AlarmType selectAlarmTypeById(Long id)
    {
        return alarmTypeMapper.selectAlarmTypeById(id);
    }

    /**
     * 查询报警类型表列表
     * 
     * @param alarmType 报警类型表
     * @return 报警类型表
     */
    @Override
    public List<AlarmType> selectAlarmTypeList(AlarmType alarmType)
    {
        return alarmTypeMapper.selectAlarmTypeList(alarmType);
    }

    /**
     * 新增报警类型表
     * 
     * @param alarmType 报警类型表
     * @return 结果
     */
    @Override
    public int insertAlarmType(AlarmType alarmType)
    {
        alarmType.setCreateTime(DateUtils.getNowDate());
        return alarmTypeMapper.insertAlarmType(alarmType);
    }

    /**
     * 修改报警类型表
     * 
     * @param alarmType 报警类型表
     * @return 结果
     */
    @Override
    public int updateAlarmType(AlarmType alarmType)
    {
        alarmType.setUpdateTime(DateUtils.getNowDate());
        return alarmTypeMapper.updateAlarmType(alarmType);
    }

    /**
     * 批量删除报警类型表
     * 
     * @param ids 需要删除的报警类型表主键
     * @return 结果
     */
    @Override
    public int deleteAlarmTypeByIds(Long[] ids)
    {
        return alarmTypeMapper.deleteAlarmTypeByIds(ids);
    }

    /**
     * 删除报警类型表信息
     * 
     * @param id 报警类型表主键
     * @return 结果
     */
    @Override
    public int deleteAlarmTypeById(Long id)
    {
        return alarmTypeMapper.deleteAlarmTypeById(id);
    }
}
