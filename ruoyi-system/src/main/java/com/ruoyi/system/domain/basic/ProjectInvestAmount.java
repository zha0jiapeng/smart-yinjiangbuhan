package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("project_invest_amount")
public class ProjectInvestAmount extends BaseDomain {
    /**
     * 上报日期
     */
    private String uploadDate;
    /**
     * 项目累计完成
     */
   // private Double totalAmount;
    /**
     * 预计投资
     */
    private BigDecimal estimateAmount;
    /**
     * 已投资
     */
    private BigDecimal investAmount;

}
