package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("progress_bing_relation")
public class ProgressBingRelation extends BaseDomain{

    @ApiModelProperty("项目ID")
    private String projectId;

    @ApiModelProperty("三维模型ID")
    private String modelId;

}
