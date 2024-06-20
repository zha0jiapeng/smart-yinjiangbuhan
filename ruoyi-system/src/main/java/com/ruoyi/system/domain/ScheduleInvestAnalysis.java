package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("schedule_invest_analysis")
public class ScheduleInvestAnalysis extends BaseDomain {
    /**
     * 分析名称
     */
    private String analysisName;
    /**
     * 色值
     */
    private String colour;
    /**
     * 占比
     */
    private BigDecimal proportion;
    /**
     * 周
     */
    private String weekBase;
    /**
     * 年份
     */
    private String yearBase;
    /**
     * 月份
     */
    private String monthBase;

}
