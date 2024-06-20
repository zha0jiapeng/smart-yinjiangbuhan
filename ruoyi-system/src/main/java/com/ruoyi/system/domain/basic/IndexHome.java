package com.ruoyi.system.domain.basic;

import com.ruoyi.system.entity.QualityProblem;
import lombok.Data;

import java.util.List;

@Data
public class IndexHome {
    /**
     * tab
     * 1: 整体项目进度
     * 2: 工程整体进度
     */
    private Integer tab;
    /**
     * 月份
     */
    private Integer month;
    /**
     * 完成
     */
    private Integer complete;
    /**
     * 拖延
     */
    private Integer delay;
    /**
     * 进行
     */
    private Integer conduct;
    /**
     * 未开始
     */
    private Integer noStarted;

    /**
     * 计划任务
     */
    private Integer planTask;
    /**
     * 完成任务
     */
    private Integer okTask;
    /**
     * 未完成任务
     */
    private Integer noTask;

    private List<QualityProblem> qualityProblemList;

    private List<SecurityProblem> securityProblemList;

    private List<ConstructionProblem> constructionProblemList;

}
