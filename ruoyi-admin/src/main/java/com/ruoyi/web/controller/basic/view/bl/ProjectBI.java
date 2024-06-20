package com.ruoyi.web.controller.basic.view.bl;

import lombok.Data;

import java.util.List;

@Data
public class ProjectBI {
    /**
     * 工期情况
     */
    private Overview overview;
    /**
     * 施工曲线图
     */
    private TargetAdvance targetAdvance;
    /**
     * 施工人力
     */
    private ManpowerResource manpowerResource;
    /**
     * 任务详情
     */
    private List<TaskDetails> taskDetails;

    @Data
    public static class Overview {
        /**
         * 实际工期（/天）
         */
        private Integer durationDay;
        /**
         * 正常天数
         */
        private Integer normalDay;
        /**
         * 倒计时
         */
        private Integer countdownDay;
        /**
         * 计划开始时间
         */
        private String planStartDate;
        /**
         * 计划结束时间
         */
        private String planEndDate;
        /**
         * 实际开始时间
         */
        private String realityStartDate;
        /**
         * 实际进度
         */
        private String realityEndDate;
        /**
         * 比例
         */
        private String proportion;
    }

    /**
     * 施工进度
     */
    @Data
    public static class TargetAdvance {
        private List<String> jihuashijian;
        private List<String> wanchengjindu;
        private List<String> planJindu;
    }

    @Data
    public static class ManpowerResource {
        private List<ManpowerResourceVo> renliziyuantouru;
    }

    @Data
    public static class ManpowerResourceVo {
        private String shijian;
        private String jihuatouru;
        private String shijitouru;
    }

    @Data
    public static class TaskDetails {
        /**
         * 任务名称
         */
        private String taskName;
        /**
         * 计划开始时间
         */
        private String planStartDate;
        /**
         * 计划结束时间
         */
        private String planEndDate;
        /**
         * 实际开始时间
         */
        private String realityStartDate;
        /**
         * 实际结束时间
         */
        private String realityEndDate;
        /**
         * 比例
         */
        private String proportion;
        /**
         * 计划时间
         */
        private List<String> planDate;
        /**
         * 实际时间
         */
        private List<String> realityDate;
        /**
         * 延期
         */
        private List<String> extension;
    }

}
