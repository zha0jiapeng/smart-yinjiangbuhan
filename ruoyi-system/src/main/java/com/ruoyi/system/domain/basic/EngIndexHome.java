package com.ruoyi.system.domain.basic;

import lombok.Data;

@Data
public class EngIndexHome {
    /**
     * 标题1
     */
    private String titleOne;
    /**
     * 已完成
     */
    private Integer majorOne;
    /**
     * 已拖延
     */
    private Integer moreOne;
    /**
     * 进行中
     */
    private Integer commonlyOne;
    /**
     * 未完成
     */
    private Integer lowOne;

    /**
     * 标题2
     */
    private String titleTwo;
    /**
     * 总工期
     */
    private Integer countDuration;
    /**
     * 完成工期
     */
    private Integer completeDuration;

}
