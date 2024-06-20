package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (PreventionPlanFile)实体类
 *
 * @author makejava
 * @since 2022-12-17 13:22:15
 */
public class PreventionPlanFile implements Serializable {
    private static final long serialVersionUID = 626052584317502498L;
    
    private Integer id;
    
    private Integer preventionPlanId;
    
    private Integer fileId;
    
    private String fileName;
    
    private String fileUrl;
    
    private Date createTime;
    
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPreventionPlanId() {
        return preventionPlanId;
    }

    public void setPreventionPlanId(Integer preventionPlanId) {
        this.preventionPlanId = preventionPlanId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

