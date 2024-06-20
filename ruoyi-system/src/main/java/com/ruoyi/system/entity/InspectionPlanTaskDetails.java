package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划任务详情表(InspectionPlanTaskDetails)实体类
 *
 * @author makejava
 * @since 2022-11-30 16:39:42
 */
public class InspectionPlanTaskDetails implements Serializable {
    private static final long serialVersionUID = 577881543598437272L;
    
    private Integer id;
    /**
     * 计划任务
     */
    private Integer planTaskId;
    /**
     * 点位名称
     */
    private String pointName;
    /**
     * 点位ID
     */
    private Integer pointId;
    /**
     * 检查人员
     */
    private String checkUser;
    /**
     * 检查时间
     */
    private String checkTime;
    /**
     * 检查状态
     */
    private String checkState;
    /**
     * 检查说明
     */
    private String checkInfo;
    
    private Date createTime;
    
    private Date updateTime;
    /**
     * 项目内容
     */
    private String formData;

    /**
     * 表单样式
     */
    private String formJson;

    public String getFormJson() {
        return formJson;
    }

    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanTaskId() {
        return planTaskId;
    }

    public void setPlanTaskId(Integer planTaskId) {
        this.planTaskId = planTaskId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
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

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

}

