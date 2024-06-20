package com.ruoyi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 三维_进度统计数据(ProjectCalculate)实体类
 *
 * @since 2023-06-06 10:01:25
 */
@Data
public class ProjectCalculate implements Serializable {
    private static final long serialVersionUID = 668509348205314679L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 模块名称
     */
    private String modelKey;
    /**
     * 模块名称
     */
    private String modelValue;
    /**
     * 审核状态 1:待审核 2:审核中 3:审核通过
     */
    private Long checkState;
    /**
     * 审核人
     */
    private String checkBy;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;

    private String checkDateStart;

    private String checkDateEnd;

    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    /**
     * 更新人
     */
    private String modifyBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    /**
     * 逻辑删除标识 0:删除 1:正常
     */
    private Integer yn;
}

