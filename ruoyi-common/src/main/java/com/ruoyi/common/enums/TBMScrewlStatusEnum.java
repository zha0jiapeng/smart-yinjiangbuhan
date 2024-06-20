package com.ruoyi.common.enums;

/**
 * TBM 螺旋机状态 暂时没有用到
 */
public enum TBMScrewlStatusEnum {
    STOP("0","停止"),
    PREPARE("1","准备"),
    POWERREADY("2","动力就绪"),
    FORWARDLOWSPEED("3","正转低速"),
    FORWARDHIGHSPEED("4","正转高速"),
    REVERSELOWSPEED("5","反转低速"),
    REVERSEHIGHSPEED("6","反转高速");

    TBMScrewlStatusEnum(String value, String name) {
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

    public static String getNameByValue(String value ){
        for(TBMScrewlStatusEnum tbmScrewlStatusEnum : TBMScrewlStatusEnum.values()){
            if(tbmScrewlStatusEnum.equals(value)){
                return tbmScrewlStatusEnum.name;
            }
        }
        return "其它";
    }
}
