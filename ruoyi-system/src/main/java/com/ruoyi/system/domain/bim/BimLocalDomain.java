package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimLocalDomain {


    private List<Coordinate> array;

    @Data
    public static class Coordinate{

        private String id;

        private String name;

        private String department;

        private String telephone;

        private String x;

        private String y;

        private String z;

    }


}
