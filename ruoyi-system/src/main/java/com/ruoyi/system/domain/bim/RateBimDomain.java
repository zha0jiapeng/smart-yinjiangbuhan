package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RateBimDomain {

    //计划进度
    private PlanScheduleModel jihuajindu;

    //工程完成情况
    private ProjectCompleteModel gongchengwanchengqingkuang;

    //工程完成进度数
    private ProjectCompleteNumberModel gongchengwanchengjindushu;

    //付款进度
    private PayScheduleModel fukuanjindu;

    //计划工程进度
    private ProjectScheduleModel jihuagongchengjindu;

    //产值进度 production schedule
    private ProductionScheduleModel chanzhijindu;

    //施工进度 target advance
    private TargetAdvance shigongjinduquxian;

    //人力资源投入 manpower resource
    private ManpowerResource renliziyuan;

    //进度展现 progress show
    private ProgressShow jinduzhanxian;

    //管片TBM掘金分析
    private ScheduleTbmSegment scheduleTbmSegment;

    //投资分析
    private List<InvestmentAnalysis> investmentAnalysisList;

    /**
     * 施工进度
     */
    @Data
    public static class TargetAdvance {
        private List<String> jihuashijian;
        private List<String> planJindu;
        private List<String> wanchengjindu;
    }

    // 施工进度_集合
    @Data
    public static class TargetModuleVo {
        private String shijian;
        private Integer gongzuoliang;
    }

    /**
     * 人力资源投入
     */
    @Data
    public static class ManpowerResource {
        private List<ManpowerResourceVo> renliziyuantouru;
    }

    // 人力资源投入_集合
    @Data
    public static class ManpowerResourceVo {
        private String shijian;
        private String jihuatouru;
        private String shijitouru;
    }

    /**
     * 进度展现
     */
    @Data
    public static class ProgressShow {
        private Integer yanqi;
        private Integer yiwancheng;
    }

    /**
     * 计划进度
     */
    @Data
    public static class PlanScheduleModel {
        private String weikaishi;
        private String jinxingzhong;
        private String yanqi;
        private String wancheng;
    }

    /**
     * 工程完成情况
     */
    @Data
    public static class ProjectCompleteModel {
        private String yanqi;
        private String jinxingzhong;
        private String wancheng;
        private String weiwancheng;
    }

    /**
     * 工程完成进度数
     */
    @Data
    public static class ProjectCompleteNumberModel {
        private String benyuewanchengshu;
        private String jinriwanchengshu;
        private List<String> quannian;
    }

    @Data
    public static class ScheduleTbmSegment {
        /**
         * tbm日掘进数
         */
        private BigDecimal tbmProduce;
        /**
         * 管片日生产数
         */
        private BigDecimal segmentProduce;
        /**
         * tbm计划掘金数
         */
        private String planTbmProduce;
        /**
         * tbm实际掘金数
         */
        private String realityTbmProduce;
        /**
         * 管片计划生产数
         */
        private String planSegmentProduce;
        /**
         * 管片实际生产数
         */
        private String realitySegmentProduce;
    }

    @Data
    public static class InvestmentAnalysis {
        /**
         * 分析名称
         */
        private String analysisName;
        /**
         * 颜色
         */
        private String r;

        private String g;

        private String b;

        /**
         * 比例
         */
        private String proportion;
    }

    /**
     * 资产进度
     */
    @Data
    public static class PayScheduleModel {
        private String yujifukuan;
        private String yifukuan;
        private String chaoe;
        private String weizhifu;
        private String zhengchang;
    }

    /**
     * 计划工程进度
     */
    @Data
    public static class ProjectScheduleModel {
        private String jihua;
        private String yiwancheng;
        private List<ProjectScheduleModel> quannian;
    }

    /**
     * 产值进度
     */
    @Data
    public static class ProductionScheduleModel {
        private String benyuanchanzhi;
        private String jinrichanzhi;
        private List<String> yuefen;
        private List<String> shuliang;
    }

}
