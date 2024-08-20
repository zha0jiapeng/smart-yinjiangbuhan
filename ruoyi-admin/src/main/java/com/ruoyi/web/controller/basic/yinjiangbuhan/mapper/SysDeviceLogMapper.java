package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;

import java.util.List;


/**
 * 设备日志Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-20
 */
public interface SysDeviceLogMapper extends BaseMapper<SysDeviceLog>
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
     * 删除设备日志
     * 
     * @param id 设备日志主键
     * @return 结果
     */
    public int deleteSysDeviceLogById(Long id);

    /**
     * 批量删除设备日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDeviceLogByIds(Long[] ids);
}
