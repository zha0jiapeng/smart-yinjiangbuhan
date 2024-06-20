package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检计划(InspectionPlan)实体类
 *
 * @author makejava
 * @since 2022-11-29 13:49:27
 */
public class InspectionPlan implements Serializable {
    private static final long serialVersionUID = 313456457347206711L;
    
    private Integer id;
    /**
     * 计划编号
     */
    private String planCode;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 计划状态
     */
    private String planState;
    /**
     * 	
计划类型
     */
    private String planType;
    /**
     * 开始时间
     */
    private String planStartTime;
    /**
     * 结束时间
     */
    private String planEndTime;
    /**
     * 计划执行人员
     */
    private String planUserNames;
    /**
     * 计划执行人员名称
     */
    private String planUserIds;
    /**
     * 部门ID
     */
    private Integer planDeptId;
    /**
     * 部门名称
     */
    private String planDeptName;
    /**
     * 巡检路线ID
     */
    private Integer routeId;
    /**
     * 巡检路线
     */
    private String routeName;
    /**
     * 备注
     */
    private String remarks;
    
    private Date createTime;
    
    private Date updateTime;
    /**
     * 是否启用 0-是 1-否
     */
    private Integer planEnable;
    /**
     * 周期
     */
    private String cycle;
    /**
     * 单位
     */
    private String unit;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
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

    public Integer getPlanDeptId() {
        return planDeptId;
    }

    public void setPlanDeptId(Integer planDeptId) {
        this.planDeptId = planDeptId;
    }

    public String getPlanDeptName() {
        return planDeptName;
    }

    public void setPlanDeptName(String planDeptName) {
        this.planDeptName = planDeptName;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Integer getPlanEnable() {
        return planEnable;
    }

    public void setPlanEnable(Integer planEnable) {
        this.planEnable = planEnable;
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

