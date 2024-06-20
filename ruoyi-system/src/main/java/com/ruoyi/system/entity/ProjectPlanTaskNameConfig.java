package com.ruoyi.system.entity;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * 计划-进度任务分类配置表(ProjectPlanTaskNameConfig)实体类
 */
@Data
public class ProjectPlanTaskNameConfig implements Serializable {
    private static final long serialVersionUID = 871375024420121218L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 计划任务类型
     */
    private Long planTaskType;
    /**
     * 计划任务名称
     */
    private String planTaskName;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人
     */
    private String modifyBy;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 逻辑删除表示 1:正常 0:删除
     */
    private Integer yn;

}

