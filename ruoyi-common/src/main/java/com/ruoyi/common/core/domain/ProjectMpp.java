package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("sys_project_progress")
public class ProjectMpp {
    /* 自增主键Id */
    private Integer projId;
    /* 上级Id */
    private Integer parentId;
    /* 结构层级 */
    private Integer level;
    /* 任务名称 */
    private String taskName;
    /* 工期 */
    private String durationDate;
    /* 开始时间 */
    private String startDate;
    /* 结束时间 */
    private String endDate;
    /* 前置任务ID */
    private String frontTask;
    /* 后续任务 */
    private String afterTask;
    /* 宽延时间 */
    private String wideDate;
    /* 责任人 */
    private String personLiableId;
    /* 相关人 */
    private String relatedPersonsId;
    /* 责任人 */
    private String personLiable;
    /* 相关人 */
    private String relatedPersons;
    /* 任务排序 */
    private Integer sortTask;
    /* 备注 */
    private String remark;
    /* 资源名称 */
    private String resourcesName;
    /* 导入时间 */
    private Date importTime;
    /* 批次号 */
    private String batchNum;
    /* 任务类型 */
    private String taskType;

    @TableField(exist = false)
    private Boolean Leaf;
    /**
     * 模型ids
     */
    @TableField(exist = false)
    private String modelIds;
    /**
     * 兼容参数
     */
    @TableField(exist = false)
    private List<Integer> modelIdsParam;
    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<ProjectMpp> children = new ArrayList<ProjectMpp>();
    /**
     * 前后置任务
     */
    @TableField(exist = false)
    private List<ProjectMpp> frontAndAfter = new ArrayList<ProjectMpp>();
    /**
     * 模型信息
     */
    @TableField(exist = false)
    private List<String> models;
}
