package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;


@Setter
@Getter
@Accessors(chain = true)
//@TableName(value = "pms_project_planning")
public class PmsProjectPlanning {
    @JsonCreator
    public PmsProjectPlanning() {
    }

    //主键
    @TableId(value = "ID_", type = IdType.INPUT)
    private String id;

    //创建人名称
    @TableField(value = "CREATE_BY_NAME", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String createByName;

    //前置任务
    @TableField(value = "FRONT_TASK_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String frontTask;

    //流程实例ID
    @TableField(value = "INST_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String instId;

    //状态
    @TableField(value = "INST_STATUS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String instStatus;

    //父ID
    @TableField(value = "PARENT_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String parentId;

    //项目名称
    @TableField(value = "PROJECT_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String projectId;

    //项目名称
    @TableField(value = "PROJECT_NAME_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String projectName;

    //外键
    @TableField(value = "REF_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String refId;

    //实际完成日期
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //   @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "TASK_ACTUAL_FINISH_DATE_", jdbcType = JdbcType.TIMESTAMP, updateStrategy = FieldStrategy.IGNORED)
    private java.util.Date taskActualFinishDate;

    //实际开始日期
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "TASK_ACTUAL_START_DATE_", jdbcType = JdbcType.TIMESTAMP, updateStrategy = FieldStrategy.IGNORED)
    private java.util.Date taskActualStartDate;

    //实际阶段工作量
    @TableField(value = "TASK_ACTUAL_WORK_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskActualWork;

    //实际工作量单位
    @TableField(value = "TASK_ACTUAL_WORK_UNITS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskActualWorkUnits;

    //阶段计划工期
    @TableField(value = "TASK_DURATION_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskDuration;

    //计划工期单位
    @TableField(value = "TASK_DURATION_UNITS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskDurationUnits;

    //计划完成日期
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //  @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "TASK_FINISH_DATE_", jdbcType = JdbcType.TIMESTAMP, updateStrategy = FieldStrategy.IGNORED)
    private java.util.Date taskFinishDate;

    //任务ID
    @TableField(value = "TASK_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskId;

    //任务项负责人
    @TableField(value = "TASK_LEADER_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskLeader;

    //模块名称
    @TableField(value = "TASK_MODEL_NAME_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskModelName;

    //任务项名称
    @TableField(value = "TASK_NAME_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskName;

    //阶段负责人
    @TableField(value = "TASK_OPERATOR_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskOperator;

    //任务级别
    @TableField(value = "TASK_OUTLINE_LEVEL_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskOutlineLevel;

    //父任务ID
    @TableField(value = "TASK_PARENT_DEF_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskParentDefId;

    //阶段完成百分比
    @TableField(value = "TASK_PERCENTAGE_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskPercentage;

    //阶段名称
    @TableField(value = "TASK_PHASE_NAME_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskPhaseName;

    //任务流
    @TableField(value = "TASK_PREDECESSORS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskPredecessors;

    //备注
    @TableField(value = "TASK_REMARK_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskRemark;

    //计划开始日期
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //   @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd HH:mm:ss")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "TASK_START_DATE_", jdbcType = JdbcType.TIMESTAMP, updateStrategy = FieldStrategy.IGNORED)
    private java.util.Date taskStartDate;

    //任务唯一ID
    @TableField(value = "TASK_UNIQUE_ID_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskUniqueId;

    //WBS码
    @TableField(value = "TASK_WBS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskWbs;

    //阶段计划工作量
    @TableField(value = "TASK_WORK_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskWork;

    //阶段状态
    @TableField(value = "TASK_WORK_STATUS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskWorkStatus;

    //计划工作量单位
    @TableField(value = "TASK_WORK_UNITS_", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String taskWorkUnits;

    //更新人名称
    @TableField(value = "UPDATE_BY_NAME", jdbcType = JdbcType.VARCHAR, updateStrategy = FieldStrategy.IGNORED)
    private String updateByName;

    //版本号
    @TableField(value = "UPDATE_VERSION_", jdbcType = JdbcType.NUMERIC, updateStrategy = FieldStrategy.IGNORED)
    private Long updateVersion;

//    @Override
//    public String getPkId() {
//        return id;
//    }
//
//    @Override
//    public void setPkId(String pkId) {
//        this.id=pkId;
//    }

}
