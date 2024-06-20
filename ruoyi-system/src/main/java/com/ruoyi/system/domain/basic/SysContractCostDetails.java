package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysContractCostDetails {
    /**
     * 批次
     */
    private Integer orderNum;
    /**
     * 进度比例
     */
    private Integer proportion;
    /**
     * 打款金额
     */
    private BigDecimal amount;
}
