package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.FileManage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 隐患排查计划(PreventionPlan)实体类
 *
 * @author makejava
 * @since 2022-12-17 13:21:45
 */
public class PreventionPlan implements Serializable {
    private static final long serialVersionUID = 431070143505534610L;
    
    private Integer id;
    /**
     * 责任部门ID
     */
    private Integer deptId;
    /**
     * 责任部门
     */
    private String deptName;
    /**
     * 责任人

     */
    private String userNames;
    /**
     * 责任人
        责任人IDS
     */
    private String userIds;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 排查类型
     */
    private String checkType;
    /**
     * 排查范围
     */
    private String checkScope;
    /**
     * 排查级别

     */
    private String checkLevel;
    /**
     * 开始时间
     */
    private String checkStartTime;
    /**
     * 结束时间
     */
    private String checkEndTime;
    /**
     * 工作联系人
     */
    private String workContactPerson;
    /**
     * 状态（0-已发布 1-未发布）
     */
    private Integer planState;
    /**
     * 备注
     */
    private String planInfo;
    /**
     * 周期
     */
    private String cycle;
    /**
     * 单位
     */
    private String unit;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建人ID
     */
    private Integer createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String fileId;

    private List<FileManage> fileManageList;

    public List<FileManage> getFileManageList() {
        return fileManageList;
    }

    public void setFileManageList(List<FileManage> fileManageList) {
        this.fileManageList = fileManageList;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckScope() {
        return checkScope;
    }

    public void setCheckScope(String checkScope) {
        this.checkScope = checkScope;
    }

    public String getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getWorkContactPerson() {
        return workContactPerson;
    }

    public void setWorkContactPerson(String workContactPerson) {
        this.workContactPerson = workContactPerson;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
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

