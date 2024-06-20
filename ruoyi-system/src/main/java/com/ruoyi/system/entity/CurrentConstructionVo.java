package com.ruoyi.system.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CurrentConstructionVo {

    private String relationId;

    private List<Map<String, String>> info;

}
