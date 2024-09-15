package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备日志对象 sys_device_log
 *
 * @author ruoyi
 * @date 2024-08-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_device_log")
@ApiModel(value = "SysDeviceLog", description = "设备日志对象 sys_device_log")
public class SysDeviceLog implements Serializable {

    private static final long serialVersionUID=1L;


    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /** sys_device表中id */
    @ApiModelProperty(value = "sys_device表中id")
    private Long sysDeviceId;

    /** 设备名称 */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /** 设备ip */
    @ApiModelProperty(value = "设备ip")
    private String deviceIp;

    /** 设备类型枚举 */
    @ApiModelProperty(value = "设备类型枚举")
    private String deviceType;

    /** 设备端口 */
    @ApiModelProperty(value = "设备端口")
    private Integer devicePort;

    /** 设备区域 */
    @ApiModelProperty(value = "设备区域")
    private String deviceArea;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /** 设备配置 */
    @ApiModelProperty(value = "设备配置")
    private String configJson;

    /** 是否在线 */
    @ApiModelProperty(value = "是否在线")
    private Long isOnline;

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private Integer cameraType;

    /** 设备sn码 */
    @ApiModelProperty(value = "设备sn码")
    private String sn;

    /** sys_device表中创建人 */
    @ApiModelProperty(value = "sys_device表中创建人")
    private String sysDeviceCreatedBy;

    /** sys_device表中创建时间 */
    @ApiModelProperty(value = "sys_device表中创建时间")
    private Date sysDeviceCreatedData;

    /** sys_device表中更新人 */
    @ApiModelProperty(value = "sys_device表中更新人")
    private String sysDeviceModifyBy;

    /** sys_device表中更新时间 */
    @ApiModelProperty(value = "sys_device表中更新时间")
    private Date sysDeviceModifyDate;

    /** sys_device表中逻辑删除标识 0删除 1正常 */
    @ApiModelProperty(value = "sys_device表中逻辑删除标识 0删除 1正常")
    private Long sysDeviceYn;

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