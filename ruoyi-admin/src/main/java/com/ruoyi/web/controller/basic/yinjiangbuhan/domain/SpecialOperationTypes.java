package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 特种作业类型对象 special_operation_types
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("special_operation_types")
@ApiModel(value = "SpecialOperationTypes", description = "特种作业类型对象 special_operation_types")
public class SpecialOperationTypes implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /** 作业类型 */
    @ApiModelProperty(value = "作业类型")
    private String jobType;

    /** 作业编码 */
    @ApiModelProperty(value = "作业编码")
    private String assignmentCode;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

}