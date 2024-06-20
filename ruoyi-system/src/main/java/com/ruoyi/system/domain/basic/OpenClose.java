package com.ruoyi.system.domain.basic;

import lombok.Data;

@Data
public class OpenClose {
    /**
     * 类型: 弹框
     */
    private String type;
    /**
     * 标题
     */
    private String closeOrOpen;
    /**
     * 地址
     */
    private String url;
    /**
     * 唯一标识
     */
    private String uuid;
}
