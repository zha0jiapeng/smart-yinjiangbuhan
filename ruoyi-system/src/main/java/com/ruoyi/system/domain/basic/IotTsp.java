package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("lot_tsp")
public class IotTsp extends BaseDomain implements Serializable {

    private String devId;

    private String pmTwoFive;

    private String pmTen;

    private String tsp;

    private String temperature;

    private String humidity;

    private String windSpeed;

    private String windDirection;

    private String noise;

    private String pressure;

    private String noTwo;

    private String soTwo;

    private String co;

    private String three;

    private String voc;

}
