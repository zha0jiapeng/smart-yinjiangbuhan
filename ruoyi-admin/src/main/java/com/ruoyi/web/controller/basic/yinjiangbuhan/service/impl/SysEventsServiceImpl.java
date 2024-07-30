package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysEvents;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SysEventsMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 事件Service业务层处理
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@Service
public class SysEventsServiceImpl extends ServiceImpl<SysEventsMapper, SysEvents> implements ISysEventsService
{
    @Autowired
    private SysEventsMapper sysEventsUserMapper;

    /**
     * 查询事件
     * 
     * @param id 事件主键
     * @return 事件
     */
    @Override
    public SysEvents selectSysEventsById(Long id)
    {
        return sysEventsUserMapper.selectSysEventsById(id);
    }

    /**
     * 查询事件列表
     * 
     * @param sysEventsUser 事件
     * @return 事件
     */
    @Override
    public List<SysEvents> selectSysEventsList(SysEvents sysEventsUser)
    {
        return sysEventsUserMapper.selectSysEventsList(sysEventsUser);
    }

    /**
     * 新增事件
     * 
     * @param sysEventsUser 事件
     * @return 结果
     */
    @Override
    public int insertSysEvents(SysEvents sysEventsUser)
    {
        sysEventsUser.setCreateTime(DateUtils.getNowDate());
        return sysEventsUserMapper.insertSysEvents(sysEventsUser);
    }

    /**
     * 修改事件
     * 
     * @param sysEventsUser 事件
     * @return 结果
     */
    @Override
    public int updateSysEvents(SysEvents sysEventsUser)
    {
        sysEventsUser.setUpdateTime(DateUtils.getNowDate());
        return sysEventsUserMapper.updateSysEvents(sysEventsUser);
    }

    /**
     * 批量删除事件
     * 
     * @param ids 需要删除的事件主键
     * @return 结果
     */
    @Override
    public int deleteSysEventsByIds(Long[] ids)
    {
        return sysEventsUserMapper.deleteSysEventsByIds(ids);
    }

    /**
     * 删除事件信息
     * 
     * @param id 事件主键
     * @return 结果
     */
    @Override
    public int deleteSysEventsById(Long id)
    {
        return sysEventsUserMapper.deleteSysEventsById(id);
    }
}
