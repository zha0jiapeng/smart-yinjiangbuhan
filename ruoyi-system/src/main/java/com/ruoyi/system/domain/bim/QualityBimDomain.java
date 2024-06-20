package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class QualityBimDomain {

    //质量问题
    private QualityProblem daizhenggaizhiliangwenti;

    //质量问题分布
    private QualityDistribution zhiliangwentifenbu;

    //质量问题整改用时
    private QualityRectification zhiliangwentizhenggaiyongshi;

    //质量问题等级
    private QualityGrade zhiliangwentidengjifenbu;

    //质量问题统计
    private QualityCount zhiliangwentitongji;

    //质量问题趋势
    private QualityTrend zhiliangwentifashengqushi;

    //负责人
    private Head fuzeren;

    @Data
    public static class QualityTrend {
        private List<String> quannian;
    }

    @Data
    public static class QualityCount {
        private List<String> quannian;
    }

    @Data
    public static class QualityGrade {
        private String zhongda;
        private String zhongdeng;
        private String putong;
    }

    @Data
    public static class QualityRectification {
        private List<String> quannian;
    }

    @Data
    public static class QualityDistribution {
        private String moban;
        private String gangjin;
        private String hunningtu;
        private String qiti;

        private String suidao;
        private String dianqi;
        private String tongfeng;
    }

    @Data
    public static class QualityProblem {
        private Integer id;
        private String state;

        private Integer status;
        private String time;
        private String info;
        private String x;
        private String y;
        private String z;
        private String personincharge;
        private String model;
        private String place;
        private String region;
        private String problemdescription;
        private String reportPerson;
        private String reporTime;
        private String rectifier;
        private String rectifierTime;
        private String beforeRectificationImage;
        private String afterRectificationImage;
        private List<QualityProblem> array;
    }

    @Data
    public static class Head {
        private List<String> name;
        private List<String> value;
    }

}
