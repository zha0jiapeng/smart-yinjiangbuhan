package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
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
 * 薪资对象 migrant_worker_payroll
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("migrant_worker_payroll")
@ApiModel(value = "MigrantWorkerPayroll", description = "薪资对象 migrant_worker_payroll")
public class MigrantWorkerPayroll implements Serializable {

    private static final long serialVersionUID=1L;


    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /** 人员id */
    @ApiModelProperty(value = "人员id")
    @Excel(name = "人员id")
    private Long sysWorkPeopleId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    @Excel(name = "手机号")
    private String phone;

    /** 人员姓名 */
    @ApiModelProperty(value = "人员姓名")
    @Excel(name = "人员姓名")
    private String userName;

    /** 月份 */
    @ApiModelProperty(value = "月份")
    @Excel(name = "月份")
    private String month;

    /** 应发薪资（元） */
    @ApiModelProperty(value = "应发薪资（元）")
    @Excel(name = "应发薪资（元）")
    private BigDecimal grossSalary;

    /** 实发薪资（元） */
    @ApiModelProperty(value = "实发薪资（元）")
    @Excel(name = "实发薪资（元）")
    private BigDecimal netSalary;

    /** 发放时间 */
    @ApiModelProperty(value = "发放时间")
    @Excel(name = "发放时间")
    private String paymentDate;

    /** 工资单号 */
    @ApiModelProperty(value = "工资单号")
    @Excel(name = "工资单号")
    private String payrollNumber;

    /** 工种 */
    @ApiModelProperty(value = "工种")
    @Excel(name = "工种")
    private String workType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    @Excel(name = "身份证号")
    private String idCard;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司")
    @Excel(name = "所属公司")
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