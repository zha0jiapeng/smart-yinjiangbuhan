package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务基础数据实例类
 *
 * @author yansir
 * @date 2023/07/20 18:59
 */
@TableName("basic_data_value_config")
public class BasicDataValueConfig implements Serializable {

    /**
     * 
     */
    @TableId("id")
    private Integer id;

    /**
     * 基础数据key
     */
    @TableField("basic_key")
    private String basicKey;

    /**
     * 基础数据name
     */
    @TableField("basic_name")
    private String basicName;

    /**
     * 基础数据value
     */
    @TableField("basic_value")
    private String basicValue;

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

    public String getBasicKey() {
        return basicKey;
    }

    public void setBasicKey(String basicKey) {
        this.basicKey = basicKey;
    }

    public String getBasicName() {
        return basicName;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public String getBasicValue() {
        return basicValue;
    }

    public void setBasicValue(String basicValue) {
        this.basicValue = basicValue;
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
        return "BasicDataValueConfig{" +
                "id=" + id +
                ", basicKey=" + basicKey + '\'' +
                ", basicName=" + basicName + '\'' +
                ", basicValue=" + basicValue + '\'' +
                ", createdBy=" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", modifyBy=" + modifyBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", yn=" + yn +
                '}';
    }
}
