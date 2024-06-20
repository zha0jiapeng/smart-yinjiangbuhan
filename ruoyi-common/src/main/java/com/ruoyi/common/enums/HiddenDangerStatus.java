package com.ruoyi.common.enums;

public enum HiddenDangerStatus {

    UNDER(0, "待整改"),
    AUDIT(1, "整改中"),
    OK(2, "已完成"),
    ;
    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    HiddenDangerStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
