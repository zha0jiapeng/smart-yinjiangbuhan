package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 双重预防-风险分析清单明细(PreventionSecurityRisk)实体类
 *
 * @author makejava
 * @since 2022-11-17 11:18:53
 */
public class PreventionSecurityRisk implements Serializable {
    private static final long serialVersionUID = 980778347754032941L;
    
    private Integer id;
    /**
     * 装置ID
     */
    private Integer deviceId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门ID
     */
    private Integer deptId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 分析单元
     */
    private String analysisUnit;
    /**
     * 安全风险事件
     */
    private String dangerEvent;
    /**
     * 措施分类1
     */
    private String measuresOne;
    /**
     * 措施分类2
     */
    private String measuresTwo;
    /**
     * 措施分类3
     */
    private String measuresThree;
    /**
     * 管控措施
     */
    private String controlMeasures;
    /**
     * 隐患排查内容
     */
    private String hiddenContent;
    /**
     * 岗位负责人
     */
    private String deptUserName;
    /**
     * 周期
     */
    private String cycle;
    /**
     * 单位
     */
    private String unit;
    /**
     * 隐患问题描述
     */
    private String hiddenInfo;
    /**
     * 备注
     */
    private String remarks;

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

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

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

    public String getMeasuresOne() {
        return measuresOne;
    }

    public void setMeasuresOne(String measuresOne) {
        this.measuresOne = measuresOne;
    }

    public String getMeasuresTwo() {
        return measuresTwo;
    }

    public void setMeasuresTwo(String measuresTwo) {
        this.measuresTwo = measuresTwo;
    }

    public String getMeasuresThree() {
        return measuresThree;
    }

    public void setMeasuresThree(String measuresThree) {
        this.measuresThree = measuresThree;
    }

    public String getControlMeasures() {
        return controlMeasures;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
    }

    public String getHiddenContent() {
        return hiddenContent;
    }

    public void setHiddenContent(String hiddenContent) {
        this.hiddenContent = hiddenContent;
    }

    public String getDeptUserName() {
        return deptUserName;
    }

    public void setDeptUserName(String deptUserName) {
        this.deptUserName = deptUserName;
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

    public String getHiddenInfo() {
        return hiddenInfo;
    }

    public void setHiddenInfo(String hiddenInfo) {
        this.hiddenInfo = hiddenInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

