package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectMppIcon {
    /**
     * 时间图表
     */
    private List<String> dateChart;
    /**
     * 计划进度开始时间
     */
    private String planProgressStartDate;
    /**
     * 计划进度结束时间
     */
    private String planProgressEndDate;
    /**
     * 实际进度开始时间
     */
    private Date actualProgressStartDate;
    /**
     * 实际进度结束时间
     */
    private Date actualProgressEndDate;
}
