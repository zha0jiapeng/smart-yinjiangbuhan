package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 施工日志Mapper接口
 * 
 * @author mashir0
 * @date 2024-08-20
 */
public interface SysConstructionProgressLogMapper extends BaseMapper<SysConstructionProgressLog>
{
    @Select("SELECT " +
            "hole_type, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 " +
            "GROUP BY hole_type")
    List<Map<String, Object>> getSum();

    @Select("SELECT " +
            "hole_type, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 AND YEAR(log_date) = #{year} " +
            "GROUP BY hole_type")
    List<Map<String, Object>> getYearSum(@Param("year")String text);

    @Select("SELECT " +
            "hole_type, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 AND DATE_FORMAT(log_date, '%Y-%m') = #{month}  " +
            "GROUP BY hole_type")
    List<Map<String, Object>> getMonthSum(@Param("month")String text);

    @Select("SELECT " +
            "hole_type, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 AND log_date = #{date} " +
            "GROUP BY hole_type " +
            "ORDER BY hole_type")
    List<Map<String, Object>> getDaySum(String text);

    @Select("SELECT " +
            "hole_type, " +
            "YEAR(log_date) AS item, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 " +
            "GROUP BY hole_type, YEAR(log_date) " +
            "ORDER BY hole_type, year")
    List<Map<String, Object>> getTotalCurve();

    @Select("SELECT " +
            "hole_type, " +
            "MONTH(log_date) AS item, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 AND YEAR(log_date) = #{year} " +
            "GROUP BY hole_type, MONTH(log_date) " +
            "ORDER BY hole_type, month")
    List<Map<String, Object>> getYearCurve(String year);
    @Select("SELECT " +
            "hole_type, " +
            "log_date AS item, " +
            "SUM(IFNULL(drill_blasting, 0) + IFNULL(tmb5, 0) + IFNULL(tmb6, 0)) AS total_excavation, " +
            "SUM(IFNULL(lining_casting, 0) + IFNULL(side_top_arch, 0) + IFNULL(segments, 0) * 1.8) AS total_lining " +
            "FROM sys_construction_progress_log " +
            "WHERE yn = 1 AND DATE_FORMAT(log_date, '%Y-%m') = #{month} " +
            "GROUP BY hole_type, log_date " +
            "ORDER BY hole_type, day")
    List<Map<String, Object>> getMonthCurve(String month);
}
