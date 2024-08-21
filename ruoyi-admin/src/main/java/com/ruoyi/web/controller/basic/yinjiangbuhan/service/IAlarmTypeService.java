package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AlarmType;

import java.util.List;


/**
 * 报警类型表Service接口
 * 
 * @author liang
 * @date 2024-08-21
 */
public interface IAlarmTypeService  extends IService<AlarmType>
{
    /**
     * 查询报警类型表
     * 
     * @param id 报警类型表主键
     * @return 报警类型表
     */
    public AlarmType selectAlarmTypeById(Long id);

    /**
     * 查询报警类型表列表
     * 
     * @param alarmType 报警类型表
     * @return 报警类型表集合
     */
    public List<AlarmType> selectAlarmTypeList(AlarmType alarmType);

    /**
     * 新增报警类型表
     * 
     * @param alarmType 报警类型表
     * @return 结果
     */
    public int insertAlarmType(AlarmType alarmType);

    /**
     * 修改报警类型表
     * 
     * @param alarmType 报警类型表
     * @return 结果
     */
    public int updateAlarmType(AlarmType alarmType);

    /**
     * 批量删除报警类型表
     * 
     * @param ids 需要删除的报警类型表主键集合
     * @return 结果
     */
    public int deleteAlarmTypeByIds(Long[] ids);

    /**
     * 删除报警类型表信息
     * 
     * @param id 报警类型表主键
     * @return 结果
     */
    public int deleteAlarmTypeById(Long id);
}
