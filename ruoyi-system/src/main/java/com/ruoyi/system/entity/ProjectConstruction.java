package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目施工报表(ProjectConstruction)实体类
 *
 * @since 2023-06-01 14:05:22
 */
@Data
public class ProjectConstruction implements Serializable {
    private static final long serialVersionUID = -14767358197561355L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * WBS
     */
    @Excel(name = "WBS")
    private String wbs;
    /**
     * L1权重（任务1）
     */
    @Excel(name = " L1权重（任务1）")
    private String loneWeight;
    /**
     * 工程项（任务2）
     */
    @Excel(name = " 工程项（任务2）")
    private String engineeringPro;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String taskName;
    /**
     * 工程描述（任务4）
     */
    @Excel(name = " 工程描述（任务4）")
    private String engineeringDescription;
    /**
     * 权重
     */
    @Excel(name = "权重")
    private String weight;
    /**
     * 数量
     */
    @Excel(name = "数量")
    private String numbers;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;
    /**
     * 本日完成量
     */
    @Excel(name = "本日完成量")
    private String todayFinish;
    /**
     * 累计完成量
     */
    @Excel(name = "累计完成量")
    private String totalFinish;
    /**
     * 累计完成百分比
     */
    @Excel(name = "累计完成百分比")
    private String totalPercentage;
    /**
     * 累计完成情况描述（完成/进行中）
     */
    @Excel(name = "累计完成情况描述（完成/进行中）")
    private String totalDescription;
    /**
     * 计划工期/天
     */
    @Excel(name = "计划工期/天")
    private String planDay;
    /**
     * 计划开始时间
     */
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    @Excel(name = "计划开始时间")
    private String planBegin;
    /**
     * 计划完成时间
     */
    /*    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    @Excel(name = "计划完成时间")
    private String planFinish;
    /**
     * 实际开始时间
     */
    /*   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    @Excel(name = "实际开始时间")
    private String actualBegin;
    /**
     * 实际完成时间
     */
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    @Excel(name = "实际完成时间")
    private String actualFinish;
    /**
     * 实际工期/天
     */
    @Excel(name = "实际工期/天")
    private String actualDay;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 是否删除
     */
    private Integer isDel;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String startDate;

    private String endDate;

}

