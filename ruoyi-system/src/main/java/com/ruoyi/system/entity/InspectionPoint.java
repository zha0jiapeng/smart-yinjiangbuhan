package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检点位(InspectionPoint)实体类
 *
 * @author makejava
 * @since 2022-11-30 17:31:09
 */
public class InspectionPoint implements Serializable {
    private static final long serialVersionUID = -84932881788535678L;
    
    private Integer id;
    /**
     * 点编号
     */
    private String pointCode;
    /**
     * 点名称
     */
    private String pointName;
    /**
     * 点状态
     */
    private String pointState;
    /**
     * 点类型
     */
    private String pointType;
    /**
     * 最少拍照数量
     */
    private String minImages;
    /**
     * 最多拍照数量
     */
    private String maxImages;
    /**
     * 巡检路线ID
     */
    private Integer routeId;
    /**
     * 巡检路线
     */
    private String routeName;
    /**
     * 检查项目ID
     */
    private Integer projectId;
    /**
     * 检查项目
     */
    private String projectName;
    /**
     * 分类目录
     */
    private String subjectCatalog;
    /**
     * 备注
     */
    private String remarks;
    
    private Date createTime;
    
    private Date updateTime;
    /**
     * 二维码路径
     */
    private String codeImageFileUrl;
    /**
     * 二维码文件ID
     */
    private Integer codeImageFileId;

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

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointState() {
        return pointState;
    }

    public void setPointState(String pointState) {
        this.pointState = pointState;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getMinImages() {
        return minImages;
    }

    public void setMinImages(String minImages) {
        this.minImages = minImages;
    }

    public String getMaxImages() {
        return maxImages;
    }

    public void setMaxImages(String maxImages) {
        this.maxImages = maxImages;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSubjectCatalog() {
        return subjectCatalog;
    }

    public void setSubjectCatalog(String subjectCatalog) {
        this.subjectCatalog = subjectCatalog;
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

    public String getCodeImageFileUrl() {
        return codeImageFileUrl;
    }

    public void setCodeImageFileUrl(String codeImageFileUrl) {
        this.codeImageFileUrl = codeImageFileUrl;
    }

    public Integer getCodeImageFileId() {
        return codeImageFileId;
    }

    public void setCodeImageFileId(Integer codeImageFileId) {
        this.codeImageFileId = codeImageFileId;
    }

}

