package com.ruoyi.system.domain.basic;

import lombok.Data;

@Data
public class PmsExcelPlanning {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 工期
     */
    private String duration;
    /**
     * 任务开始时间
     */
    private String taskStartDate;
    /**
     * 任务结束时间
     */
    private String taskEndDate;
    /**
     * 前置任务
     */
    private String frontTask;
    /**
     * 后续任务
     */
    private String followUpTask;
    /**
     * 完成宽延时间
     */
    private String completeWideDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 资源
     */
    private String usableResources;
    /**
     * 添加新列
     */
    private String addNewColumn;
}
