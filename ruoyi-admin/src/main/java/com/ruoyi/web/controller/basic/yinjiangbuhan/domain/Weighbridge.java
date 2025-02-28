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
 * 地磅对象 weighbridge
 *
 * @author ruoyi
 * @date 2025-01-23
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("weighbridge")
@ApiModel(value = "Weighbridge", description = "地磅对象 weighbridge")
public class Weighbridge implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "${column.columnComment}")
    private Long id;

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

    /** 毛重 */
    @ApiModelProperty(value = "毛重")
    private String gross;

    /** 皮重 */
    @ApiModelProperty(value = "皮重")
    private String tare;

    /** 驶出时间 */
    @ApiModelProperty(value = "驶出时间")
    private String tareWeightingTime;

    /** 车牌号 */
    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    /** 净重 */
    @ApiModelProperty(value = "净重")
    private String netWeight;

    /** 原始数据 */
    @ApiModelProperty(value = "原始数据")
    private String rawData;

    /** 物料名称 */
    @ApiModelProperty(value = "物料名称")
    private String materialName;

    /** 进入时间 */
    @ApiModelProperty(value = "进入时间")
    private String grossWeightingTime;

    /** 原始数据id */
    @ApiModelProperty(value = "原始数据id")
    private Long rawDataId;

}