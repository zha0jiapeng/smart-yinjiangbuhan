package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 设备对象 sys_device
 * 
 * @author mashir0
 * @date 2024-06-23
 */
@TableName("sys_device")
@Data
public class Device extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备ip */
    @Excel(name = "设备ip")
    private String deviceIp;

    /** 设备类型枚举 */
    @Excel(name = "设备类型枚举")
    private String deviceType;

    /** 设备端口 */
    @Excel(name = "设备端口")
    private String devicePort;

    /** 设备区域 */
    @Excel(name = "设备区域")
    private String deviceArea;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 设备配置 */
    @Excel(name = "设备配置")
    private String configJson;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdDate;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifyBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyDate;

    /** 逻辑删除标识 0删除 1正常 */
    @Excel(name = "逻辑删除标识 0删除 1正常")
    private Long yn;

    private Integer isOnline;

    @Excel(name = "设备sn")
    private String sn;

    @Excel(name = "摄像头类型")
    private Integer cameraType;

    private Integer distance;
}
