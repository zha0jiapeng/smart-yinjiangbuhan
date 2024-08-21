package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AlarmType;

import java.util.List;


/**
 * 报警类型表Mapper接口
 * 
 * @author liang
 * @date 2024-08-21
 */
public interface AlarmTypeMapper extends BaseMapper<AlarmType>
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
     * 删除报警类型表
     * 
     * @param id 报警类型表主键
     * @return 结果
     */
    public int deleteAlarmTypeById(Long id);

    /**
     * 批量删除报警类型表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAlarmTypeByIds(Long[] ids);
}
