package com.ruoyi.system.domain.basic;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMppFile {
    /**
     * 文档名称
     */
    private String fileName;
    /**
     * 文档目录
     */
    private String fileCatalogue;
    /**
     * 关联文件
     */
    private String relationFile;
    /**
     * 关联人
     */
    private String relationPeople;
    /**
     * 关联日期
     */
    private Date relationDate;
}
