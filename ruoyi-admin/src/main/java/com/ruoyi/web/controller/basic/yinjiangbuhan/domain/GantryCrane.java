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
 * 龙门吊对象 gantry_crane
 *
 * @author ruoyi
 * @date 2025-04-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("gantry_crane")
@ApiModel(value = "GantryCrane", description = "龙门吊对象 gantry_crane")
public class GantryCrane implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "${column.columnComment}")
    private Long id;

    /** 设备标识 */
    @ApiModelProperty(value = "设备标识")
    private String sn;

    /** 原始数据 */
    @ApiModelProperty(value = "原始数据")
    private String rawData;

    /** 大车行程 */
    @ApiModelProperty(value = "大车行程")
    private String distOfCrane;

    /** 小车行程 */
    @ApiModelProperty(value = "小车行程")
    private String distOfCar;

    /** 主钩吊重（t） */
    @ApiModelProperty(value = "主钩吊重（t）")
    private String loadWghtOfMain;

    /** 主钩高度(m) */
    @ApiModelProperty(value = "主钩高度(m)")
    private String loadHghtOfMain;

    /** 小钩吊重（t） */
    @ApiModelProperty(value = "小钩吊重（t）")
    private String loadWghtOfAux;

    /** 小钩高度(m) */
    @ApiModelProperty(value = "小钩高度(m)")
    private String loadHghtOfAux;

    /** 风速(m/s) */
    @ApiModelProperty(value = "风速(m/s)")
    private String windSpeed;

    /** 大车左限位报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "大车左限位报警(0 = 正常,1 = 报警)")
    private String rtLimAlmOfCrane;

    /** 大车右限位报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "大车右限位报警(0 = 正常,1 = 报警)")
    private String leLimAlmOfCrane;

    /** 小车前限位报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "小车前限位报警(0 = 正常,1 = 报警)")
    private String ftLimAlmOfCar;

    /** 小车后限位报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "小车后限位报警(0 = 正常,1 = 报警)")
    private String bkLimAlmOfCar;

    /** 超重报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "超重报警(0 = 正常,1 = 报警)")
    private String overWghtAlm;

    /** 超风速报警(0 = 正常,1 = 报警) */
    @ApiModelProperty(value = "超风速报警(0 = 正常,1 = 报警)")
    private String overWindSpdAlm;

    /** 司机身份证号码 */
    @ApiModelProperty(value = "司机身份证号码")
    private String driverIdCard;

    /** 司机姓名 */
    @ApiModelProperty(value = "司机姓名")
    private String driverName;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String installPosition;

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