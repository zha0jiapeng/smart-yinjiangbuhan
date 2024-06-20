package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel(description = "model_relation_task_config")
@Data
@TableName("model_relation_task_config")
public class ModelRelationTaskConfig extends BaseDomain {

    /**
     * 模型id
     */
    private String modelId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务类型
     */
    private Integer taskType;

    @TableField(exist = false)
    private List<String> modelIds;

}
