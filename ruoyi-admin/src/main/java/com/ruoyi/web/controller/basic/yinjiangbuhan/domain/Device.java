package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备对象 sys_device
 * 
 * @author mashir0
 * @date 2024-06-23
 */
@TableName("sys_device")
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setDeviceIp(String deviceIp) 
    {
        this.deviceIp = deviceIp;
    }

    public String getDeviceIp() 
    {
        return deviceIp;
    }
    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }
    public void setDevicePort(String devicePort) 
    {
        this.devicePort = devicePort;
    }

    public String getDevicePort() 
    {
        return devicePort;
    }
    public void setDeviceArea(String deviceArea) 
    {
        this.deviceArea = deviceArea;
    }

    public String getDeviceArea() 
    {
        return deviceArea;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setConfigJson(String configJson) 
    {
        this.configJson = configJson;
    }

    public String getConfigJson() 
    {
        return configJson;
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
            .append("deviceName", getDeviceName())
            .append("deviceIp", getDeviceIp())
            .append("deviceType", getDeviceType())
            .append("devicePort", getDevicePort())
            .append("deviceArea", getDeviceArea())
            .append("projectName", getProjectName())
            .append("configJson", getConfigJson())
            .append("createdBy", getCreatedBy())
            .append("createdDate", getCreatedDate())
            .append("modifyBy", getModifyBy())
            .append("modifyDate", getModifyDate())
            .append("yn", getYn())
            .toString();
    }
}
