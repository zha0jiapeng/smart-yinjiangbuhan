package com.ruoyi.system.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 测点预警统计信息
 */
@Data
public class PointAlarmStatisticalInfo {

    //点组编码
    private  String pgroup_id;
    //点组名称
    private  String point_name;
    //测量时间
    private  String plan_time;
    //总点数
    private int all_point_count;
    //黄色预警数
    private int yellow_point_count;
    //红色预警数
    private int red_point_count;
    //预警信息列表
    private List<PointAlarmInfo> alarm_info;
    //正常的信息列表
    private List<PointDataVO> point_data;

}
