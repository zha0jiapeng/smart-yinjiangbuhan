package com.ruoyi.common.enums;

public enum ModelTaskEnum {

    SEGMENT(1, "管片"),
    PIPELINE(2, "管线"),
    GROUND(3, "地面");

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

    ModelTaskEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
