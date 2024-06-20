package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (PreventionDanger)实体类
 *
 * @author makejava
 * @since 2022-11-19 10:46:00
 */
public class PreventionDanger implements Serializable {
    private static final long serialVersionUID = 295965697366049726L;
    
    private Integer id;
    /**
     * 重大危险源名称
     */
    private String dangerName;
    /**
     * 主要物料
     */
    private String material;
    /**
     * 最大存储量
     */
    private String maxStorage;
    /**
     * 级指标R 值
     */
    private String rValue;
    /**
     * 重大危险源级别
     */
    private String dangerLevel;
    /**
     * 备注信息
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

    public String getDangerName() {
        return dangerName;
    }

    public void setDangerName(String dangerName) {
        this.dangerName = dangerName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaxStorage() {
        return maxStorage;
    }

    public void setMaxStorage(String maxStorage) {
        this.maxStorage = maxStorage;
    }

    public String getRValue() {
        return rValue;
    }

    public void setRValue(String rValue) {
        this.rValue = rValue;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
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

