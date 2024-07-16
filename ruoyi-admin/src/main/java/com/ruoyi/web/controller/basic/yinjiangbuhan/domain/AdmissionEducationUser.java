package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 入场三级教育用户对象 sys_admission_education_user
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@Data
@TableName("sys_admission_education_user")
public class AdmissionEducationUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 教育id */
    @Excel(name = "教育id")
    private Long admissionEducationId;

    /** 培训对象 */
    @Excel(name = "培训对象")
    private Long userId;

    @Excel(name = "培训对象")
    @TableField(exist = false)
    private String userName;

    /** 成绩 */
    @Excel(name = "成绩")
    private Long userScore;

    @TableLogic
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

}
