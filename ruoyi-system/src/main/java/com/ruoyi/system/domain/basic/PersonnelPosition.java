package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("personnel_position")
public class PersonnelPosition extends BaseDomain implements Serializable {

    private String imei;

    private String name;

    private String outsiders;

    private Long uploadTime;

    private Integer flat;

    private Integer floor;

    private Double x;

    private Double y;

    private String address;
    /**
     * 血型
     */
    private String bloodType;
    /**
     * 积分
     */
    private String integral;

    @TableField(exist = false)
    private String phone;

}
