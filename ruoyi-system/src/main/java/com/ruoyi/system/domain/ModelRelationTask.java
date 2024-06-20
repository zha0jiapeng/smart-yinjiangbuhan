package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel(description = "model_relation_task")
@Data
@TableName("model_relation_task")
public class ModelRelationTask extends BaseDomain {
    /**
     * 模型IDS
     */
    @TableField(exist = false)
    private List<String> modelIds;
    /**
     * 任务ID
     */
    private String modelId;
    /**
     * 任务ID
     */
    private String taskId;
}
