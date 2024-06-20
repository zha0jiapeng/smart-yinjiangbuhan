package com.ruoyi.system.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TbmWorkProgressLog {
    private int id;
    //盾头里程
    private BigDecimal dtlc;
    //环片计数器
    private Integer hpjsq;
    //数据获取时间
    private Date pullTime;
    //推进工作状态
    private String tjgzzt;
}
