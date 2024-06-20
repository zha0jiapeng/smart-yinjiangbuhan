package com.ruoyi.common.enums;

public enum ProjectCalculateEnum {

    CONSTRUCTION_PROGRESS_CURVE(1, "施工进度曲线"),
    PROJECT_COMPLETION_PROGRESS(2, "施工人力曲线"),
    PROJECT_COMPLETION_PROGRESS_NUMBER(3, "管片分析"),
    INVESTMENT_PROGRESS(4, "投资进度"),
    ENGINEERING_PROGRESS(5, "投资分析饼状图"),
    PROGRESS_OUTPUT_VALUE(6, "产值进度");

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

    ProjectCalculateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
