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
 * @date 2025-03-10
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("employee_certificates")
@ApiModel(value = "EmployeeCertificates", description = "人员证照对象 employee_certificates")
public class EmployeeCertificates implements Serializable {

private static final long serialVersionUID=1L;


    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "${column.columnComment}")
    private Long id;

    /** 证书名称 */
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号")
    private String certificateNumber;

    /** 证书类型 */
    @ApiModelProperty(value = "证书类型")
    private String certificateType;

    /** 作业类别 */
    @ApiModelProperty(value = "作业类别")
    private String jobCategory;

    /** 证书等级 */
    @ApiModelProperty(value = "证书等级")
    private String certificateLevel;

    /** 生效日期 */
    @ApiModelProperty(value = "生效日期")
    private String effectiveDate;

    /** 失效日期 */
    @ApiModelProperty(value = "失效日期")
    private String expiryDate;

    /** 附件地址 */
    @ApiModelProperty(value = "附件地址")
    private String attachmentUrl;

    /** 人员id */
    @ApiModelProperty(value = "人员id")
    private Long sysWorkPeopleId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 人员姓名 */
    @ApiModelProperty(value = "人员姓名")
    private String userName;

    /** 工种 */
    @ApiModelProperty(value = "工种")
    private String workType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司")
    private String company;

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