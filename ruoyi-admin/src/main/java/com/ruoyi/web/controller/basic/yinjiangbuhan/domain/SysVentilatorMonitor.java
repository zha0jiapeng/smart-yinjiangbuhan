package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 通风机监测数据对象 sys_ventilator_monitor
 * 
 * @author mashir0
 * @date 2024-08-24
 */
@Data
public class SysVentilatorMonitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增 */
    private Long id;

    /** 设备id */
    @Excel(name = "设备id")
    private Long deviceId;

    /** 设备sn码 */
    @Excel(name = "设备sn码")
    private String sn;

    /** 是否开启 */
    @ExcelProperty("开启状态")
    @TableField(exist = false)
    private String isOpenStr;

    private Integer isOpen;

    /** 通风机功率 */
    @ExcelProperty("功率（%）")
    private BigDecimal power;

    /** 直径（mm） */
    @ExcelProperty("直径（mm）")
    private Long diameter;

    /** 转速（rpm） */
    @ExcelProperty("转速（rpm）")
    private BigDecimal speed;

    /** 送风量（m³） */
    @ExcelProperty("送风量（m³）")
    private BigDecimal airSupply;

    /** 风压（Pa） */
    @Excel(name = "风压（Pa）")
    private BigDecimal windPresssure;

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
