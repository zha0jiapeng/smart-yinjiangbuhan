package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 质检问题表(QualityProblem)实体类
 *
 * @author makejava
 * @since 2023-01-19 16:37:11
 */
@Data
public class QualityProblem implements Serializable {
    private static final long serialVersionUID = -47506713357847369L;
    
    private Integer id;

    /**
     * 质检任务ID
     */
    private Integer qualityTaskId;

    /**
     * 问题描述
     */
    private String problemInfo;

    /**
     * 问题附件文件名称
     */
    private String problemFileName;

    /**
     * 问题附件文件路径
     */
    private String problemFileUrl;

    /**
     * 问题级别
     */
    private String problemLevel;

    /**
     * 问题图片
     */
    private String problemImageUrl;

    /**
     * 处理意见
     */
    private String handleOpinion;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 处理图片
     */
    private String handleImage;

    /**
     * 处理附件文件名称
     */
    private String handleFileName;

    /**
     * 处理附件文件路径
     */
    private String handleFileUrl;

    /**
     * 问题状态
     */
    private String problemProgress;

    /**
     * 问题类型
     */
    private Integer problemType;

    private String problemTypeBase;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 问题种类
     */
    private String problemSpecies;

    /**
     * 问题名称
     */
    private String problemName;

    /**
     * 问题审核人名称
     */
    private String problemAuditUserName;

    /**
     * 问题审核人ID
     */
    private Integer problemAuditUserId;

    /**
     * 问题执行人名称
     */
    private String problemExecuteUserName;

    /**
     * 问题执行人ID
     */
    private Integer problemExecuteUserId;

    /**
     * 问题验收人名称
     */
    private String problemReviewUserName;

    /**
     * 问题验收人ID
     */
    private Integer problemReviewUserId;

    /**
     * 模型ID
     */
    private Integer modelId;

    /**
     * 点位下标
     */
    private String modelIndex;

    /**
     * 创建人
     */
    private String createUserName;
    
    private Integer createUserId;

    /**
     * 质检任务名称
     */
    private String qualityTaskName;

    /**
     * 问题审核时间
     */
    private String problemAuditTime;

    /**
     * 问题执行时间
     */
    private String problemExecuteTime;

    /**
     * 问题验收时间
     */
    private String problemReviewTime;

    /**
     * 进度任务ID
     */
    private Integer projId;

    /**
     * 进度任务名称
     */
    private String projName;

    /**
     * 问题抄送人
     */
    private String problemCopyUserName;

    /**
     * 问题抄送人ID
     */
    private Integer problemCopyUserId;

    /**
     *  是否通过 0-通过 1-未通过
     */
    private Integer pass;

    private QualityTask qualityTask;

    private List<String> ids;

    private List<QualityProblemHandle> handles;

    /**
     * 审核状态(0-未处理 1-审核中 2已通过)
     */
    @TableField("check_status")
    private Integer checkStatus;

    /**
     * 附件URL
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 附件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 整改内容
     */
    @TableField("check_content")
    private String checkContent;

    /**
     * 审批意见
     */
    @TableField("examine_opinion")
    private String examineOpinion;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    /**
     * 限期整改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "限期整改时间")
    private Date abarbeitungTime;

    private String region;

    private String moonBase;

    private String patternBase;

    private String rectificationMeasure;

    /**
     * 桩号
     */
    private String pileNumber;

}

