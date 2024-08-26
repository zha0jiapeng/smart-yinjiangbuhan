package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SysVentilatorMonitorMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysVentilatorMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 通风机监测数据Service业务层处理
 * 
 * @author mashir0
 * @date 2024-08-24
 */
@Service
public class SysVentilatorMonitorServiceImpl extends ServiceImpl<SysVentilatorMonitorMapper, SysVentilatorMonitor> implements ISysVentilatorMonitorService
{
    @Autowired
    private SysVentilatorMonitorMapper sysVentilatorMonitorMapper;

    /**
     * 查询通风机监测数据
     * 
     * @param id 通风机监测数据主键
     * @return 通风机监测数据
     */
    @Override
    public SysVentilatorMonitor selectSysVentilatorMonitorById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询通风机监测数据列表
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 通风机监测数据
     */
    @Override
    public List<SysVentilatorMonitor> selectSysVentilatorMonitorList(SysVentilatorMonitor sysVentilatorMonitor)
    {
        return list(new LambdaQueryWrapper<>(sysVentilatorMonitor));
    }

    /**
     * 新增通风机监测数据
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 结果
     */
    @Override
    public int insertSysVentilatorMonitor(SysVentilatorMonitor sysVentilatorMonitor)
    {
        return sysVentilatorMonitorMapper.insert(sysVentilatorMonitor);
    }

    /**
     * 修改通风机监测数据
     * 
     * @param sysVentilatorMonitor 通风机监测数据
     * @return 结果
     */
    @Override
    public int updateSysVentilatorMonitor(SysVentilatorMonitor sysVentilatorMonitor)
    {
        return sysVentilatorMonitorMapper.updateById(sysVentilatorMonitor);
    }

    /**
     * 批量删除通风机监测数据
     * 
     * @param ids 需要删除的通风机监测数据主键
     * @return 结果
     */
    @Override
    public int deleteSysVentilatorMonitorByIds(Long[] ids)
    {
        return sysVentilatorMonitorMapper.deleteBatchIds(new ArrayList<>(Arrays.asList(ids)));
    }

    /**
     * 删除通风机监测数据信息
     * 
     * @param id 通风机监测数据主键
     * @return 结果
     */
    @Override
    public int deleteSysVentilatorMonitorById(Long id)
    {
        return sysVentilatorMonitorMapper.deleteById(id);
    }

    @Override
    public SysVentilatorMonitor getRandomRecord() {
        return sysVentilatorMonitorMapper.getRandomRecord();
    }

    @Override
    public List<Map<String, Object>> getCurve() {
        return sysVentilatorMonitorMapper.getHourlyAverage();
    }
}
