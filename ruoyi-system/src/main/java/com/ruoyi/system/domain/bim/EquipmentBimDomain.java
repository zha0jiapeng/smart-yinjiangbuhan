package com.ruoyi.system.domain.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EquipmentBimDomain {

    private String normal;

    private String error;

    //故障报修趋势
    private List<String> quannian;

    private Trend guzhangbaoxiuqushi;

    //故障报修情况
    private FaultCondition guzhangbaoxiuqingkuang;

    //报修工单
    private FaultWorkOrder baoxiugongdan;

    private Device shebeidianwei;

    private WmsOrderCount wmsOrderCount;

    @Data
    public static class WmsOrderCount {

        private Integer inOrder;

        private Integer outOrder;

        private Integer transferOrder;

    }

    /**
     * 设备管理接口
     */
    @Data
    public static class FaultWorkOrder {
        private String state;
        private String time;
        private String name;
        private String type;
        private List<FaultWorkOrder> array;
    }

    /**
     * 故障报修情况
     */
    @Data
    public static class FaultCondition {
        private String chaoqi;
        private String baoxiu;
        private String wenxiuzhong;
        private String wancheng;
    }

    @Data
    public static class Trend {
        private List<String> quannian;
    }

    @Data
    public static class Device {

        private String normal;

        private String error;

        private List<DeviceDetails> array;

    }

    @Data
    public static class DeviceDetails {

        private String shebeimingcheng;

        private String shebeizhuangtai;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

    }

}
