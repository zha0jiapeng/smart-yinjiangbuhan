package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 施工进度计划对象 sys_construction_progress_plan
 * 
 * @author mashir0
 * @date 2024-08-22
 */
@Data
public class SysConstructionProgressPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 年月份 */
    @Excel(name = "年月份")
    private String yearMonth;

    /** 主隧洞开挖长度 */
    @Excel(name = "主隧洞开挖长度")
    private Long mainHoleDiggingLength;

    /** 主隧洞衬砌长度 */
    @Excel(name = "主隧洞衬砌长度")
    private Long mainHoleLiningLength;

    /** 支隧洞开挖长度 */
    @Excel(name = "支隧洞开挖长度")
    private Long sideHoleDiggingLength;

    /** 支隧洞衬砌长度 */
    @Excel(name = "支隧洞衬砌长度")
    private Long sideHoleLiningLength;

    /** 年投资额 */
    @Excel(name = "年投资额")
    private Long totalInvestment;

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
