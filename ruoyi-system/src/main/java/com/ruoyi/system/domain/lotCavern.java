package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("lot_cavern")
public class lotCavern extends BaseDomain {

    private String coTwo;

    private String pmTwoFive;

    private String temperature;

    private String humidity;

}
