package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SysDeviceLogMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysDeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 设备日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-20
 */
@Service
public class SysDeviceLogServiceImpl extends ServiceImpl<SysDeviceLogMapper, SysDeviceLog> implements ISysDeviceLogService
{
    @Autowired(required = false)
    private SysDeviceLogMapper sysDeviceLogMapper;

    /**
     * 查询设备日志
     * 
     * @param id 设备日志主键
     * @return 设备日志
     */
    @Override
    public SysDeviceLog selectSysDeviceLogById(Long id)
    {
        return sysDeviceLogMapper.selectSysDeviceLogById(id);
    }

    /**
     * 查询设备日志列表
     * 
     * @param sysDeviceLog 设备日志
     * @return 设备日志
     */
    @Override
    public List<SysDeviceLog> selectSysDeviceLogList(SysDeviceLog sysDeviceLog)
    {
        return sysDeviceLogMapper.selectSysDeviceLogList(sysDeviceLog);
    }

    /**
     * 新增设备日志
     * 
     * @param sysDeviceLog 设备日志
     * @return 结果
     */
    @Override
    public int insertSysDeviceLog(SysDeviceLog sysDeviceLog)
    {
        Date nowDate = DateUtils.getNowDate();
        sysDeviceLog.setCreatedDate(nowDate);
        sysDeviceLog.setModifyDate(nowDate);
        return sysDeviceLogMapper.insertSysDeviceLog(sysDeviceLog);
    }

    /**
     * 修改设备日志
     * 
     * @param sysDeviceLog 设备日志
     * @return 结果
     */
    @Override
    public int updateSysDeviceLog(SysDeviceLog sysDeviceLog)
    {
        return sysDeviceLogMapper.updateSysDeviceLog(sysDeviceLog);
    }

    /**
     * 批量删除设备日志
     * 
     * @param ids 需要删除的设备日志主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceLogByIds(Long[] ids)
    {
        return sysDeviceLogMapper.deleteSysDeviceLogByIds(ids);
    }

    /**
     * 删除设备日志信息
     * 
     * @param id 设备日志主键
     * @return 结果
     */
    @Override
    public int deleteSysDeviceLogById(Long id)
    {
        return sysDeviceLogMapper.deleteSysDeviceLogById(id);
    }
}
