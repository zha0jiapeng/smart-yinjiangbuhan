package com.ruoyi.system.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 三维_累计比例(ProjectRatio)实体类
 * @since 2023-06-07 17:55:24
 */
@Data
public class ProjectRatio implements Serializable {
    private static final long serialVersionUID = -94970036269641118L;

    private Integer id;
    /**
     * 累计完成
     */
    private String cumulativeCompletion;
    /**
     * 日期
     */
    private String cumulativeDate;
    /**
     * 预计投资额
     */
    private BigDecimal predictInvestment;
    /**
     * 已投资
     */
    private BigDecimal alreadyInvestment;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 当前周
     */
    private String weekBase;
    /**
     * 当前周开始时间
     */
    private String weekStartTime;
}

