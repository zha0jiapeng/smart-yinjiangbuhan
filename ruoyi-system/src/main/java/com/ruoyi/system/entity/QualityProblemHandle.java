package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * (QualityProblemHandle)实体类
 *
 * @author makejava
 * @since 2023-01-19 17:36:50
 */
public class QualityProblemHandle implements Serializable {
    private static final long serialVersionUID = 842928522105494029L;
    
    private Integer id;
    /**
     * 问题ID
     */
    private Integer problemId;
    /**
     * 问题阶段
     */
    private String problemType;
    /**
     * 提交次数
     */
    private Integer frequency;
    /**
     * 处理结果
     */
    private String handleResult;
    /**
     * 处理图片
     */
    private String handleImage;
    /**
     * 处理附件文件名称
     */
    private String handleFileName;
    /**
     * 处理附件文件路径
     */
    private String handleFileUrl;
    /**
     * 是否通过 0-通过 1-未通过
     */
    private Integer pass;
    /**
     * 阶段执行人名称
     */
    private String execUserName;
    /**
     * 阶段执行人ID
     */
    private Integer execUserId;

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

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getHandleImage() {
        return handleImage;
    }

    public void setHandleImage(String handleImage) {
        this.handleImage = handleImage;
    }

    public String getHandleFileName() {
        return handleFileName;
    }

    public void setHandleFileName(String handleFileName) {
        this.handleFileName = handleFileName;
    }

    public String getHandleFileUrl() {
        return handleFileUrl;
    }

    public void setHandleFileUrl(String handleFileUrl) {
        this.handleFileUrl = handleFileUrl;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public String getExecUserName() {
        return execUserName;
    }

    public void setExecUserName(String execUserName) {
        this.execUserName = execUserName;
    }

    public Integer getExecUserId() {
        return execUserId;
    }

    public void setExecUserId(Integer execUserId) {
        this.execUserId = execUserId;
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

