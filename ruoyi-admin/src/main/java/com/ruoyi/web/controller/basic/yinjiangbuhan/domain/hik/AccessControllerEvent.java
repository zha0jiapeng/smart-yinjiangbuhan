package com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik;

import lombok.Data;

@Data
public class AccessControllerEvent {
    private String deviceName;
    private Integer majorEventType;
    private Integer subEventType;
    private String name;
    private Integer cardReaderKind;
    private Integer cardReaderNo;
    private Integer verifyNo;
    private String employeeNoString;
    private Integer serialNo;
    private String userType;
    private String currentVerifyMode;
    private Integer frontSerialNo;
    private String attendanceStatus;
    private String label;
    private Integer statusValue;
    private String mask;
    private String helmet;
    private Integer picturesNumber;
    private Boolean purePwdVerifyEnable;
    private FaceRect faceRect;

    // Getters and Setters
    // toString method, if necessary
}