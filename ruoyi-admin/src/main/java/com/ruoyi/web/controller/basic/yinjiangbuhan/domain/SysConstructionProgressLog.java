package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 施工日志对象 sys_construction_progress_log
 * 
 * @author mashir0
 * @date 2024-08-20
 */
public class SysConstructionProgressLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value  = "日期")
    private Date logDate;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLogDate(Date logDate) 
    {
        this.logDate = logDate;
    }

    public Date getLogDate() 
    {
        return logDate;
    }
    public void setDrillBlasting(Long drillBlasting) 
    {
        this.drillBlasting = drillBlasting;
    }

    public Long getDrillBlasting() 
    {
        return drillBlasting;
    }
    public void setSideTopArch(Long sideTopArch) 
    {
        this.sideTopArch = sideTopArch;
    }

    public Long getSideTopArch() 
    {
        return sideTopArch;
    }
    public void setTbm5(Long tbm5) 
    {
        this.tbm5 = tbm5;
    }

    public Long getTbm5() 
    {
        return tbm5;
    }
    public void setPrecastTrackBlock(Long precastTrackBlock) 
    {
        this.precastTrackBlock = precastTrackBlock;
    }

    public Long getPrecastTrackBlock() 
    {
        return precastTrackBlock;
    }
    public void setLiningCasting(Long liningCasting) 
    {
        this.liningCasting = liningCasting;
    }

    public Long getLiningCasting() 
    {
        return liningCasting;
    }
    public void setTmb6(Long tmb6) 
    {
        this.tmb6 = tmb6;
    }

    public Long getTmb6() 
    {
        return tmb6;
    }
    public void setSegments(Long segments) 
    {
        this.segments = segments;
    }

    public Long getSegments() 
    {
        return segments;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setCreatedDate(Date createdDate) 
    {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() 
    {
        return createdDate;
    }
    public void setModifyBy(String modifyBy) 
    {
        this.modifyBy = modifyBy;
    }

    public String getModifyBy() 
    {
        return modifyBy;
    }
    public void setModifyDate(Date modifyDate) 
    {
        this.modifyDate = modifyDate;
    }

    public Date getModifyDate() 
    {
        return modifyDate;
    }
    public void setYn(Long yn) 
    {
        this.yn = yn;
    }

    public Long getYn() 
    {
        return yn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("logDate", getLogDate())
            .append("drillBlasting", getDrillBlasting())
            .append("sideTopArch", getSideTopArch())
            .append("tbm5", getTbm5())
            .append("precastTrackBlock", getPrecastTrackBlock())
            .append("liningCasting", getLiningCasting())
            .append("tmb6", getTmb6())
            .append("segments", getSegments())
            .append("createdBy", getCreatedBy())
            .append("createdDate", getCreatedDate())
            .append("modifyBy", getModifyBy())
            .append("modifyDate", getModifyDate())
            .append("yn", getYn())
            .toString();
    }
}
