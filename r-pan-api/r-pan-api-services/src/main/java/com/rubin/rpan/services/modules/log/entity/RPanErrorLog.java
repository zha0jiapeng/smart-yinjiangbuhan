package com.rubin.rpan.services.modules.log.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RPanErrorLog implements Serializable {

    private static final long serialVersionUID = -3465718425314172813L;

    private Long id;

    private String logContent;

    private Integer logStatus;

    private Long createUser;

    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    public RPanErrorLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Integer getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Integer logStatus) {
        this.logStatus = logStatus;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPanErrorLog that = (RPanErrorLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(logContent, that.logContent) &&
                Objects.equals(logStatus, that.logStatus) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logContent, logStatus, createUser, createTime, updateUser, updateTime);
    }

    @Override
    public String toString() {
        return "RPanErrorLog{" +
                "id=" + id +
                ", logContent='" + logContent + '\'' +
                ", logStatus=" + logStatus +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }

}