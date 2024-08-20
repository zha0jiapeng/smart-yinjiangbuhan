package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;

import java.util.List;


/**
 * 设备日志Service接口
 * 
 * @author ruoyi
 * @date 2024-08-20
 */
public interface ISysDeviceLogService  extends IService<SysDeviceLog>
{
    /**
     * 查询设备日志
     * 
     * @param id 设备日志主键
     * @return 设备日志
     */
    public SysDeviceLog selectSysDeviceLogById(Long id);

    /**
     * 查询设备日志列表
     * 
     * @param sysDeviceLog 设备日志
     * @return 设备日志集合
     */
    public List<SysDeviceLog> selectSysDeviceLogList(SysDeviceLog sysDeviceLog);

    /**
     * 新增设备日志
     * 
     * @param sysDeviceLog 设备日志
     * @return 结果
     */
    public int insertSysDeviceLog(SysDeviceLog sysDeviceLog);

    /**
     * 修改设备日志
     * 
     * @param sysDeviceLog 设备日志
     * @return 结果
     */
    public int updateSysDeviceLog(SysDeviceLog sysDeviceLog);

    /**
     * 批量删除设备日志
     * 
     * @param ids 需要删除的设备日志主键集合
     * @return 结果
     */
    public int deleteSysDeviceLogByIds(Long[] ids);

    /**
     * 删除设备日志信息
     * 
     * @param id 设备日志主键
     * @return 结果
     */
    public int deleteSysDeviceLogById(Long id);
}
