package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_work_people_integral_details")
public class SysWorkPeopleIntegralDetails extends BaseDomain {

    private Integer pid;

    @TableField(exist = false)
    private Integer type;

    private BigDecimal integral;

    private String integralDetails;


}
