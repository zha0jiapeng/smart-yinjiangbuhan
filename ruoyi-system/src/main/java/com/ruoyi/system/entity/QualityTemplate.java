package com.ruoyi.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 质量模板表(QualityTemplate)实体类
 *
 * @author makejava
 * @since 2022-12-25 14:48:58
 */
public class QualityTemplate implements Serializable {
    private static final long serialVersionUID = 465736486761215665L;

    private Integer id;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板表单
     */
    private String templateForm;
    /**
     * 创建人
     */
    private String createUser;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateForm() {
        return templateForm;
    }

    public void setTemplateForm(String templateForm) {
        this.templateForm = templateForm;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

