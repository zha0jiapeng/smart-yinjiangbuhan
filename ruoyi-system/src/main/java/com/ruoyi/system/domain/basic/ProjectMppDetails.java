package com.ruoyi.system.domain.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppDetails {
    /**
     * 任务编码
     */
    private String taskCode;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planSateDate;
    /**
     * 实际开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualStartDate;
    /**
     * 计划产值
     */
    private String planOutputValue;

    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date planEndDate;
    /**
     * 实际结束时间
     *
     */
    private Date actualEndDate;
    /**
     * 实际产值
     */
    private String actualOutputValue;

    /**
     * 所属群组
     */
    private String affiliationGroup;
    /**
     * 工期
     */
    private String duration;

    /**
     * 任务状态
     */
    private String taskStatus;
    /**
     * 模型
     */
    private Integer model;
}
