package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("quality_automatic_config")
public class QualityAutomaticConfig extends BaseDomain {

    private Integer pid;

    private String qualityConfigType;

    private String qualityConfigDetails;

}
