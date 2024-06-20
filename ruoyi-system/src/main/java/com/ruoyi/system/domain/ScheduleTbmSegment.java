package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("schedule_tbm_segment")
public class ScheduleTbmSegment extends BaseDomain {
    /**
     * 类型:1:月 2:日
     */
    private Integer createType;
    /**
     * tbm计划值
     */
    private BigDecimal tbmPlanPrice;
    /**
     * schedule计划值
     */
    private BigDecimal schedulePlanPrice;
    /**
     * TBM实际值
     */
    private BigDecimal tbmRealityPrice;
    /**
     * schedule实际值
     */
    private BigDecimal scheduleRealityPrice;
    /**
     * 文件id
     */
    private Integer fileId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String fileUrl;
    /**
     * 年
     */
    private String yearBase;
    /**
     * 月
     */
    private String monthBase;
    /**
     * 日
     */
    private String dayBase;
}
