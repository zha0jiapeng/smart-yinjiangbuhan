package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimThingsDomain {

    private List<Station> array;

    @Data
    public static class Station{

        private String id;

        private String typename;

        private String value;

        private String state;

        private String x;

        private String y;

        private String z;

    }

}
