package com.ruoyi.system.pojo.vo;

import lombok.Data;

@Data
public class PointDataVO {

    //点编码
    private  String PointId;
    //点名称
    private  String PointName;
    //点组编码
    private  String PgroupId;
    //点组名称
    private  String PgroupName;
    //设站编码
    private  String SetstationId;
    //设站名称
    private  String SetstationName;
    //终端MAC
    private  String MacAddress;
    //终端名称
    private  String ModuleName;
    //测量时间
    private  String PlanTime;
    //测量次数
    private  String Cycle;
    //温度
    private  String Temperature;
    //湿度
    private  String Humidity;
    //气压
    private  String Pressure;
    //水平角
    private  String Horizontal_ang;
    //垂直角
    private  String Verticall_ang;
    //距离
    private  String Distance;
    //北坐标
    private  String Pos_X;
    //东坐标
    private  String Pos_Y;
    //高程
    private  String Pos_Z;
    //北坐标本次变化量
    private  String Current_Variation_X;
    //北坐标累计变化量
    private  String Total_Variation_X;
    //北坐标本次变化速率
    private  String Current_ChangeRate_X;
    //东坐标本次变化量
    private  String Current_Variation_Y;
    //东坐标累计变化量
    private  String Total_Variation_Y;
    //东坐标本次变化速率
    private  String Current_ChangeRate_Y;
    //高程累计变化量
    private  String Total_Variation_H;
    //高程本次变化量
    private  String Current_Variation_H;
    //高程本次变化速率
    private  String Current_ChangeRate_H;
    //△S本次变化量
    private  String Current_Variation_S;
    //△S累计变化量
    private  String Total_Variation_S;
    //△S本次变化速率
    private  String Current_ChangeRate_S;
    //△M本次变化量(平行于参考方向)
    private  String Current_Variation_M;
    //△M累计变化量(平行于参考方向)
    private  String Total_Variation_M;
    //△M本次变化速率(平行于参考方向)
    private  String Current_ChangeRate_M;
    //△V本次变化量(垂直于参考方向)
    private  String Current_Variation_V;
    //△V累计变化量(垂直于参考方向)
    private  String Total_Variation_V;
    //△V本次变化速率(垂直于参考方向)
    private  String Current_ChangeRate_V;
    //垂直净距
    private  String Vertical_Distance = "47.0";
    //该参数主要用作UE中判断是否能查出数据，默认为true
    private String newData = "true";
}
