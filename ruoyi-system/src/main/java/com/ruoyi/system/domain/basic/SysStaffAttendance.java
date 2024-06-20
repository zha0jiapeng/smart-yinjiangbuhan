package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("sys_staff_attendance")
public class SysStaffAttendance {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 人员id
     */
    private Integer staffId;
    /**
     * 考勤日期
     */
    private String attendanceDate;
    /**
     * 上班时间
     */
    private String upperTime;
    /**
     * 下班时间
     */
    private String lowerTime;
    /**
     * 创建人id
     */
    private Integer createdById;
    /**
     * 创建人名称
     */
    private String createdByName;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人id
     */
    private Integer modifyById;
    /**
     * 更新人名称
     */
    private String modifyByName;
    /**
     * 更新时间
     */
    private Timestamp modifyDate;
    /**
     * 逻辑删除标识 1正常 0 删除
     */
    private Integer yn;
}
