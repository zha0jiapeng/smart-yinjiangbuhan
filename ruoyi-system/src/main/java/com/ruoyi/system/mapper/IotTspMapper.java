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
            "ROUND(AVG(temperature), 2) AS avg_temperature, " +
            "ROUND(AVG(pm_two_five), 2) AS avg_pm_two_five, " +
            "ROUND(AVG(pm_ten), 2) AS avg_pm_ten, " +
            "ROUND(AVG(tsp), 2) AS avg_tsp, " +
            "ROUND(AVG(humidity), 2) AS avg_humidity, " +
            "ROUND(AVG(wind_speed), 2) AS avg_wind_speed, " +
            "ROUND(AVG(noise), 2) AS avg_noise, " +
            "ROUND(AVG(pressure), 2) AS avg_pressure, " +
            "ROUND(AVG(no_two), 2) AS avg_no_two, " +
            "ROUND(AVG(so_two), 2) AS avg_so_two, " +
            "ROUND(AVG(co), 2) AS avg_co, " +
            "ROUND(AVG(three), 2) AS avg_three, " +
            "ROUND(AVG(voc), 2) AS avg_voc " +
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
            "<if test='type == 2'>" +
            "   GROUP BY DATE_FORMAT(created_date, '%Y-%m') " +
            "   ORDER BY DATE_FORMAT(created_date, '%Y-%m') ASC " +
            "</if> " +
            "<if test='type == 3'>" +
            "   GROUP BY DATE_FORMAT(created_date, '%Y-%m-%d') " +
            "   ORDER BY DATE_FORMAT(created_date, '%Y-%m-%d') ASC " +
            "</if> " +
            "<if test='type == 4'>" +
            "   GROUP BY DATE_FORMAT(created_date, '%Y-%m-%d %H') " +
            "   ORDER BY DATE_FORMAT(created_date, '%Y-%m-%d %H') ASC " +
            "</if> " +
            "</script>")
    List<Map<String, Object>> getCurve(@Param("type") Integer type,@Param("text") String text);
}
