package com.ruoyi.common.enums;

public enum VerifyEnum {
    ID_NULL(10000, "主键不能为空"),
    CHECK_WORK_NAME(10001, "工人名称不可为空"),
    CHECK_STAFF_ID(10002, "员工id不能为空"),
    FILE_PACKAGE_ID(10003, "文件夹ID不可为空"),

    THREE_DIMENSIONAL_MODEL_IDS_NULL(10004, "三维模型IDS不能为空"),

    PHONE_NULL(1005,"手机号不能为空"),

    PHONE_NULL_OR_ID(1006,"手机号或者ID不能为空");
    ;

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    VerifyEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
