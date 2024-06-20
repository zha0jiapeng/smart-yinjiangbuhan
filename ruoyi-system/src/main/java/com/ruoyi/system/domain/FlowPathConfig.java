package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flow_path_config")
public class FlowPathConfig extends BaseDomain {

    private String flowPathCode;

    private String flowPathName;

    private Integer flowReviewedById;

    private String flowReviewedBy;

    private String flowReviewedByPhone;

}
