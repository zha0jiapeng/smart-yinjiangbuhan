package com.ruoyi.common.enums;

/**
 * 数据源
 *
 * @author ruoyi
 */
public enum InOrOutEnum {
    OUT(2, "出库"),
    IN(1, "入库");

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

    InOrOutEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
