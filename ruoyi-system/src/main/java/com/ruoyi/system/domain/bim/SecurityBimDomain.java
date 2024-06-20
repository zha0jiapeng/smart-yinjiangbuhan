package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class SecurityBimDomain {

    //安全应急
    private SecurityArrangementModel benyueanquanyingjijianbao;

    //风险等级统计
    private RiskGradeCountModel fengxiandengjitongji;

    //区域风险统计
    private AreaGradeCountModel quyufengxiantongji;

    //带整改隐患信息
    private AngerInfoModel daizhenggaiyinhuanxinxi;

    //隐患排除统计
    private AngerPaichaCountModel yinhuanpaichutongji;

    //风险隐患新增趋势
    private RiskAngerModel fengxianyinhuanxinzhengqushi;

    //消防站
    private FirehouseModel xiaofangzhan;

    //应急事故柜
    private EmergencyAccidentModel yingjishigugui;

    //人员统计
    private PersonnelStatistics personnelStatistics;

    /**
     * 安全问题统计
     */
    private SecureProblemClass secureProblemClassCount;

    @Data
    public static class SecureProblemClass {
        /**
         * 安全问题名称
         */
        private List<String> secureProblemName;
        /**
         * 安全问题数量
         */
        private List<Integer> secureProblemNumber;
    }

    /**
     * 安全应急接口 security arrangement
     */
    @Data
    public static class SecurityArrangementModel {
        private String xiancunyinhuan;
        private String xiancunfengxian;
        private String zhenggaizhong;
        private String benyuexinzengfengxian;
        private String benyuexinzhengyinhuan;
        private String fengxianyujing;
        private String benyuezhenggai;
        private String guankong;
    }


    /**
     * 风险等级统计 risk grade
     */
    @Data
    public static class RiskGradeCountModel {
        private String teda;
        private String zhongda;
        private String jiaoda;
        private String yiban;
    }


    /**
     * 区域风险统计 area grade
     */
    @Data
    public static class AreaGradeCountModel {
        private String jhedian;
        private String name;
        private String teda;
        private String zhongda;
        private String jiaoda;
        private String yiban;
        private List<AreaGradeCountModel> array;
    }


    /**
     * 带整改隐患信息 danger Info
     */
    @Data
    public static class AngerInfoModel {
        private Long id;
        private String state;
        private Integer status;
        private String info;
        private String time;
        private String region;
        private String problemdescription;
        private String reportPerson;
        private String reporTime;
        private String rectifier;
        private String rectifierTime;
        private String beforeRectificationImage;
        private String afterRectificationImage;
        private List<AngerInfoModel> array;
    }


    /**
     * 隐患排除统计 danger Info
     */
    @Data
    public static class AngerPaichaCountModel {
        private String zhongda;
        private String yanzhong;
        private String yiban;
        private String zhengchang;
    }


    /**
     * 风险隐患新增趋势 danger Info
     */
    @Data
    public static class RiskAngerModel {
        private String fengxian;
        private String yinhuan;
        private List<RiskAngerModel> quannian;
    }


    /**
     * 消防站 firehouse
     */
    @Data
    public static class FirehouseModel {
        private String fangzhididian;
        private String wuzi;
        private String x;
        private String y;
        private List<FirehouseModel> array;
    }


    /**
     * 应急事故柜
     */
    @Data
    public static class EmergencyAccidentModel {
        private String fangzhididian;
        private String wuzi;
        private String x;
        private String y;
        private List<EmergencyAccidentModel> array;
    }


    @Data
    public static class PersonnelStatistics {
        private String presentPeople;
        private String constructors;
        private String management;
        private String supervisor;
        private String constructionPersonnel;
        private String visitor;
    }

}
