package com.ruoyi.common.enums;

public enum PresenceDetailsTypeEnum {

    TEMPORARY(1,"临时访客"),
    SCENE(2,"现场工人");

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

    PresenceDetailsTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
