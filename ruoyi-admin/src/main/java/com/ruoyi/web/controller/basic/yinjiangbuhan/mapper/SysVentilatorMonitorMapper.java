package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT " +
            "DATE_FORMAT(created_date, '%Y-%m-%d %H:00:00') AS hour, " +
            "AVG(power) AS avg_power, " +
            "AVG(speed) AS avg_speed, " +
            "AVG(air_supply) AS avg_air_supply, " +
            "AVG(wind_presssure) AS avg_wind_pressure " +
            "FROM sys_ventilator_monitor " +
            "WHERE DATE(created_date) = CURDATE() " +
            "GROUP BY hour " +
            "ORDER BY hour ASC")
    List<Map<String, Object>> getHourlyAverage();
}
