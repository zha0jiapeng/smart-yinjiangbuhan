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
 * 准操项目对象 approved_operation_items
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("approved_operation_items")
@ApiModel(value = "ApprovedOperationItems", description = "准操项目对象 approved_operation_items")
public class ApprovedOperationItems implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /** 项目 */
    @ApiModelProperty(value = "项目")
    private String project;

    /** 项目编码 */
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

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