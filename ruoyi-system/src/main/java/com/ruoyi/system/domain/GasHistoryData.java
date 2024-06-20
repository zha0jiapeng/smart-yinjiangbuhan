package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.basic.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(description = "history_data")
@Data
@TableName("history_data")
public class GasHistoryData extends BaseDomain {
    /**
     * 项目名称
     */
    @TableField(value = "设备名称")
    private String deviceName;
    /**
     * 生成时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "时间")
    private Date createDate;
    /**
     * 湿度
     */
    @TableField(value = "湿度")
    private String temperature;
    /**
     * 温度
     */
    @TableField(value = "温度")
    private String humidity;
    /**
     * 噪音
     */
    @TableField(value = "噪音")
    private String noise;
    /**
     * pm2.5
     */
//    @TableField(value = "PM2.5")
//    private String pmTwoFive;
    /**
     * pm10
     */
    @TableField(value = "PM10")
    private String pmTen;
    /**
     * tsp
     */
    @TableField(value = "TSP")
    private String tsp;
    /**
     * 大气压
     */
    @TableField(value = "大气压")
    private String pressure;
    /**
     * 风速
     */
    /**
     * 风向
     */
    @TableField(value = "风向")
    private String windDirection;
    /**
     * 氧气
     */
    @TableField(value = "氧气")
    private String oxygen;
    /**
     * 甲烷
     */
    @TableField(value = "甲烷")
    private String methane;
    /**
     * 一氧化碳
     */
    @TableField(value = "一氧化碳")
    private String oneCarbonization;
    /**
     * 硫化氧
     */
    @TableField(value = "硫化氢")
    private String sulfuretedHydrogen;
    /**
     * 二氧化碳
     */
    @TableField(value = "二氧化碳")
    private String twoCarbonization;
    /**
     * 二氧化硫
     */
    @TableField(value = "二氧化硫")
    private String twoSulfurization;
    /**
     * 氧气
     */
    /**
     * 氨气
     */

    /**
     * 光照
     */
    @TableField(value = "光照")
    private String cdDrive;
    /**
     * 负压
     */
    @TableField(value = "负压")
    private String negativePressure;
}
