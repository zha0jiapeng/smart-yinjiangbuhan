package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
            "p.name, " +
            "TIMESTAMPDIFF(HOUR, l.enter_time, NOW()) AS hours_stayed, " +
            "l.enter_time AS earliest_enter_time " +  // 添加最早进入时间列
            "FROM sys_work_people p " +
            "JOIN ( " +
            "  SELECT " +
            "    l1.sys_work_people_id, " +
            "    MIN(l1.log_time) AS enter_time " +
            "  FROM sys_work_people_inout_log l1 " +
            "  JOIN sys_device d ON l1.sn = d.sn " +
            "  WHERE " +
            "    l1.mode = 1 " +  // 只选取进入记录
            "    AND d.camera_type = 1 " +  // 只统计 camera_type = 1 的记录
            "    AND l1.sys_work_people_id IS NOT NULL " +  // 只统计 sys_work_people_id 不为空的记录
            "    AND NOT EXISTS ( " +
            "      SELECT 1 " +
            "      FROM sys_work_people_inout_log l2 " +
            "      WHERE l2.sys_work_people_id = l1.sys_work_people_id " +
            "      AND l2.mode = 0 " +  // 查找离开记录
            "      AND l2.log_time > l1.log_time " +  // 离开的时间要晚于进入的时间
            "    ) " +
            "  GROUP BY l1.sys_work_people_id, l1.name " +  // 将 l1.name 添加到 GROUP BY 中
            ") l ON p.id = l.sys_work_people_id " +
            "ORDER BY hours_stayed DESC")
    List<Map<String, Object>> getStayStatistics();
}
