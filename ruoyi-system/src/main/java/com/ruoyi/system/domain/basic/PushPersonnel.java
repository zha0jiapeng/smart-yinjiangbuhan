package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.io.Serializable;

@Data
public class PushPersonnel implements Serializable {

    private String merchantCode;

    private String token;

    private String data;

    private String key;

}
