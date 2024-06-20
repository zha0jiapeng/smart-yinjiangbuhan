package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("current_construction_new")
@SuppressWarnings("all")
public class CurrentConstructionNew extends BaseDomain {
    /**
     * 关联id
     */
    private String ids;
    /**
     * 桩号
     */
    private String pileNumber;
    /**
     * 结束桩号
     */
    private String pileNumberEnd;
    /**
     * 方向
     */
    private String direction;
    /**
     * 施工日期
     */
    private String constructionDate;
    /**
     * TBM实际值
     */
    private BigDecimal tbmRealityPrice;
    /**
     * schedule实际值
     */
    private BigDecimal scheduleRealityPrice;
    /**
     * 施工单位id
     */
    private Integer constructionCompanyId;
    /**
     * 施工单位
     */
    private String constructionCompany;
    /**
     * 监理单位id
     */
    private Integer supervisionCompanyId;
    /**
     * 监理单位
     */
    private String supervisionCompany;
    /**
     * TBM主司机id
     */
    private Integer tbmDriverId;
    /**
     * TBM主司机
     */
    private String tbmDriver;
    /**
     * 管片拼装手id
     */
    private Integer segmentAssemblyId;
    /**
     * 管片拼装手
     */
    private String segmentAssembly;
    /**
     * 班组组长id
     */
    private Integer teamLeaderId;
    /**
     * 班组组长
     */
    private String teamLeader;
    /**
     * 质量主管id
     */
    private Integer qualityLeaderId;
    /**
     * 质量主管
     */
    private String qualityLeader;
    /**
     * 监理验收id
     */
    private Integer supervisionAcceptId;
    /**
     * 监理验收
     */
    private String supervisionAccept;
    /**
     * 管片厂家id
     */
    private Integer segmentManufacturerId;
    /**
     * 管片厂家
     */
    private String segmentManufacturer;
    /**
     * 施工开始日期
     */
    private String constructionStartDate;
    /**
     * 施工结束日期
     */
    private String constructionEndDate;
    /**
     * 管片类型
     */
    private Integer segmentType;
    /**
     * 详情
     */
    private String basicValueInfoTop;
    /**
     * 详情
     */
    private String basicValueInfoBottom;
    /**
     * 详情
     */
    private String basicValueInfoLeft;
    /**
     * 详情
     */
    private String basicValueInfoRight;
    /**
     * 是不是列表
     */
    private Integer dataFlag;
    /**
     * 头状态
     */
    private Integer headFlag;
    /**
     * 体状态
     */
    private Integer bodyFlag;

    private Integer pid;

    @TableField(exist = false)
    private List<Map> info;

}
