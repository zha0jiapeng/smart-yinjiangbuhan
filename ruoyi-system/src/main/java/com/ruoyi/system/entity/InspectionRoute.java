package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检路线表(InspectionRoute)实体类
 *
 * @author makejava
 * @since 2022-11-25 17:11:06
 */
public class InspectionRoute implements Serializable {
    private static final long serialVersionUID = -29174437172467733L;
    
    private Integer id;
    /**
     * 路线编号
     */
    private String routeCode;
    /**
     * 路线名称
     */
    private String routeName;
    /**
     * 	
负责人
     */
    private String chargePeople;
    /**
     * 联系电话
     */
    private String phone;
    
    private Integer deptId;
    /**
     * 	
所属部门
     */
    private String deptName;
    /**
     * 所属人员
     */
    private String userName;
    
    private Integer userId;
    /**
     * 备注说明
     */
    private String remarks;
    
    private Date createTime;
    
    private Date updateTime;

    private Integer stopCheck;

    public Integer getStopCheck() {
        return stopCheck;
    }

    public void setStopCheck(Integer stopCheck) {
        this.stopCheck = stopCheck;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getChargePeople() {
        return chargePeople;
    }

    public void setChargePeople(String chargePeople) {
        this.chargePeople = chargePeople;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}

