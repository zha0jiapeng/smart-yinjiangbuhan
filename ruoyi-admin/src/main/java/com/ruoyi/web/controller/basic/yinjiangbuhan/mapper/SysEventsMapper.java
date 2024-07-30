package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysEvents;

import java.util.List;

/**
 * 事件Mapper接口
 * 
 * @author mashir0
 * @date 2024-06-23
 */
public interface SysEventsMapper extends BaseMapper<SysEvents>
{
    /**
     * 查询事件
     * 
     * @param id 事件主键
     * @return 事件
     */
    public SysEvents selectSysEventsById(Long id);

    /**
     * 查询事件列表
     * 
     * @param sysEvents 事件
     * @return 事件集合
     */
    public List<SysEvents> selectSysEventsList(SysEvents sysEvents);

    /**
     * 新增事件
     * 
     * @param sysEvents 事件
     * @return 结果
     */
    public int insertSysEvents(SysEvents sysEvents);

    /**
     * 修改事件
     * 
     * @param sysEvents 事件
     * @return 结果
     */
    public int updateSysEvents(SysEvents sysEvents);

    /**
     * 删除事件
     * 
     * @param id 事件主键
     * @return 结果
     */
    public int deleteSysEventsById(Long id);

    /**
     * 批量删除事件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEventsByIds(Long[] ids);
}
