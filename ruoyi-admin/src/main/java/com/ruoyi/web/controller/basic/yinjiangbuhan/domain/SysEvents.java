package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 事件 sys_events
 * 
 * @author mashir0
 * @date 2024-06-23
 */
@TableName("sys_events")
@Data
public class SysEvents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 摄像头名称 */
    @Excel(name = "摄像头名称")
    private String cameraName;

    /** 摄像头编号 */
    @Excel(name = "摄像头编号")
    private String cameraNum;

    /** 摄像头IP */
    @Excel(name = "摄像头IP")
    private String cameraIp;

    /** 设备状态，1在线，0离线 */
    @Excel(name = "设备状态，1在线，0离线")
    private Long cameraState;

    /** 报警时间 */
    @Excel(name = "报警时间")
    private String monitorTime;

    /** 报警类型，抽烟、明火，未佩戴安全帽等，开发方编码定义 */
    @Excel(name = "报警类型，抽烟、明火，未佩戴安全帽等，开发方编码定义")
    private String alertType;

    /** 标段编码 */
    @Excel(name = "标段编码")
    private String bidCode;

    /** 工区编码 */
    @Excel(name = "工区编码")
    private String workAreaCode;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imageUrl;

    /** 原始数据 */
    @Excel(name = "原始数据")
    private String rawData;

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
}
