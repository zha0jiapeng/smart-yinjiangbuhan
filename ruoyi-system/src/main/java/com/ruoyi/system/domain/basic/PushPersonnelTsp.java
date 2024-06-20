package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushPersonnelTsp implements Serializable {

    private String merchantCode;

    private String token;

    private List<IotTsp> data;

    private String key;

}
