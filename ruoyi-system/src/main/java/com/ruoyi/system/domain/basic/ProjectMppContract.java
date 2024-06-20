package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectMppContract {
    /**
     * 合同编码
     */
    private String contractCode;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 签订日期
     */
    private Date signDate;
}
