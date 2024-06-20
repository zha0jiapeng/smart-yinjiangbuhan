package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 提醒表(SysRemind)实体类
 *
 * @author makejava
 * @since 2022-12-17 15:16:40
 */
public class SysRemind implements Serializable {
    private static final long serialVersionUID = 445617274704556062L;

    public static final String TYPE_MONITOR = "报警";
    public static final String TYPE_ALARM = "警告";
    public static final String TYPE_PROMPT = "提示";

    private Integer id;
    /**
     * 提醒类型---警告---报警---提示
     */
    private String remindType;
    /**
     * 提醒内容
     */
    private String remindContent;
    /**
     * 提醒人员
     */
    private Integer remindUserId;
    /**
     * 是否已读
     */
    private Integer whetherRead;
    /**
     * 是否删除
     */
    private Integer whetherDelete;
    
    private Date createTime;
    
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    public Integer getRemindUserId() {
        return remindUserId;
    }

    public void setRemindUserId(Integer remindUserId) {
        this.remindUserId = remindUserId;
    }

    public Integer getWhetherRead() {
        return whetherRead;
    }

    public void setWhetherRead(Integer whetherRead) {
        this.whetherRead = whetherRead;
    }

    public Integer getWhetherDelete() {
        return whetherDelete;
    }

    public void setWhetherDelete(Integer whetherDelete) {
        this.whetherDelete = whetherDelete;
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

