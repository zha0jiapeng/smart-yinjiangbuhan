package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划任务表(InspectionPlanTask)实体类
 *
 * @author makejava
 * @since 2022-11-28 17:55:24
 */
public class InspectionPlanTask implements Serializable {
    private static final long serialVersionUID = 553895032892785001L;
    
    private Integer id;
    /**
     * 计划ID
     */
    private Integer planId;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 计划执行人员
     */
    private String planUserNames;
    /**
     * 计划执行人员ID
     */
    private String planUserIds;
    /**
     * 路线ID
     */
    private Integer routeId;
    /**
     * 路线名称
     */
    private String routeName;
    /**
     * 计划任务完成状态
     */
    private String planTaskState;
    
    private Date createTime;
    
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public String getPlanUserNames() {
        return planUserNames;
    }

    public void setPlanUserNames(String planUserNames) {
        this.planUserNames = planUserNames;
    }

    public String getPlanUserIds() {
        return planUserIds;
    }

    public void setPlanUserIds(String planUserIds) {
        this.planUserIds = planUserIds;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPlanTaskState() {
        return planTaskState;
    }

    public void setPlanTaskState(String planTaskState) {
        this.planTaskState = planTaskState;
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

