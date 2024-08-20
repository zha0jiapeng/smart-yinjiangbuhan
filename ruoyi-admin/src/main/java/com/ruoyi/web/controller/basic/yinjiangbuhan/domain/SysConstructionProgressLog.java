package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 施工日志对象 sys_construction_progress_log
 * 
 * @author mashir0
 * @date 2024-08-20
 */
@Data
public class SysConstructionProgressLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value  = "日期")
    private Date logDate;

    @ExcelProperty(value = "隧洞类型")
    private String holeType;

    /** 钻爆掘进数 */
    @ExcelProperty(value = "钻爆掘进")
    private Long drillBlasting;

    /** 边顶拱掘进数 */
    @ExcelProperty(value = "边顶拱")
    private Long sideTopArch;

    /** tbm5掘进数 */
    @ExcelProperty(value = "tbm5掘进")
    private Long tbm5;

    /** 预制行车块 */
    @ExcelProperty(value = "预制行车块")
    private Long precastTrackBlock;

    /** 衬砌浇筑 */
    @ExcelProperty(value ="衬砌浇筑")
    private Long liningCasting;

    /** tbm6掘进数 */
    @ExcelProperty(value = "tbm6掘进")
    private Long tmb6;

    /** 安装管片数 */
    @ExcelProperty(value = "安装管片")
    private Long segments;

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
