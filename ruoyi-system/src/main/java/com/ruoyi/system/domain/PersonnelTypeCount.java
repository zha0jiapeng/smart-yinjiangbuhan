package com.ruoyi.system.domain;

import lombok.Data;

@Data
public class PersonnelTypeCount {
    /**
     * 总数
     */
    private String presentPeople;
    /**
     * 施工
     */
    private String constructors;
    /**
     * 业主
     */
    private String owner;
    /**
     * 管理
     */
    private String management;
    /**
     * 监理
     */
    private String supervisor;
    /**
     * 班组
     */
    private String constructionPersonnel;
    /**
     * 访客
     */
    private String visitor;
}
