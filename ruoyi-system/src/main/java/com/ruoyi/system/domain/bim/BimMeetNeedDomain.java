package com.ruoyi.system.domain.bim;

import lombok.Data;

import java.util.List;

@Data
public class BimMeetNeedDomain {

    private List<Malfunction> array;

    @Data
    public static class Malfunction {

        private String id;

        private String place;

        private String goods;

        private String x;

        private String y;

        private String z;

    }

}
