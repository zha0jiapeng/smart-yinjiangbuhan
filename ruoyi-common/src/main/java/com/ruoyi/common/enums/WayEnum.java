package com.ruoyi.common.enums;

public enum WayEnum {

    IN(1, "进"),
    OUT(2, "出");

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

    WayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
