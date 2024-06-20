package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目概况实例类
 *
 * @author 333
 * @date 2023/07/20 18:28
 */
@TableName("project_overview")
public class ProjectOverview extends BaseDomain implements Serializable {

    /**
     *
     */
    @TableId("id")
    private Integer id;

    /**
     * 建设单位
     */
    @TableField("construct_unit")
    private String constructUnit;

    /**
     * 承建单位
     */
    @TableField("contract_unit")
    private String contractUnit;

    /**
     * 施工单位
     */
    @TableField("construction_unit")
    private String constructionUnit;

    /**
     * 监理单位
     */
    @TableField("supervision_unit")
    private String supervisionUnit;

    /**
     * 项目名称
     */
    @TableField("entry_name")
    private String entryName;

    /**
     * 项目位置
     */
    @TableField("entry_address")
    private String entryAddress;

    /**
     * 项目性质
     */
    @TableField("entry_nature")
    private String entryNature;

    /**
     * 项目面积
     */
    @TableField("entry_area")
    private String entryArea;

    /**
     * 地上部分面积
     */
    @TableField("up_area")
    private String upArea;

    /**
     * 地下部分面积
     */
    @TableField("dw_area")
    private String dwArea;

    /**
     * 项目经纬度
     */
    @TableField("entry_latitude")
    private String entryLatitude;

    /**
     * 建筑总高度
     */
    @TableField("build_height")
    private String buildHeight;

    /**
     * 项目总投资额
     */
    @TableField("invest_volume")
    private String investVolume;

    /**
     * 项目状态
     */
    @TableField("entry_status")
    private String entryStatus;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("created_date")
    private Date createdDate;

    /**
     * 更新人
     */
    @TableField("modify_by")
    private String modifyBy;

    /**
     * 更新时间
     */
    @TableField("modify_date")
    private Date modifyDate;

    /**
     * 逻辑删除标识 0删除 1正常
     */
    @TableField("yn")
    private Integer yn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConstructUnit() {
        return constructUnit;
    }

    public void setConstructUnit(String constructUnit) {
        this.constructUnit = constructUnit;
    }

    public String getContractUnit() {
        return contractUnit;
    }

    public void setContractUnit(String contractUnit) {
        this.contractUnit = contractUnit;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getSupervisionUnit() {
        return supervisionUnit;
    }

    public void setSupervisionUnit(String supervisionUnit) {
        this.supervisionUnit = supervisionUnit;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryAddress() {
        return entryAddress;
    }

    public void setEntryAddress(String entryAddress) {
        this.entryAddress = entryAddress;
    }

    public String getEntryNature() {
        return entryNature;
    }

    public void setEntryNature(String entryNature) {
        this.entryNature = entryNature;
    }

    public String getEntryArea() {
        return entryArea;
    }

    public void setEntryArea(String entryArea) {
        this.entryArea = entryArea;
    }

    public String getUpArea() {
        return upArea;
    }

    public void setUpArea(String upArea) {
        this.upArea = upArea;
    }

    public String getDwArea() {
        return dwArea;
    }

    public void setDwArea(String dwArea) {
        this.dwArea = dwArea;
    }

    public String getEntryLatitude() {
        return entryLatitude;
    }

    public void setEntryLatitude(String entryLatitude) {
        this.entryLatitude = entryLatitude;
    }

    public String getBuildHeight() {
        return buildHeight;
    }

    public void setBuildHeight(String buildHeight) {
        this.buildHeight = buildHeight;
    }

    public String getInvestVolume() {
        return investVolume;
    }

    public void setInvestVolume(String investVolume) {
        this.investVolume = investVolume;
    }

    public String getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    @Override
    public String toString() {
        return "ProjectOverview{" +
                "id=" + id +
                ", constructUnit=" + constructUnit + '\'' +
                ", contractUnit=" + contractUnit + '\'' +
                ", constructionUnit=" + constructionUnit + '\'' +
                ", supervisionUnit=" + supervisionUnit + '\'' +
                ", entryName=" + entryName + '\'' +
                ", entryAddress=" + entryAddress + '\'' +
                ", entryNature=" + entryNature + '\'' +
                ", entryArea=" + entryArea + '\'' +
                ", upArea=" + upArea + '\'' +
                ", dwArea=" + dwArea + '\'' +
                ", entryLatitude=" + entryLatitude + '\'' +
                ", buildHeight=" + buildHeight + '\'' +
                ", investVolume=" + investVolume + '\'' +
                ", entryStatus=" + entryStatus + '\'' +
                ", createdBy=" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifyBy=" + modifyBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", yn=" + yn +
                '}';
    }
}
