package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimEquipmentDomain {

    private List<Details> array;

    @Data
    public static class Details {

        private String equipmentname;

        private String information;

        private String state;

        private String x;

        private String y;

        private String z;

    }

}
