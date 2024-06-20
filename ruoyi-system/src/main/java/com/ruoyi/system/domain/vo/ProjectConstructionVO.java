package com.ruoyi.system.domain.vo;
import lombok.Data;

/**
 * 项目施工报表(ProjectConstruction)实体类
 * @since 2023-06-01 14:05:22
 */
@Data
public class ProjectConstructionVO {

    /**
     * 权重
     */
    private String weight;
    /**
     * 累计完成百分比
     */
    private String totalPercentage;
    /**
     * 累计完成情况描述（完成/进行中）
     */
    private String totalDescription;
    /**
     * 计划开始时间
     */
    private String planBegin;
    /**
     * 计划完成时间
     */
    private String planFinish;

    /**
     * 实际开始时间
     */
    private String actualBegin;
    /**
     * 实际完成时间
     */
    private String actualFinish;
    /**
     * 实际工期/天
     */
    private String actualDay;
    /**
     * 计划工期/天
     */
    private String planDay;









//    /**
//     * L1权重（任务1）
//     */
//    private String loneWeight;
//    /**
//     * 本日完成量
//     */
//    private String todayFinish;
//    /**
//     * 累计完成量
//     */
//    private String totalFinish;
//    /**
//     * 累计完成情况描述（完成/进行中）
//     */
//    private String totalDescription;
//    /**
//     * 计划工期/天
//     */
//    private String planDay;
//    /**
//     * 实际开始时间
//     */
//    private String actualBegin;
//    /**
//     * 实际完成时间
//     */
//    private String actualFinish;

}

