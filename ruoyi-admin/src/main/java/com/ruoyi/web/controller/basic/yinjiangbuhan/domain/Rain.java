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
 * 雨量计对象 rain
 *
 * @author ruoyi
 * @date 2024-08-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rain")
@ApiModel(value = "Rain", description = "雨量计对象 rain")
public class Rain implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 设备状态（normal:正常；alarming:报警；preAlarming:预警；offline:离线）
     */
    @ApiModelProperty(value = "设备状态（normal:正常；alarming:报警；preAlarming:预警；offline:离线）")
    private String deviceStatus;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /**
     * 设备地址码
     */
    @ApiModelProperty(value = "设备地址码")
    private String deviceAddr;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lat;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lng;

    /**
     * 继电器状态 Json 字符串，
     * 内含两个属性：
     * relayNo 继电器编号
     * relayStatus 0:断开 1:闭合
     */
    @ApiModelProperty(value = "继电器状态 Json 字符串， 内含两个属性： relayNo 继电器编号 relayStatus 0:断开 1:闭合")
    private String relayStatus;

    /**
     * 系统编码
     */
    @ApiModelProperty(value = "系统编码")
    private String systemCode;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private String timeStamp;

    /**
     * 节点内容
     */
    @ApiModelProperty(value = "节点内容")
    private String dataItem;

    /**
     * 当天降雨量
     */
    @ApiModelProperty(value = "当天降雨量")
    private String curRain;

    /**
     * 累计降雨量
     */
    @ApiModelProperty(value = "累计降雨量")
    private String totalRain;

    /**
     * 1分钟时段降雨量
     */
    @ApiModelProperty(value = "1分钟时段降雨量")
    private String rain1;

    /**
     * 5分钟时段降雨量
     */
    @ApiModelProperty(value = "5分钟时段降雨量")
    private String rain5;

    /**
     * 10分钟时段降雨量
     */
    @ApiModelProperty(value = "10分钟时段降雨量")
    private String rain10;

    /**
     * 60分钟时段降雨量
     */
    @ApiModelProperty(value = "60分钟时段降雨量")
    private String rain60;

    /**
     * 监测时间点得水位
     */
    @ApiModelProperty(value = "监测时间点得水位")
    private String waterLevel;

    /**
     * 原始数据
     */
    @ApiModelProperty(value = "原始数据")
    private String rawData;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 1分钟前降雨量
     */
    @ApiModelProperty(value = "1分钟前降雨量")
    private String oldRain1;

}