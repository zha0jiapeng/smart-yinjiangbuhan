package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimControlDomain {

    private List<Details> array;

    @Data
    public static class Details {

        private String id;

        private String url;

        private String x;

        private String y;

        private String z;

    }

}
