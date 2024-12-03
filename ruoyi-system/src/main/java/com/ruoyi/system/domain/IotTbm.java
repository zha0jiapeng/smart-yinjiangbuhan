package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("Iot_tbm")
public class IotTbm extends BaseDomain {

    private String deviceCode;

    private String cutterTorque;

    private String cutterRev;

    private String totalThrustOne;

    private String totalThrustTwo;

    private Integer status;

}
