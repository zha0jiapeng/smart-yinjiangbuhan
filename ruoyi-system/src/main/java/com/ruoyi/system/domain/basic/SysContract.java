package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.ProjectMpp;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@TableName("sys_contract")
public class SysContract {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 合同编码
     */
    private String contractCode;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 签订日期
     */
    private String signingDate;
    /**
     * 供应商id
     */
    private String supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    /**
     * 结算比例
     */
    private String settlementProportion;
    /**
     * 结算金额
     */
    private BigDecimal settlementAmount;
    /**
     * 剩余金额
     */
    private BigDecimal surplusAmount;
    /**
     * 附件id
     */
    private String enclosureId;
    /**
     * 附件名称
     */
    private String enclosureName;
    /**
     * 附件地址
     */
    private String enclosureUrl;
    /**
     * 模型ids
     */
    private String modelIds;
    /**
     * 兼容参数
     */
    @TableField(exist = false)
    private List<Integer> modelIdsParam;

    @TableField(exist = false)
    private List<ProjectMpp> projectMppList;

    @TableField(exist = false)
    private List<SysContractCostDetails> sysContractCostDetails;
    /**
     * 批次信息
     */

    private String batchDetail;
    /**
     * 创建人id
     */
    private Integer createdById;
    /**
     * 创建人名称
     */
    private String createdByName;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人id
     */
    private Integer modifyById;
    /**
     * 更新人名称
     */
    private String modifyByName;
    /**
     * 更新时间
     */
    private Timestamp modifyDate;
    /**
     * 逻辑删除标识 1正常 0 删除
     */
    private Integer yn;
}
