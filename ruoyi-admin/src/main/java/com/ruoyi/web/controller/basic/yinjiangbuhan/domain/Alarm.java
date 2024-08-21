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
 * 报警对象 alarm
 *
 * @author liang
 * @date 2024-08-21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("alarm")
@ApiModel(value = "Alarm", description = "报警对象 alarm")
public class Alarm implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "${column.columnComment}")
    private Long id;

    /** 设备id */
    @ApiModelProperty(value = "设备id")
    private Long deviceId;

    /** 报警点位 */
    @ApiModelProperty(value = "报警点位")
    private Long alarmPoint;

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /** 设备ip */
    @ApiModelProperty(value = "设备ip")
    private String deviceIp;

    /** 设备类型枚举 */
    @ApiModelProperty(value = "设备类型枚举")
    private String deviceType;

    /** 设备sn码 */
    @ApiModelProperty(value = "设备sn码")
    private String sn;

    /** 报警类型id */
    @ApiModelProperty(value = "报警类型id")
    private Long alarmTypeId;

    /** 报警类型 */
    @ApiModelProperty(value = "报警类型")
    private String alarmType;

    /** 报警时间 */
    @ApiModelProperty(value = "报警时间")
    private String alarmTime;

    /** 报警抓拍 */
    @ApiModelProperty(value = "报警抓拍")
    private String alarmCapture;

    /** 报警内容 */
    @ApiModelProperty(value = "报警内容")
    private String alarmContent;

    /** 报警状态 0代处理 1处理中 2处理完成 */
    @ApiModelProperty(value = "报警状态 0代处理 1处理中 2处理完成")
    private Integer alarmStatus;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdDate;

    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String modifyBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;

    /** 逻辑删除标识 0删除 1正常 */
    @ApiModelProperty(value = "逻辑删除标识 0删除 1正常")
    private Long yn;

}