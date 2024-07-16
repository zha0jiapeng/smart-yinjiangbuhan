package com.ruoyi.web.controller.basic.yinjiangbuhan.bean;

import java.util.ArrayList;

/**
 * 门禁点反控制
 */


public class DoControlRequest {
    /**
     * 门禁索引
     */
    private ArrayList<String> doorIndexCodes;

    /**
     * 控制类型
     */
    private Integer controlType;

    public ArrayList<String> getDoorIndexCodes() {
        return doorIndexCodes;
    }

    public void setDoorIndexCodes(ArrayList<String> doorIndexCodes) {
        this.doorIndexCodes = doorIndexCodes;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }
}
