package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 隐患排查计划任务(PreventionPlanTask)实体类
 *
 * @author makejava
 * @since 2022-12-17 13:22:34
 */
public class PreventionPlanTask implements Serializable {
    private static final long serialVersionUID = -66743975155747330L;
    
    private Integer id;
    /**
     * 计划ID
     */
    private Integer preventionPlanId;
    /**
     * 任务开始时间
     */
    private String taskStartTime;
    /**
     * 任务结束时间
     */
    private String taskEndTime;
    /**
     * 执行人员ID
     */
    private String planUserIds;
    /**
     * 执行人员名称
     */
    private String planUserNames;
    /**
     * 排查人
     */
    private String checkUserName;
    /**
     * 排查人ID
     */
    private Integer checkUserId;
    /**
     * 排查时间
     */
    private String checkTime;
    /**
     * 任务状态
     */
    private String checkState;
    /**
     * 排查说明
     */
    private String checkInfo;
    
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

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getPlanUserIds() {
        return planUserIds;
    }

    public void setPlanUserIds(String planUserIds) {
        this.planUserIds = planUserIds;
    }

    public String getPlanUserNames() {
        return planUserNames;
    }

    public void setPlanUserNames(String planUserNames) {
        this.planUserNames = planUserNames;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
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

