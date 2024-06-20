package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppCount {
    /**
     * 总工期
     */
    private String totalDuration;
    /**
     * 已施工天数
     */
    private String complete;
    /**
     * 剩余天数
     */
    private String surplus;
    /**
     * 任务已完成
     */
    private String taskComplete;
    /**
     * 任务未完成
     */
    private String taskNoComplete;
    /**
     * 正常完成
     */
    private String normal;
    /**
     * 提前完成
     */
    private String advance;
    /**
     * 拖延完成
     */
    private String delay;
    /**
     * 进度提前
     */
    private String progressAdvance;
    /**
     * 进度拖延
     */
    private String progressDelay;
    /**
     * 未开始
     */
    private String notStarted;

    @Data
    public static class OutputValue {
        /**
         * 月份
         */
        private Date month;
        /**
         * 单位1
         */
        private String unitOne;
        /**
         * 单位2
         */
        private String unitTwo;
    }

}
