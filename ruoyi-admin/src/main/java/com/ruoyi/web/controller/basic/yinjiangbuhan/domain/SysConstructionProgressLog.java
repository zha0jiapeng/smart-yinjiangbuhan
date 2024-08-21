package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 施工日志对象 sys_construction_progress_log
 *
 * @author mashir0
 * @date 2024-08-21
 */
@Data
public class SysConstructionProgressLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 隧道类型 */
    @ExcelProperty("隧道类型")
    private String holeType;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty("日期")
    private Date logDate;

    /** 钻爆掘进起始桩号 */
    @ExcelProperty(value = "钻爆掘进起始桩号")
    private String drillBlastingStart;

    /** 钻爆掘进结束桩号 */
    @ExcelProperty(value = "钻爆掘进结束桩号")
    private String drillBlastingEnd;

    /** 钻爆掘进数 */
    @Excel(name = "钻爆掘进数")
    private BigDecimal drillBlasting;

    /** 边顶拱掘起始桩号 */
    @ExcelProperty(value = "边顶拱起始桩号")
    private String sideTopArchStart;

    /** 边顶拱掘进结束桩号 */
    @ExcelProperty(value = "边顶拱结束桩号")
    private String sideTopArchEnd;

    /** 边顶拱掘进数 */
    @Excel(name = "边顶拱掘进数")
    private BigDecimal sideTopArch;

    /** 衬砌浇筑起始桩号 */
    @ExcelProperty(value = "衬砌浇筑起始桩号")
    private String liningCastingStart;

    /** 衬砌浇筑结束桩号 */
    @ExcelProperty(value = "衬砌浇筑结束桩号")
    private String liningCastingEnd;

    /** 衬砌浇筑 */
    @Excel(name = "衬砌浇筑")
    private BigDecimal liningCasting;

    /** tbm5掘进起始桩号 */
    @ExcelProperty(value = "tbm5掘进起始桩号")
    private String tmb5Start;

    /** tbm5掘进起始桩号 */
    @ExcelProperty(value = "tbm5掘进起始桩号")
    private String tmb5End;

    /** tbm5掘进数 */
    @Excel(name = "tbm5掘进数")
    private BigDecimal tmb5;

    /** tbm6掘进起始桩号 */
    @ExcelProperty(value = "tbm6掘进起始桩号")
    private String tmb6Start;

    /** tbm6掘进结束桩号 */
    @ExcelProperty(value = "tbm6掘进结束桩号")
    private String tmb6End;

    /** tbm6掘进数 */
    @Excel(name = "tbm6掘进数")
    private BigDecimal tmb6;

    /** 安装管片数 */
    @ExcelProperty(value = "安装管片数")
    private Long segments;

    /** 预制行车块 */
    @ExcelProperty(value = "预制行车块")
    private Long precastTrackBlock;

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
