package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 施工进度计划对象 sys_construction_progress_plan
 * 
 * @author mashir0
 * @date 2024-08-22
 */
public class SysConstructionProgressPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 年月份 */
    @Excel(name = "年月份")
    private String month;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }
    public void setMainHoleDiggingLength(Long mainHoleDiggingLength) 
    {
        this.mainHoleDiggingLength = mainHoleDiggingLength;
    }

    public Long getMainHoleDiggingLength() 
    {
        return mainHoleDiggingLength;
    }
    public void setMainHoleLiningLength(Long mainHoleLiningLength) 
    {
        this.mainHoleLiningLength = mainHoleLiningLength;
    }

    public Long getMainHoleLiningLength() 
    {
        return mainHoleLiningLength;
    }
    public void setSideHoleDiggingLength(Long sideHoleDiggingLength) 
    {
        this.sideHoleDiggingLength = sideHoleDiggingLength;
    }

    public Long getSideHoleDiggingLength() 
    {
        return sideHoleDiggingLength;
    }
    public void setSideHoleLiningLength(Long sideHoleLiningLength) 
    {
        this.sideHoleLiningLength = sideHoleLiningLength;
    }

    public Long getSideHoleLiningLength() 
    {
        return sideHoleLiningLength;
    }
    public void setTotalInvestment(Long totalInvestment) 
    {
        this.totalInvestment = totalInvestment;
    }

    public Long getTotalInvestment() 
    {
        return totalInvestment;
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
            .append("month", getMonth())
            .append("mainHoleDiggingLength", getMainHoleDiggingLength())
            .append("mainHoleLiningLength", getMainHoleLiningLength())
            .append("sideHoleDiggingLength", getSideHoleDiggingLength())
            .append("sideHoleLiningLength", getSideHoleLiningLength())
            .append("totalInvestment", getTotalInvestment())
            .append("createdBy", getCreatedBy())
            .append("createdDate", getCreatedDate())
            .append("modifyBy", getModifyBy())
            .append("modifyDate", getModifyDate())
            .append("yn", getYn())
            .toString();
    }
}
