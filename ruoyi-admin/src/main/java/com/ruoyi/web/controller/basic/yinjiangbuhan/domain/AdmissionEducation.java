package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 入场三级教育对象 sys_admission_education
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@Data
@TableName("sys_admission_education")
public class AdmissionEducation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 培训部门 */
    @Excel(name = "培训部门")
    private Long deptId;

    /** 培训名称 */
    @Excel(name = "培训名称")
    private String trainName;

    /** 培训开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "培训开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date trainStartTime;

    /** 培训结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "培训结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date trainEndTime;

    /** 培训时长 */
    @Excel(name = "培训时长")
    private Long trainDuration;

    /** 培训讲师 */
    @Excel(name = "培训讲师")
    private String trainTeacherName;

    /** 培训内容 */
    @Excel(name = "培训内容")
    private String trainContent;

    /** 培训材料 */
    @Excel(name = "培训材料")
    private String trainAttachment;

    @TableLogic
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    @TableField(exist = false)
    private List<AdmissionEducationUser> admissionEducationUsers;

    @TableField(exist = false)
    private String userNames;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String queryDate;
}
