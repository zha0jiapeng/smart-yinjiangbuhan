package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IotTspCopy extends BaseDomain implements Serializable {

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

    @TableField(exist = false)
    private List<IotTspCopyDetails> iotTspCopyDetails;

    @Data
    public static class IotTspCopyDetails {
        private String name;

        private List<String> key;

        private List<String> value;
    }

}
