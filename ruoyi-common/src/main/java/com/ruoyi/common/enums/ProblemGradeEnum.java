package com.ruoyi.common.enums;

public enum ProblemGradeEnum {
    Z(1, "重大"),
    T(2, "特大"),
    Y(3, "一般"),
    ZC(4, "正常"),
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

    ProblemGradeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
