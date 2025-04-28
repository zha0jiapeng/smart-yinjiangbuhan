package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 人员证照对象 employee_certificates
 *
 * @author ruoyi
 * @date 2025-03-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee_certificates")
@ApiModel(value = "EmployeeCertificates", description = "人员证照对象 employee_certificates")
public class EmployeeCertificates implements Serializable {

    private static final long serialVersionUID=1L;


    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /** 人员姓名 */
    @ApiModelProperty(value = "人员姓名")
    private String userName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /** 生效日期 */
    @ApiModelProperty(value = "生效日期")
    private String effectiveDate;

    /** 失效日期 */
    @ApiModelProperty(value = "失效日期")
    private String expiryDate;

    /** 证书名称 */
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    /** 特种作业类型 */
    @ApiModelProperty(value = "特种作业类型")
    private String workType;

    @TableField(exist = false)
    private String workTypeName;

    /** 准操项目 */
    @ApiModelProperty(value = "准操项目")
    private String operationItem;

    @TableField(exist = false)
    private String operationItemName;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号")
    private String certificateNumber;

    /** 证书图片地址 */
    @ApiModelProperty(value = "证书图片地址")
    private String attachmentUrl;

    /** 证书文件转base64 */
    @ApiModelProperty(value = "证书文件转base64")
    private String photoBase64;

    /** 证书文件后缀: 若photoBase64填了，必填 */
    @ApiModelProperty(value = "证书文件后缀: 若photoBase64填了，必填")
    private String fileSuffix;

    /** 发证书机构 */
    @ApiModelProperty(value = "发证书机构")
    private String issuingAuthority;

    /** 发证时间，指定格式:yyyy-MM-dd */
    @ApiModelProperty(value = "发证时间，指定格式:yyyy-MM-dd")
    private String issuingDate;

    /** 一次复审日期 */
    @ApiModelProperty(value = "一次复审日期")
    private String reCheckOneDate;

    /** 二次复审日期 */
    @ApiModelProperty(value = "二次复审日期")
    private String reCheckTwoDate;

    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

}