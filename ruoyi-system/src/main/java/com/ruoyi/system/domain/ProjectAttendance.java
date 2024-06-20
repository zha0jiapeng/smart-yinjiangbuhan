package com.ruoyi.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 考勤
 */
@Data
public class ProjectAttendance implements Serializable {

    private String startDate;

    private String endDate;

    // 标题栏
    private String titleBar;
    // 单位(公司)
    private String companyName;
    // 应出勤天数集合（月）
    private List<Integer> oughtAttendanceDayList;
    // 人员出勤集合
    private List<ProjectAttendanceStaff> staffList;

    @Data
    public static class ProjectAttendanceStaff {
        // 姓名
        private String name;
        // 工种
        private String type;
        // 出勤天数(集合)
        private List<ProjectAttendancedDay> attendanceDayList;
    }

    @Data
    public static class ProjectAttendancedDay {
        private String day;
        private Boolean flag;
    }

}

