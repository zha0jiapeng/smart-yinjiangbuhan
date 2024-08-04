package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 隐患登记
 * </p>
 *
 * @author liushuai
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_hidden_danger")
@ApiModel(value="SysHiddenDanger对象", description="隐患登记")
public class SysHiddenDanger extends Model<SysHiddenDanger> {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "附件表ID")
    @TableField("file_id")
    private String fileId;

    @ApiModelProperty(value = "附件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "附件路径")
    @TableField("file_url")
    private String fileUrl;

    @ApiModelProperty(value = "位置id")
    @TableField("area_id")
    private Integer areaId;

    @ApiModelProperty(value = "位置名称")
    @TableField("area_name")
    private String areaName;

    @ApiModelProperty(value = "问题项id")
    @TableField("problem_id")
    private Integer problemId;

    @ApiModelProperty(value = "问题项")
    @TableField("problem")
    private String problem;

    @ApiModelProperty(value = "类型")
    @TableField("task_type")
    private Integer taskType;

    @ApiModelProperty(value = "整改时限")
    @TableField("change_date")
    private String changeDate;

    @ApiModelProperty(value = "责任单位id")
    @TableField("duty_unit_id")
    private Integer dutyUnitId;

    @ApiModelProperty(value = "责任单位")
    @TableField("duty_unit")
    private String dutyUnit;

    @ApiModelProperty(value = "责任人")
    @TableField("duty_person_id")
    private String dutyPersonId;

    @ApiModelProperty(value = "责任人")
    @TableField("duty_person")
    private String dutyPerson;

    @ApiModelProperty(value = "整改措施")
    @TableField("change_measure")
    private String changeMeasure;

    @ApiModelProperty(value = "整改描述")
    @TableField("change_describe")
    private String changeDescribe;

    @ApiModelProperty(value = "问题项名称")
    @TableField("problem_name")
    private String problemName;

    @ApiModelProperty(value = "问题等级")
    @TableField("problem_grade")
    private Integer problemGrade;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "审批意见")
    @TableField("examine_idea")
    private String examineIdea;

    @ApiModelProperty(value = "周")
    @TableField("week")
    private String week;

    @ApiModelProperty(value = "月")
    @TableField("month")
    private String month;

    @ApiModelProperty(value = "删除标识")
    @TableField("del_flag")
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date examineVerifyDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "限期整改时间")
    private Date withinDate;

    @TableField(exist = false)
    private String moon;

    @TableField(exist = false)
    private String problemBase;

    @TableField(exist = false)
    private List<SysHiddenDangerFiles> sysHiddenDangerFiles;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "开始时间")
    private String stateDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "结束时间")
    private String endDate;

}
