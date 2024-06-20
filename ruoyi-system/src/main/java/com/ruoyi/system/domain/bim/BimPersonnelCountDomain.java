package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimPersonnelCountDomain {

    private PersonnelStatistics personnelStatistics;

    private TrendHour trendHour;

    private WorkType workType;

    private SubpackageUnit subpackageUnit;

    private SevenAnalysis sevenAnalysis;

    private ConstructionHuman constructionHuman;

    @Data
    public static class PersonnelStatistics {
        private String presentPeople;
        private String constructors;
        private String owner;
        private String management;
        private String supervisor;
        private String constructionPersonnel;
        private String visitor;
    }


    @Data
    public static class TrendHour {
        //管理人员
        private List<String> manage;

        //监理人员
        private List<String> supervisor;

        //施工人员
        private List<String> construction;

        //访客人员
        private List<String> visitor;

    }

    @Data
    public static class WorkType {

        //工种(实时)
        private List<String> type;

        //数量(实时)
        private List<String> number;

        //工种(今日)
        private List<String> typeCase;

        //数量(今日)
        private List<String> numberCase;

    }

    @Data
    public static class SubpackageUnit {

        //分包单位(实时)
        private List<String> unit;

        //数量(实时)
        private List<String> number;

        //分包单位(今日)
        private List<String> unitCase;

        //数量(今日)
        private List<String> numberCase;

    }

    @Data
    public static class SevenAnalysis {
        //下标
        private List<String> subscript;
        //数量
        private List<String> number;
    }

    @Data
    public static class ConstructionHuman {
        //下标
        private List<String> subscript;

        //计划
        private List<String> plan;

        //实际
        private List<String> reality;

    }

}
