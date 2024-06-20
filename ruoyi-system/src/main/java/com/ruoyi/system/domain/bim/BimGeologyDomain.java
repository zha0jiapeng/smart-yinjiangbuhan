package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimGeologyDomain {

    private List<Details> array;

    @Data
    public static class Details{
        private String id;
        private ProspectInfo prospectInfo;
        private ForecastInfo forecastInfo;
        private RealityInfo realityInfo;
    }

    @Data
    public static class ProspectInfo {
        private String name;
        private String state;
        private String info;
    }

    @Data
    public static class ForecastInfo {
        private String name;
        private String state;
        private String info;
    }

    @Data
    public static class RealityInfo {
        private String name;
        private String state;
        private String info;
    }

}
