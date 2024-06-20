package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("current_construction")
@SuppressWarnings("all")
public class CurrentConstruction extends BaseDomain {
    /**
     * 关联id
     */
    private String relationId;
    /**
     * 当前进度   %
     */
    private String currentSchedule;
    /**
     * 当前施工   140m
     */
    private String currentConstruction;
    /**
     * 当前施工量
     */
    private String currentQuantity;
    /**
     * 锚杆          根
     */
    private String bolt;
    /**
     * 水泥          吨
     */
    private String cement;
    /**
     * 预制管片   片
     */
    private String segment;

    private String saveArray;

    @TableField(exist = false)
    private List<Map> info;
}
