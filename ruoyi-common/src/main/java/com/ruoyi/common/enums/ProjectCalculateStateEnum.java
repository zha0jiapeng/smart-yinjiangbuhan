package com.ruoyi.common.enums;

public enum ProjectCalculateStateEnum {

    // 投资进度状态
    PREDICT_PAY_STATE(1, "预计投资"),
    INVESTED_PAY_STATE(2, "已投资"),
    NORMAL_PAY_STATE(3, "正常"),
    NO_PAY_STATE(4, "未支付"),
    OVERFULFIL_PAY_STATE(5, "超额"),

    // 产值进度状态
    DAY_NUMBER_STATE(6, "今日完成数"),
    MONTH_NUMBER_STATE(7, "本月完成数"),

    // 投资进度
    INVEST_PROGRESS(8, "投资进度"),
    // 产值进度
    CAPITAL_PROGRESS(9, "产值进度"),
    // 施工进度曲线
    WORK_PROGRESS(10, "施工进度曲线"),

    // 数值
    STR_NUMBER(11, "0");

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

    ProjectCalculateStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
