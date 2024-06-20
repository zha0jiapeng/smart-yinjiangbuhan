package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 双重预防-排查任务配置表(PreventionCheckTaskConfig)实体类
 *
 * @author makejava
 * @since 2022-11-18 13:58:19
 */
public class PreventionCheckTaskConfig implements Serializable {
    private static final long serialVersionUID = -38042483341962364L;
    
    private Integer id;
    /**
     * 风险分析清单明细ID
     */
    private Integer securityRiskId;
    /**
     * 任务开始时间
     */
    private String taskStartTime;
    /**
     * 排查人员
     */
    private String checkUserNames;
    /**
     * 排查人员ID
     */
    private String checkUserIds;
    /**
     * 是否配置 0-是 1-否
     */
    private Integer whetherConfig;
    /**
     * 是否发布 0-是 1-否
     */
    private Integer whetherRelease;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSecurityRiskId() {
        return securityRiskId;
    }

    public void setSecurityRiskId(Integer securityRiskId) {
        this.securityRiskId = securityRiskId;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getCheckUserNames() {
        return checkUserNames;
    }

    public void setCheckUserNames(String checkUserNames) {
        this.checkUserNames = checkUserNames;
    }

    public String getCheckUserIds() {
        return checkUserIds;
    }

    public void setCheckUserIds(String checkUserIds) {
        this.checkUserIds = checkUserIds;
    }

    public Integer getWhetherConfig() {
        return whetherConfig;
    }

    public void setWhetherConfig(Integer whetherConfig) {
        this.whetherConfig = whetherConfig;
    }

    public Integer getWhetherRelease() {
        return whetherRelease;
    }

    public void setWhetherRelease(Integer whetherRelease) {
        this.whetherRelease = whetherRelease;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

}

