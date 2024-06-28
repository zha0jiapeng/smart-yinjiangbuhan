package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import lombok.Data;

import java.util.List;

@Data
public class SwzkParam {
    private String deviceType;
    private String deviceName;
    private String SN;
    private String dataType;
    private String bidCode;
    private String workAreaCode;
    private String workSurface;
    private String managementDept;
    private List<Object> values;

}
