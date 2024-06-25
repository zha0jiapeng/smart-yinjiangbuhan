package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("lot_staff_attendance")
public class IotStaffAttendance implements Serializable {

    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    private String id;
    //  项目ID
    private String orgId;

    private String name;

    private String phone;
    // 人员id
    private String employeeId;
    // 图片
    private String image;

    private Integer wayBase;
    // 进出
    private String way;

    private String datetime;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private String workType;

    @TableField(exist = false)
    private String company;
    // 年
    private String yearCase;
    // 月
    private String monthCase;
    // 日
    private String dayCase;
    // 周
    private String weekCase;
    // 小时
    private String hourCase;

    /**
     * 人员类型
     */
    @TableField(exist = false)
    private Integer personnelConfigType;

    @TableField(exist = false)
    private String lastDate;
}
