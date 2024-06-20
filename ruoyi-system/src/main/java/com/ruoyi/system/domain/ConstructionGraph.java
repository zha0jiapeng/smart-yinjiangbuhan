package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工曲线图实例类
 *
 * @author yansir
 * @date 2023/07/24 14:00
 */
@TableName("construction_graph")
public class ConstructionGraph implements Serializable {

    /**
     * 
     */
    @TableId("id")
    private Integer id;

    /**
     * 时间
     */
    @TableField("do_date")
    private String doDate;

    /**
     * 计划
     */
    @TableField("plan_number")
    private String planNumber;

    /**
     * 实际
     */
    @TableField("reality_number")
    private String realityNumber;

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

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getRealityNumber() {
        return realityNumber;
    }

    public void setRealityNumber(String realityNumber) {
        this.realityNumber = realityNumber;
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
        return "ConstructionGraph{" +
                "id=" + id +
                ", doDate=" + doDate + '\'' +
                ", planNumber=" + planNumber + '\'' +
                ", realityNumber=" + realityNumber + '\'' +
                ", createdBy=" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifyBy=" + modifyBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", yn=" + yn +
                '}';
    }
}
