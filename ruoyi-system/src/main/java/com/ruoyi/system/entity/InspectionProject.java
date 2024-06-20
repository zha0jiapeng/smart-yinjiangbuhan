package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (InspectionProject)实体类
 *
 * @author makejava
 * @since 2022-11-25 17:10:49
 */
public class InspectionProject implements Serializable {
    private static final long serialVersionUID = 623191124312170218L;
    
    private Integer id;
    /**
     * 检查项目名称
     */
    private String projectCode;
    /**
     * 分类目录
     */
    private String subjectCatalog;
    /**
     * 所属部门id
     */
    private Integer deptId;
    /**
     * 所属部门名称
     */
    private String deptName;
    /**
     * 所属人id
     */
    private Integer userId;
    /**
     * 所属人名称
     */
    private String userName;
    /**
     * 备注说明
     */
    private String remarks;
    /**
     * 检查项目
     */
    private String formData;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getSubjectCatalog() {
        return subjectCatalog;
    }

    public void setSubjectCatalog(String subjectCatalog) {
        this.subjectCatalog = subjectCatalog;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
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

