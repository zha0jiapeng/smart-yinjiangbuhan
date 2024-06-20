package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PreventionCheckTaskVO {

    private Integer id;
    /**
     * 排查任务配置ID
     */
    private Integer checkTaskConfigId;
    /**
     * 任务状态
     */
    private String taskState;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 排查时间
     */
    private String checkTime;
    /**
     * 排查人
     */
    private String checkUserName;
    /**
     * 排查结果
     */
    private String checkResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 排查人员
     */
    private String checkUserNames;
    /**
     * 排查人员ID
     */
    private String checkUserIds;

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
     * 用户名称
     */
    private String userName;
    /**
     * 用户ID
     */
    private Integer userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckTaskConfigId() {
        return checkTaskConfigId;
    }

    public void setCheckTaskConfigId(Integer checkTaskConfigId) {
        this.checkTaskConfigId = checkTaskConfigId;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
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
}
