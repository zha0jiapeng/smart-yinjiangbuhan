package com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik;

import lombok.Data;

@Data
public class AccessControllerEventWrapper {
    private String ipAddress;
    private String ipv6Address;
    private Integer portNo;
    private String protocol;
    private String macAddress;
    private Integer channelID;
    private String dateTime;
    private Integer activePostCount;
    private String eventType;
    private String eventState;
    private String eventDescription;
    private String deviceID;
    private AccessControllerEvent accessControllerEvent;

    // Getters and Setters
    // toString method, if necessary
}