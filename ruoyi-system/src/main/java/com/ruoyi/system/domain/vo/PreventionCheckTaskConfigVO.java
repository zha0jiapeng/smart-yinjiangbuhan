package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PreventionCheckTaskConfigVO {

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

    /**
     * 装置名称
     */
    private String deviceName;
    /**
     * 分析单元
     */
    private String analysisUnit;
    /**
     * 安全风险事件
     */
    private String dangerEvent;
    /**
     * 隐患排查内容
     */
    private String hiddenContent;
    /**
     * 周期
     */
    private String cycle;
    /**
     * 单位
     */
    private String unit;

    private String taskState;

    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAnalysisUnit() {
        return analysisUnit;
    }

    public void setAnalysisUnit(String analysisUnit) {
        this.analysisUnit = analysisUnit;
    }

    public String getDangerEvent() {
        return dangerEvent;
    }

    public void setDangerEvent(String dangerEvent) {
        this.dangerEvent = dangerEvent;
    }

    public String getHiddenContent() {
        return hiddenContent;
    }

    public void setHiddenContent(String hiddenContent) {
        this.hiddenContent = hiddenContent;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
