package com.ruoyi.system.domain.bim;

import lombok.Data;

@Data
public class BimHomeDomain {
    /**
     * 进度总数
     */
    private String progressCount;
    /**
     * 进度异常
     */
    private String progressAbnormal;
    /**
     * 进度预警
     */
    private String progressWarning;
    /**
     * 进度正常
     */
    private String progressNormal;
    /**
     * 进度完成
     */
    private String progressOk;
    /**
     * 质量问题
     */
    private String qualityCount;
    /**
     * 重大问题
     */
    private String majorCount;
    /**
     * 中等问题
     */
    private String secondaryCount;
    /**
     * 普通问题
     */
    private String ordinaryCount;
    /**
     * 模板工程
     */
    private String templateCount;
    /**
     * 钢筋工程
     */
    private String barCount;
}

