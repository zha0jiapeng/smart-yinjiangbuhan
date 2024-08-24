package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hu_p
 * @date 2024/6/23
 */
public interface SysWorkPeopleInoutLogMapper extends BaseMapper<SysWorkPeopleInoutLog> {
    @Select("SELECT COUNT(DISTINCT a.sys_work_people_id) inHoleNum " +
            "FROM sys_work_people_inout_log a " +
            "LEFT JOIN sys_work_people_inout_log b " +
            "ON a.sys_work_people_id = b.sys_work_people_id " +
            "AND b.mode = 0 " +
            "AND DATE(b.log_time) = CURDATE() " +
            "WHERE a.mode = 1 " +
            "AND a.sn like 'T99%' "+
            "AND DATE(a.log_time) = CURDATE() " +
            "AND b.sys_work_people_id IS NULL")
    int countOnlyEnteredPeopleToday();

    @Select("SELECT DATE(log_time) as date, COUNT(DISTINCT sys_work_people_id) AS attendance_count " +
            "FROM sys_work_people_inout_log " +
            "WHERE mode = 1 " +  // 只统计进入的记录
            "AND DATE_FORMAT(log_time, '%Y-%m') = #{yearMonth} " +
            "GROUP BY DATE(log_time)")
    List<Map<String, Object>> getPeopleAttendanceStatistics(String yearMonth);

    @Select("SELECT swp.personnel_config_type, " +
            "DATE_FORMAT(swil.log_time, '%Y-%m') AS month, " +
            "COUNT(DISTINCT swil.sys_work_people_id) AS attendance_count " +
            "FROM sys_work_people_inout_log swil " +
            "JOIN sys_work_people swp ON swil.sys_work_people_id = swp.id " +
            "WHERE swil.mode = 1 AND DATE_FORMAT(swil.log_time, '%Y') = #{year} " +
            "GROUP BY swp.personnel_config_type, DATE_FORMAT(swil.log_time, '%Y-%m') " +
            "ORDER BY month")
    List<Map<String, Object>> getMonthlyAttendanceCountByPersonnelConfigType(@Param("year") String year);


    @Select("SELECT sys_work_people.work_type, COUNT(DISTINCT sys_work_people_id) AS count " +
            "FROM sys_work_people_inout_log " +
            "JOIN sys_work_people ON sys_work_people.id = sys_work_people_inout_log.sys_work_people_id " +
            "WHERE DATE(log_time) BETWEEN #{today} AND DATE_ADD(#{today}, INTERVAL 1 DAY) " +
            "GROUP BY sys_work_people.work_type")
    List<Map<String, Object>> getPeopleAttendanceStatisticsByWorkType(String today);

    @Select("SELECT sys_work_people.company, COUNT(DISTINCT sys_work_people_id) AS count " +
            "FROM sys_work_people_inout_log " +
            "JOIN sys_work_people ON sys_work_people.id = sys_work_people_inout_log.sys_work_people_id " +
            "WHERE DATE(log_time) BETWEEN #{today} AND DATE_ADD(#{today}, INTERVAL 1 DAY) " +
            "GROUP BY sys_work_people.company")
    List<Map<String, Object>> getPeopleAttendanceStatisticsByCompany(String today);

    @Select("SELECT DATE(log_time) AS date, COUNT(DISTINCT sys_work_people_id) AS count " +
            "FROM sys_work_people_inout_log " +
            "WHERE DATE(log_time) BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() " +
            "GROUP BY DATE(log_time) " +
            "ORDER BY DATE(log_time)")
    List<Map<String, Object>> countDailyAttendanceForLast7Days();

    @Select("SELECT " +
            "  p.personnel_config_type, " +
            "  COUNT(DISTINCT l.sys_work_people_id) AS attended_people_count, " +
            "  COUNT(DISTINCT p.id) AS total_people_count, " +
            "  ROUND(COUNT(DISTINCT l.sys_work_people_id) / COUNT(DISTINCT p.id) * 100, 2) AS attendance_rate " +
            "FROM " +
            "  sys_work_people p " +
            "LEFT JOIN " +
            "  sys_work_people_inout_log l " +
            "ON " +
            "  p.id = l.sys_work_people_id " +
            "  AND l.mode = 1 " +  // 1表示进入
            "  AND DATE(l.log_time) = CURDATE() " +  // 只统计今天的出勤
            "WHERE " +
            "  p.key_personnel_flag = 1 " +
            "GROUP BY " +
            "  p.personnel_config_type")
    List<Map<String, Object>> getAttendanceRateByPersonnelConfigType();

    @Select("SELECT " +
            "  SUM(CASE WHEN l.enter_time IS NOT NULL AND l.exit_time IS NULL THEN 1 ELSE 0 END) AS onsite_people_count, " +
            "  SUM(CASE WHEN l.enter_time IS NOT NULL AND l.exit_time IS NULL AND TIMESTAMPDIFF(HOUR, l.enter_time, NOW()) BETWEEN 12 AND 24 THEN 1 ELSE 0 END) AS stay_12_24_hours_count, " +
            "  SUM(CASE WHEN l.enter_time IS NOT NULL AND l.exit_time IS NULL AND TIMESTAMPDIFF(HOUR, l.enter_time, NOW()) > 24 THEN 1 ELSE 0 END) AS stay_over_24_hours_count " +
            "FROM ( " +
            "  SELECT " +
            "    sys_work_people_id, " +
            "    MIN(log_time) AS enter_time, " +
            "    MAX(CASE WHEN mode = 0 THEN log_time ELSE NULL END) AS exit_time " +
            "  FROM " +
            "    sys_work_people_inout_log " +
            "  GROUP BY " +
            "    sys_work_people_id " +
            ") l;")
    Map<String, BigDecimal> getStayStatistics();
}
