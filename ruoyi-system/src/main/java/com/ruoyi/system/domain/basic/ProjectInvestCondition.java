package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("project_invest_condition")
public class ProjectInvestCondition extends BaseDomain {

    /**
     * 当前年
     */
    private String currentYear;
    /**
     * 当前月
     */
    private String currentMonth;
    /**
     * 当前周
     */
    private String currentWeek;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 工程量
     */
    private String projectQuantity;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 合计
     */
    private BigDecimal totalAmount;
    /**
     * 统计分组
     */
    private String countGroup;
    /**
     * 颜色
     */
    private String r;

    private String g;

    private String b;

}
