package com.ruoyi.system.pojo.vo;

import lombok.Data;

@Data
public class PointAlarmStatisticalVO {

    /**
     * 横坐标轴的KEY
     */
    private String key;
    /**
     * 红色预警数量
     */
    private int redVal;

    /**
     * 黄色预警数量
     */
    private int yellowVal;

}
