package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DateAnalysis {
    /**
     * 合同金额
     */
    private BigDecimal contractAmount;
    /**
     * 完成产值
     */
    private BigDecimal okOutPutValue;
    /**
     * 完成占比
     */
    private String proportion;

    /**
     * 实际工期
     */
    private Integer duration;
    /**
     * 正常工期
     */
    private Integer day;
    /**
     * 项目倒计时
     */
    private Integer projectOutDay;

    /**
     * 合同工期
     */
    private Integer contractDay;
}
