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
            "AND sn like 'T99%' "+
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
}
