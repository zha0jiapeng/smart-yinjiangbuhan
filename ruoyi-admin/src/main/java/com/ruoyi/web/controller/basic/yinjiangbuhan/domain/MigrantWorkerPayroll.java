package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.ruoyi.common.annotation.Excel;
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
 * @date 2025-03-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("migrant_worker_payroll")
@ApiModel(value = "MigrantWorkerPayroll", description = "薪资对象 migrant_worker_payroll")
public class MigrantWorkerPayroll implements Serializable {

    private static final long serialVersionUID=1L;


    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    @Excel(name = "人员姓名")
    private String userName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    @Excel(name = "身份证号")
    private String idCard;

    /** 月份 */
    @ApiModelProperty(value = "月份")
    @Excel(name = "月份")
    private String month;

    /** 工作天数 */
    @ApiModelProperty(value = "工作天数")
    @Excel(name = "工作天数")
    private String workDay;

    /** 加班天数（默认0） */
    @ApiModelProperty(value = "加班天数（默认0）")
    @Excel(name = "加班天数")
    private String overWorkDay;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资")
    @Excel(name = "实发工资")
    private String wages;

    /** 发放状态（WAGES_GIVE_OUT_YES  已发、WAGES_GIVE_OUT_NO  未发） */
    @ApiModelProperty(value = "发放状态（WAGES_GIVE_OUT_YES  已发、WAGES_GIVE_OUT_NO  未发）")
    @Excel(name = "发放状态")
    private String giveOutStatus;

    /** 发放日期 指定格式:yyyy-MM-dd */
    @ApiModelProperty(value = "发放日期 指定格式:yyyy-MM-dd")
    @Excel(name = "发放日期")
    private String giveOutDate;

    /** 是否总包代发:Y是、N否 */
    @ApiModelProperty(value = "是否总包代发:Y是、N否")
    @Excel(name = "是否总包代发:Y是、N否")
    private String isTpgrant;

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