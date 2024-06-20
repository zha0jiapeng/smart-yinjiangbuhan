package com.ruoyi.common.enums;

/**
 * TBM 推进状态
 */
public enum TBMPropelStatusEnum {
    STOP("0","停止"),
    PREPARE("1","准备"),
    POWERREADY("2","动力就绪"),
    PROPEL("3","推进"),
    ASSEMBLYMODE("4","拼装模式"),
    ASSEMBLYCOMPLETED("5","拼装结束");

    TBMPropelStatusEnum(String value, String name) {
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
        for(TBMPropelStatusEnum tbmPropelStatusEnum :TBMPropelStatusEnum.values()){
            if(tbmPropelStatusEnum.value.equals(value)){
                return tbmPropelStatusEnum.name;
            }
        }
        return "其它";
    }
}
