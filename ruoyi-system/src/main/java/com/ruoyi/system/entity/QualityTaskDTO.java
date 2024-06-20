package com.ruoyi.system.entity;

import lombok.Data;

import java.util.List;

/**
 * 质量任务(QualityTask)实体类
 *
 * @author makejava
 * @since 2023-01-19 15:27:19
 */
@Data
public class QualityTaskDTO{

    private Integer Pid;

    private List<QualityProblem> qualityProblemList;

}

