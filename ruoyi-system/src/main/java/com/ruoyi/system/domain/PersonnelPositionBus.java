package com.ruoyi.system.domain;

import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonnelPositionBus extends BaseDomain {

    private Integer id;

    private String imei;

    private String name;

    private Integer deptId;

    private String deptName;

    private String outsiders;

    private String bloodType;

    private BigDecimal integral;

    private Integer flat;

    private Integer floor;

    private String x;

    private String y;

    private String uploadTime;

    private String phone;

    private String address;

}
