package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysEvents;

import java.util.List;

/**
 * 事件Service接口
 * 
 * @author mashir0
 * @date 2024-07-16
 */
public interface ISysEventsService extends IService<SysEvents>
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
     * 批量删除事件
     * 
     * @param ids 需要删除的事件主键集合
     * @return 结果
     */
    public int deleteSysEventsByIds(Long[] ids);

    /**
     * 删除事件信息
     * 
     * @param id 事件主键
     * @return 结果
     */
    public int deleteSysEventsById(Long id);
}
