package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 代办表(SysActing)实体类
 *
 * @author makejava
 * @since 2022-12-05 17:25:25
 */
public class SysActing implements Serializable {
    private static final long serialVersionUID = 934211945221348503L;
    
    private Integer id;
    /**
     * 标题名称
     */
    private String titleName;
    /**
     * 标题类型
     */
    private String titleType;
    /**
     * 元数据ID
     */
    private Integer sourceId;
    /**
     * 代办状态 1-完成  0-未完成
     */
    private Integer state;
    
    private Date createTime;
    
    private Date updateTime;
    /**
     * 标题详情
     */
    private String titleInfo;
    /**
     * 发起人
     */
    private String originatorName;
    /**
     * 发起人ID
     */
    private Integer originatorId;
    /**
     * 执行人ID
     */
    private String executorName;
    /**
     * 执行人名称
     */
    private Integer executorId;
    /**
     * 代办完成时间
     */
    private String completeTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getState() {
        if (state == null) {
            state = 0;
        }
        return state;
    }

    public void setState(Integer state) {
        if (state == null) {
            state = 0;
        }
        this.state = state;
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

    public String getTitleInfo() {
        return titleInfo;
    }

    public void setTitleInfo(String titleInfo) {
        this.titleInfo = titleInfo;
    }

    public String getOriginatorName() {
        return originatorName;
    }

    public void setOriginatorName(String originatorName) {
        this.originatorName = originatorName;
    }

    public Integer getOriginatorId() {
        return originatorId;
    }

    public void setOriginatorId(Integer originatorId) {
        this.originatorId = originatorId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public Integer getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

}

