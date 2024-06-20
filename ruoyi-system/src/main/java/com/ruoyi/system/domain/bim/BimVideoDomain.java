package com.ruoyi.system.domain.bim;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

@Data
public class BimVideoDomain {

    private VideoTypeAnalysis videoTypeAnalysis;

    private VideoStatusAnalysis videoStatusAnalysis;

    private List<VideoListAnalysis> videoListAnalyses;

    //监控类型设备分布
    @Data
    public static class VideoTypeAnalysis {
        /**
         * 枪型摄像头
         */
        private Integer spearVideo;
        /**
         * 全景摄像头
         */
        private Integer panoramaVideo;
        /**
         * 半球摄像头
         */
        private Integer hemisphereVideo;
    }

    //监控摄像头状态
    @Data
    public static class VideoStatusAnalysis {
        /**
         * 总数
         */
        private Integer sum;
        /**
         * 在线
         */
        private Integer onLine;
        /**
         * 离线
         */
        private Integer offline;
        /**
         * 故障
         */
        private Integer fault;

        private List<VideoStatusListAnalysis> videoStatusListAnalyses;
    }

    @Data
    public static class VideoStatusListAnalysis {
        /**
         * 序号
         */
        private Integer number;
        /**
         * 摄像头ID
         */
        private String videoId;
        /**
         * 摄像头名称
         */
        private String videoName;
        /**
         * 摄像头状态
         */
        private String videoStatus;
    }

    @Data
    public static class VideoListAnalysis {
        /**
         * id
         */
        private Integer id;
        /**
         * 父节点
         */
        private Integer pid;
        /**
         * 是否是节点
         */
        private boolean flag;
        /**
         * 名称
         */
        private String name;
        /**
         * 地址
         */
        private String url;
    }

}
