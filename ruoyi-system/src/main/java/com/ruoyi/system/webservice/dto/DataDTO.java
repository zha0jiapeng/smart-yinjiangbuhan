package com.ruoyi.system.webservice.dto;

import lombok.Data;

/**
 * @author lpeng
 * @since 2023/12/1 14:17
 */
@Data
public class DataDTO {

    /**
     * 1盾头里程（切口）
     */
    private String dtlc;
    /**
     * 2前盾后里程（前盾）
     */
    private String qdhlc;
    /**
     * 3撑紧盾前里程（盾尾）
     */
    private String cjdqlc;
    /**
     * 18盾头高程
     */
    private String dtgc;
    /**
     * 12前盾水平趋向
     */
    private String qdspqx;
    /**
     * 13前盾竖直趋向
     */
    private String qdszqx;
    /**
     * 14撑靴盾水平趋向
     */
    private String cxdspqx;
    /**
     * 15撑靴盾竖直趋向
     */
    private String cxdszqx;
    /**
     * 4盾头水平偏差
     */
    private String dtsppc;
    /**
     * 5盾头垂直偏差
     */
    private String dtczpc;
    /**
     * 6前盾后水平偏差
     */
    private String qhdsppc;
    /**
     * 7前盾后垂直偏差
     */
    private String qhdczpc;
    /**
     * 8撑紧盾前水平偏差
     */
    private String cjdqsppc;
    /**
     * 9撑紧盾前垂直偏差
     */
    private String cjdqczpc;
    /**
     * 10撑紧盾后水平偏差
     */
    private String cjdhsppc;
    /**
     * 11撑紧盾后垂直偏差
     */
    private String cjdhczpc;

    /**
     * 90.推进工作状态
     */
    private String tjgzzt;

    /**
     * 0.环片计数器
     */
    private String hpjsq;

    /**
     * 93.推进速度平均值
     */
    private String tjsdpjz;

    /**
     * 91.总推进力
     */
    private String ztjl;

    /**
     * 99.主推进泵电机运行
     */
    private String ztjbdjyx;

    /**
     * 100.主推进泵出口压力
     */
    private String ztjbckyl;

    /**
     * 111.辅助推进泵电机运行
     */
    private String fztjbdjyx;

    /**
     * 112.辅助推进泵出口压力
     */
    private String fztjbckyl;

    /**
     * 92.贯入度
     */
    private String grd;

    /**
     * 94.推进速度设置
     */
    private String tjsdsz;

    /**
     * 129.拼装机角度
     */
    private String pzjjd;

    /**
     * 123.撑靴泵出口压力
     */
    private String cxbckyl;

    /**
     * 124.撑靴无杆枪压力
     */
    private String cxwgqyl;

    /**
     * 125.撑靴有杆枪压力
     */
    private String cxygqyl;

    /**
     * 126.上撑靴油缸行程检测
     */
    private String scxygxcjc;

    /**
     * 127.下撑靴油缸行程检测
     */
    private String xcxygxcjc;

    /**
     * 151.顶部伸缩盾伸缩油缸行程
     */
    private String dbssdssygxc151;

    /**
     * 152.底部伸缩盾伸缩油缸行程
     */
    private String dbssdssygxc152;

    /**
     * 62.刀盘工作状态
     */
    private String dpgzzt;

    /**
     * 63.刀盘速度设置
     */
    private String dpsdsz;

    /**
     * 64.刀盘速度
     */
    private String dpsd;

    /**
     * 65.刀盘转矩
     */
    private String dpzj;

    /**
     * 19.前盾体倾角
     */
    private String qdtqj;

    /**
     * 20.前盾体翻转角
     */
    private String qdtfzj;

    /**
     * 47.液压油箱温度
     */
    private String yyyxwd;

    /**
     * 49.齿轮油箱温度
     */
    private String clyxwd;

    /**
     * 255.注浆液压油箱温度
     */
    private String zjyyyxwd;

    /**
     * 167 甲烷浓度
     */
    private String jwc;

    /**
     * 169 二氧化碳浓度
     */
    private String erwc;

    /**
     * 166 一氧化碳浓度
     */
    private String ywc;

    /**
     * 165 氧气浓度
     */
    private String yqc;

    /**
     * 168 硫化氢浓度
     */
    private String shqc;


}
