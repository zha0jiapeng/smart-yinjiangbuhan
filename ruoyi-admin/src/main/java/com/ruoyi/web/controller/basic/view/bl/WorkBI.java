package com.ruoyi.web.controller.basic.view.bl;

import lombok.Data;

import java.util.List;

@Data
public class WorkBI {


    private DayBase dayBase;

    private WeekBase weekBase;

    @Data
    public static class WeekBase {

        private List<String> key;

        private List<String> plan;

        private List<String> value;

    }

    @Data
    public static class DayBase {

        private List<String> key;

        private List<String> value;

    }

}
