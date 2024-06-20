package com.ruoyi.system.entity;

import lombok.Data;

import java.util.List;

/**
 * 质量检查表(QualityTesting)实体类
 *
 * @author makejava
 * @since 2023-01-19 16:37:11
 */
@Data
public class QualityTestingDTO {

    private Integer pid;

    private List<QualityProblem> qualityProblemList;

}

