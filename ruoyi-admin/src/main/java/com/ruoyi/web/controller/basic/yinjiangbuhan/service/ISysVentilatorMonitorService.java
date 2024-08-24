package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;

import java.util.List;

/**
 * 通风机监测数据Service接口
 * 
 * @author mashir0
 * @date 2024-08-24
 */
public interface ISysVentilatorMonitorService extends IService<SysVentilatorMonitor>
{
    /**
     * 查询通风机监测数据
     * 
     * @param id 通风机监测数据主键
     * @return 通风机监测数据
     */
    public SysVentilatorMonitor selectSysVentilatorMonitorById(Long id);

    /**
     * 查询通风机监测数据列表
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 通风机监测数据集合
     */
    public List<SysVentilatorMonitor> selectSysVentilatorMonitorList(SysVentilatorMonitor sysVentilatorMonitor);

    /**
     * 新增通风机监测数据
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 结果
     */
    public int insertSysVentilatorMonitor(SysVentilatorMonitor sysVentilatorMonitor);

    /**
     * 修改通风机监测数据
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 结果
     */
    public int updateSysVentilatorMonitor(SysVentilatorMonitor sysVentilatorMonitor);

    /**
     * 批量删除通风机监测数据
     * 
     * @param ids 需要删除的通风机监测数据主键集合
     * @return 结果
     */
    public int deleteSysVentilatorMonitorByIds(Long[] ids);

    /**
     * 删除通风机监测数据信息
     * 
     * @param id 通风机监测数据主键
     * @return 结果
     */
    public int deleteSysVentilatorMonitorById(Long id);

    SysVentilatorMonitor getRandomRecord();
}
