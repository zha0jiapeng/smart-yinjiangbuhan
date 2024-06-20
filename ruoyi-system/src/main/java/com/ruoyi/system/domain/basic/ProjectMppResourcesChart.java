package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppResourcesChart {

    @Data
    public static class OutputValue {
        /**
         * 月份
         */
        private Date month;
        /**
         * 单位1
         */
        private String unitOne;
        /**
         * 单位2
         */
        private String unitTwo;
    }
}
