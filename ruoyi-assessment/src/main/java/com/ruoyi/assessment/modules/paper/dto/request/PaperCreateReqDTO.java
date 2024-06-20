package com.ruoyi.assessment.modules.paper.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.assessment.core.api.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bool
 */
@Data
@ApiModel(value="试卷创建请求类", description="试卷创建请求类")
public class PaperCreateReqDTO extends BaseDTO {

    @JsonIgnore
    private String userId;

    private String card;

    @ApiModelProperty(value = "考试ID", required=true)
    private String examId;

}
