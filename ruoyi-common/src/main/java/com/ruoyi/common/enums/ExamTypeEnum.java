package com.ruoyi.common.enums;

public enum ExamTypeEnum {

    STAFF(1,"员工"),
    PLATFORM(0,"平台");

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

    ExamTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
