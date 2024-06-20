package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 隐患录入记录表(PreventionHiddenRegister)实体类
 *
 * @author makejava
 * @since 2022-11-28 12:12:00
 */
public class PreventionHiddenRegister implements Serializable {
    private static final long serialVersionUID = 378325283231550558L;
    
    private Integer id;
    /**
     * 隐患排查类型

     */
    private String hiddenCheckType;
    /**
     * 隐患来源
     */
    private String hiddenSource;
    /**
     * 隐患排查人
     */
    private String hiddenCheckUser;
    /**
     * 隐患排查时间
     */
    private String hiddenCheckTime;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门ID
     */
    private Integer deptId;
    /**
     * 隐患分类

     */
    private String hiddenType;
    /**
     * 所属类别

     */
    private String hiddenBelongType;
    /**
     * 隐患级别

     */
    private String hiddenLevel;
    /**
     * 可否接受

     */
    private String acceptable;
    /**
     * 隐患录入依据
     */
    private String hiddenEntryBasis;
    /**
     * 排查级别
     */
    private String checkLevel;
    /**
     * 隐患描述
     */
    private String hiddenInfo;
    /**
     * 建议整改措施
     */
    private String hiddenRectificationMeasures;
    /**
     * 整改责任人
     */
    private String rectificationUserName;
    /**
     * 整改责任人ID
     */
    private Integer rectificationUserId;
    /**
     * 复核人
     */
    private String reviewUserName;
    /**
     * 复核人ID
     */
    private Integer reviewUserId;
    /**
     * 整改开始日期
     */
    private String rectificationStartTime;
    /**
     * 整改结束日期
     */
    private String rectificationEndTime;
    /**
     * 隐患照片文件路径
     */
    private String hiddenImageFileUrl;
    /**
     * 隐患照片文件名称
     */
    private String hiddenImageFileName;
    /**
     * 隐患照片文件ID
     */
    private Integer hiddenImageFileId;
    /**
     * 登记人
     */
    private String registerUserName;
    /**
     * 登记人ID
     */
    private Integer registerUserId;
    /**
     * 隐患登记后通过或驳回 0-未操作 1-通过 2-驳回
     */
    private Integer hiddenState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 驳回或通过说明
     */
    private String hiddenStateInfo;
    /**
     * 整改照片路径
     */
    private String completeHiddenFileUrl;
    /**
     * 整改照片名称
     */
    private String completeHiddenFileName;
    /**
     * 整改照片ID
     */
    private Integer completeHiddenFileId;
    /**
     * 治理资金
     */
    private String hiddenManagingFunds;
    /**
     * 整改完成情况
     */
    private String hiddenCompleteInfo;
    /**
     * 整改完成时间
     */
    private String hiddenCompleteTime;
    /**
     * 复核情况
     */
    private String hiddenReviewInfo;
    /**
     * 复核时间
     */
    private String hiddenReviewTime;
    /**
     * 隐患进度
     */
    private String hiddenProgress;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHiddenCheckType() {
        return hiddenCheckType;
    }

    public void setHiddenCheckType(String hiddenCheckType) {
        this.hiddenCheckType = hiddenCheckType;
    }

    public String getHiddenSource() {
        return hiddenSource;
    }

    public void setHiddenSource(String hiddenSource) {
        this.hiddenSource = hiddenSource;
    }

    public String getHiddenCheckUser() {
        return hiddenCheckUser;
    }

    public void setHiddenCheckUser(String hiddenCheckUser) {
        this.hiddenCheckUser = hiddenCheckUser;
    }

    public String getHiddenCheckTime() {
        return hiddenCheckTime;
    }

    public void setHiddenCheckTime(String hiddenCheckTime) {
        this.hiddenCheckTime = hiddenCheckTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getHiddenType() {
        return hiddenType;
    }

    public void setHiddenType(String hiddenType) {
        this.hiddenType = hiddenType;
    }

    public String getHiddenBelongType() {
        return hiddenBelongType;
    }

    public void setHiddenBelongType(String hiddenBelongType) {
        this.hiddenBelongType = hiddenBelongType;
    }

    public String getHiddenLevel() {
        return hiddenLevel;
    }

    public void setHiddenLevel(String hiddenLevel) {
        this.hiddenLevel = hiddenLevel;
    }

    public String getAcceptable() {
        return acceptable;
    }

    public void setAcceptable(String acceptable) {
        this.acceptable = acceptable;
    }

    public String getHiddenEntryBasis() {
        return hiddenEntryBasis;
    }

    public void setHiddenEntryBasis(String hiddenEntryBasis) {
        this.hiddenEntryBasis = hiddenEntryBasis;
    }

    public String getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getHiddenInfo() {
        return hiddenInfo;
    }

    public void setHiddenInfo(String hiddenInfo) {
        this.hiddenInfo = hiddenInfo;
    }

    public String getHiddenRectificationMeasures() {
        return hiddenRectificationMeasures;
    }

    public void setHiddenRectificationMeasures(String hiddenRectificationMeasures) {
        this.hiddenRectificationMeasures = hiddenRectificationMeasures;
    }

    public String getRectificationUserName() {
        return rectificationUserName;
    }

    public void setRectificationUserName(String rectificationUserName) {
        this.rectificationUserName = rectificationUserName;
    }

    public Integer getRectificationUserId() {
        return rectificationUserId;
    }

    public void setRectificationUserId(Integer rectificationUserId) {
        this.rectificationUserId = rectificationUserId;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public Integer getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(Integer reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getRectificationStartTime() {
        return rectificationStartTime;
    }

    public void setRectificationStartTime(String rectificationStartTime) {
        this.rectificationStartTime = rectificationStartTime;
    }

    public String getRectificationEndTime() {
        return rectificationEndTime;
    }

    public void setRectificationEndTime(String rectificationEndTime) {
        this.rectificationEndTime = rectificationEndTime;
    }

    public String getHiddenImageFileUrl() {
        return hiddenImageFileUrl;
    }

    public void setHiddenImageFileUrl(String hiddenImageFileUrl) {
        this.hiddenImageFileUrl = hiddenImageFileUrl;
    }

    public String getHiddenImageFileName() {
        return hiddenImageFileName;
    }

    public void setHiddenImageFileName(String hiddenImageFileName) {
        this.hiddenImageFileName = hiddenImageFileName;
    }

    public Integer getHiddenImageFileId() {
        return hiddenImageFileId;
    }

    public void setHiddenImageFileId(Integer hiddenImageFileId) {
        this.hiddenImageFileId = hiddenImageFileId;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public Integer getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(Integer registerUserId) {
        this.registerUserId = registerUserId;
    }

    public Integer getHiddenState() {
        return hiddenState;
    }

    public void setHiddenState(Integer hiddenState) {
        this.hiddenState = hiddenState;
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

    public String getHiddenStateInfo() {
        return hiddenStateInfo;
    }

    public void setHiddenStateInfo(String hiddenStateInfo) {
        this.hiddenStateInfo = hiddenStateInfo;
    }

    public String getCompleteHiddenFileUrl() {
        return completeHiddenFileUrl;
    }

    public void setCompleteHiddenFileUrl(String completeHiddenFileUrl) {
        this.completeHiddenFileUrl = completeHiddenFileUrl;
    }

    public String getCompleteHiddenFileName() {
        return completeHiddenFileName;
    }

    public void setCompleteHiddenFileName(String completeHiddenFileName) {
        this.completeHiddenFileName = completeHiddenFileName;
    }

    public Integer getCompleteHiddenFileId() {
        return completeHiddenFileId;
    }

    public void setCompleteHiddenFileId(Integer completeHiddenFileId) {
        this.completeHiddenFileId = completeHiddenFileId;
    }

    public String getHiddenManagingFunds() {
        return hiddenManagingFunds;
    }

    public void setHiddenManagingFunds(String hiddenManagingFunds) {
        this.hiddenManagingFunds = hiddenManagingFunds;
    }

    public String getHiddenCompleteInfo() {
        return hiddenCompleteInfo;
    }

    public void setHiddenCompleteInfo(String hiddenCompleteInfo) {
        this.hiddenCompleteInfo = hiddenCompleteInfo;
    }

    public String getHiddenCompleteTime() {
        return hiddenCompleteTime;
    }

    public void setHiddenCompleteTime(String hiddenCompleteTime) {
        this.hiddenCompleteTime = hiddenCompleteTime;
    }

    public String getHiddenReviewInfo() {
        return hiddenReviewInfo;
    }

    public void setHiddenReviewInfo(String hiddenReviewInfo) {
        this.hiddenReviewInfo = hiddenReviewInfo;
    }

    public String getHiddenReviewTime() {
        return hiddenReviewTime;
    }

    public void setHiddenReviewTime(String hiddenReviewTime) {
        this.hiddenReviewTime = hiddenReviewTime;
    }

    public String getHiddenProgress() {
        return hiddenProgress;
    }

    public void setHiddenProgress(String hiddenProgress) {
        this.hiddenProgress = hiddenProgress;
    }

}

