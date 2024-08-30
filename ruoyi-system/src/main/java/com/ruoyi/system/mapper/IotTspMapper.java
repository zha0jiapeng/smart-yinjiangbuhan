package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.basic.IotTspCopy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IotTspMapper extends BaseMapper<IotTsp> {

    List<IotTsp> queryAllByLimit(IotTsp iotTsp);

    List<IotTspCopy> queryAllList(@Param("starTime") String starTime, @Param("endTime")  String endTime);

    @Select("<script>" +
            "SELECT " +
            "<if test='type == 1'> " +
            "   'Total' AS period, " +
            "</if> " +
            "<if test='type == 2'> " +
            "   DATE_FORMAT(created_date, '%Y-%m') AS period, " +
            "</if> " +
            "<if test='type == 3'> " +
            "   DATE_FORMAT(created_date, '%Y-%m-%d') AS period, " +
            "</if> " +
            "<if test='type == 4'> " +
            "   DATE_FORMAT(created_date, '%Y-%m-%d %H') AS period, " +
            "</if> " +
            "AVG(temperature) AS avg_temperature, " +
            "AVG(pm_two_five) AS avg_pm_two_five, " +
            "AVG(pm_ten) AS avg_pm_ten, " +
            "AVG(tsp) AS avg_tsp, " +
            "AVG(humidity) AS avg_humidity, " +
            "AVG(wind_speed) AS avg_wind_speed, " +
            "AVG(noise) AS avg_noise, " +
            "AVG(pressure) AS avg_pressure " +
            "FROM lot_tsp " +
            "WHERE yn = 1 " +
            "<if test='type == 1'> " +
            "   AND created_date IS NOT NULL " +
            "</if> " +
            "<if test='type == 2'> " +
            "   AND DATE_FORMAT(created_date, '%Y') = #{text} " +
            "</if> " +
            "<if test='type == 3'> " +
            "   AND DATE_FORMAT(created_date, '%Y-%m') = #{text} " +
            "</if> " +
            "<if test='type == 4'> " +
            "   AND DATE_FORMAT(created_date, '%Y-%m-%d') = #{text} " +
            "</if> " +
            "GROUP BY period " +
            "ORDER BY period ASC" +
            "</script>")
    List<Map<String, Object>> getCurve(@Param("type") Integer type,@Param("text") Integer text);
}
