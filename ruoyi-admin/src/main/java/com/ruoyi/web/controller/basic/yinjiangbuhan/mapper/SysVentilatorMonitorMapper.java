package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import org.apache.ibatis.annotations.Select;

/**
 * 通风机监测数据Mapper接口
 * 
 * @author mashir0
 * @date 2024-08-24
 */
public interface SysVentilatorMonitorMapper extends BaseMapper<SysVentilatorMonitor>
{

    @Select("SELECT * FROM sys_ventilator_monitor ORDER BY RAND() LIMIT 1")
    SysVentilatorMonitor getRandomRecord();
}
