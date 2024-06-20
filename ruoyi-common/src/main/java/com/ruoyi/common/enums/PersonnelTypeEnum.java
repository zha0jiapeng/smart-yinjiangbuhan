package com.ruoyi.common.enums;

public enum PersonnelTypeEnum {

    OWNER(1, "业主代建"),
    SUPERVISOR(2, "监理人员"),
    MANAGE(3, "管理人员"),
    TEAMS(4, "班组人员"),
    TEMPORARY(5, "临时访客");

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

    PersonnelTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
