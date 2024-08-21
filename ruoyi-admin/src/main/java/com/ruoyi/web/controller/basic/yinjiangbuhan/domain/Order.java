package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import lombok.Data;

/**
 * Created by yangqinghua on 2022/2/26.
 */
@Data
public class Order {

    /**
     * 设备Id
     */
    private Long deviceId;

    /**
     * 报警点位
     */
    private Long alarmPoint;

    /**
     * 报警类型Id
     */
    private Long alarmTypeId;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警时间，格式：yyyy
     */
    private String alarmTime;

    /**
     * 报警抓拍
     */
    private String alarmCapture;

    /**
     * 报警内容
     */
    private String alarmContent;

    /**
     * 备注
     */
    private String remark;
}
