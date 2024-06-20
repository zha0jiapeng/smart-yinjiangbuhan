package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppAround {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 关联关系
     */
    private String relation;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 间隔时间（天）
     */
    private Integer interval;
    /**
     * 关联日期
     */
    private Date relationDate;
}
