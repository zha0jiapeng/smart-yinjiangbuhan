package com.ruoyi.common.enums;

import lombok.Data;
public enum YnEnum {
    N(0,"禁用"),
    Y(1,"正常"),;

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

    YnEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
