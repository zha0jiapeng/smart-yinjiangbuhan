package com.ruoyi.system.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 测点预警信息
 */
@Data
public class PointAlarmInfo {

    //数据编码
    private String alarm_info_id;
    //预警类型 1黄色，2红色
    private int alarm_type;
    //点编码
    private  String point_id;
    //点名称
    private  String pgroup_name;
    //点组编码
    private  String pgroup_id;
    //点组名称
    private  String point_name;
    //测量时间
    private  Date  data_date;
    //北坐标本次变化量
    private  String current_variation_x;
    //北坐标累计变化量
    private  String total_variation_x;
    //北坐标本次变化速率
    private  String current_changerate_x;
    //东坐标本次变化量
    private  String current_variation_y;
    //东坐标累计变化量
    private  String total_variation_y;
    //东坐标本次变化速率
    private  String current_changerate_y;
    //高程累计变化量
    private  String total_variation_h;
    //高程本次变化量
    private  String current_variation_h;
    //高程本次变化速率
    private  String current_changerate_h;
    //△S本次变化量
    private  String current_variation_s;
    //△S累计变化量
    private  String total_variation_s;
    //△S本次变化速率
    private  String current_changerate_s;
    //△M本次变化量(平行于参考方向)
    private  String current_variation_m;
    //△M累计变化量(平行于参考方向)
    private  String total_variation_m;
    //△M本次变化速率(平行于参考方向)
    private  String current_changerate_m;
    //△V本次变化量(垂直于参考方向)
    private  String current_variation_v;
    //△V累计变化量(垂直于参考方向)
    private  String total_variation_v;
    //△V本次变化速率(垂直于参考方向)
    private  String current_changerate_v;
    //水平位移S本次黄色预警值
    private String current_variation_yellow_s;
    //水平位移S本次红色预警值
    private String current_variation_red_s;
    //水平位移S累计黄色预警值
    private String total_variation_yellow_s;
    //水平位移S累计红色预警值
    private String total_variation_red_s;
    //水平位移S本次速率黄色预警值
    private String current_rate_yellow_s;
    //水平位移S本次速率红色预警值
    private String current_rate_red_s;
    //M本次黄色预警值(平行于参考方向)
    private String current_variation_yellow_m;
    //M本次红色预警值
    private String current_variation_red_m;
    //M累计黄色预警值
    private String total_variation_yellow_m;
    //M累计红色预警值
    private String total_variation_red_m;
    //M本次速率黄色预警值
    private String current_rate_yellow_m;
    //M本次速率红色预警值
    private String current_rate_red_m;
    //V本次黄色预警值(垂直于参考方向)
    private String current_variation_yellow_v;
    //V本次红色预警值
    private String current_variation_red_v;
    //V累计黄色预警值
    private String total_variation_yellow_v;
    //V累计红色预警值
    private String total_variation_red_v;
    //V本次速率黄色预警值
    private String current_rate_yellow_v;
    //V本次速率红色预警值
    private String current_rate_red_v;

}
