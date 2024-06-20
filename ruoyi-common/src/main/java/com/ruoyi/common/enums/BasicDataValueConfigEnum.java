package com.ruoyi.common.enums;

public enum BasicDataValueConfigEnum {

    monitor_type_device_distribution("monitor_type_device_distribution"),
    monitor_status("monitor_status"),
    monitor_list("monitor_list"),
    project_overview("project_overview"),
    plan_human("plan_human"),
    progress_overview_details("progress_overview_details"),
    init_plan_construction_human("init_plan_construction_human"),

    init_plan_construction_human_week("init_plan_construction_human_week"),

    construction_curve_picture("construction_curve_picture"),

    project_overview_config("project_overview_config"),
    ;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    BasicDataValueConfigEnum() {
    }

    BasicDataValueConfigEnum(String code) {
        this.code = code;
    }
}
