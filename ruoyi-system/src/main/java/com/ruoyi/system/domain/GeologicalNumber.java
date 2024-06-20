package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("geological_number")
public class GeologicalNumber extends BaseDomain {

    /**
     * 桩号
     */
    private String stakeMark;

    /**
     * 桩号详情
     */
    private String stakeMarkInfo;

}
