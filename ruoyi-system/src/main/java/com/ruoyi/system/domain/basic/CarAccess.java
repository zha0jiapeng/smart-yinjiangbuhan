package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("car_access")
public class CarAccess extends BaseDomain{
    /**
     * id
     */
    private Integer id;
    /**
     * 车辆编码
     */
    @Excel(name = "车辆编码")
    private String carCode;
    /**
     * 车辆进入时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Excel(name = "车辆入场时间")
    private Date carInDate;
    /**
     * 车辆出去时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Excel(name = "车辆出场时间")
    private Date carOutDate;

    private String photoBase64;

    private String photoUrl;

    private String sn;
}
