package com.ruoyi.common.enums;

/**
 * TBM 刀盘状态
 */
public enum TBMCutterHeadStatusEnum {
    STOP("0","停止"),
    PREPARE("1","准备"),
    READY("2","就绪"),
    ESCAPEREVERSAL("3","脱困反转"),
    ESCAPEFORWARD("4","脱困正转"),
    FOREWARD1("5","正转"),
    FOREWARD2("6","正转"),
    REVERSAL1("7","反转"),
    REVERSAL2("8","反转"),
    STOPPINGPROCESS("9","停止过程");

    TBMCutterHeadStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByValue(String value){
        for(TBMCutterHeadStatusEnum tbmCutterHeadStatusEnum : TBMCutterHeadStatusEnum.values()){
            if(tbmCutterHeadStatusEnum.value.equals(value)){
                return tbmCutterHeadStatusEnum.name;
            }
        }
        return "其它";
    }
}
