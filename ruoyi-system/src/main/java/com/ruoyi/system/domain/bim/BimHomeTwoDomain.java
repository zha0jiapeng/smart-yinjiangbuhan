package com.ruoyi.system.domain.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.ProjectOverview;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BimHomeTwoDomain {

    private ProjectOverview projectOverview;

    private List<BasicPost> basicPostList;

    private ConstructionCount constructionCount;

    private List<Call> callList;


    @Data
    public static class BasicPost {
        /**
         * 岗位
         */
        private String post;
        /**
         * 负责人
         */
        private String head;
        /**
         * 手机号
         */
        private String phone;
    }

    @Data
    public static class ConstructionCount {
        /**
         * 管片生产数
         */
        private Integer segment;
        /**
         * 投入人数
         */
        private Integer personnel;
        /**
         * 基础数据
         */
        private String jsonValue;

    }

    @Data
    public static class Call {
        /**
         * 序号
         */
        private Integer id;
        /**
         * 类型
         */
        private String noticeType;
        /**
         * 报警内容
         */
        private String content;
        /**
         * 时间
         */
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createDate;
        /**
         * 状态
         */
        private String status;
    }

}

