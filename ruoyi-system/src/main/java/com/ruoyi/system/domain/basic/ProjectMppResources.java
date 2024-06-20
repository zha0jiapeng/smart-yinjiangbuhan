package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppResources {
    /**
     * 日期
     */
    private Date date;
    /**
     * 材料名称
     */
    private String scienceName;
    /**
     * 材料型号
     */
    private String scienceModel;
    /**
     * 计划用量
     */
    private String planConsumption;
    /**
     * 单位
     */
    private String unit;
}
