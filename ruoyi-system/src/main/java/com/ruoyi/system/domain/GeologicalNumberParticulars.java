package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description="地质桩号表")
@Data
@TableName("geological_number_particulars")
public class GeologicalNumberParticulars extends BaseDomain {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("桩号id")
    private Integer pid;

    @ApiModelProperty("桩号")
    private String stakeMark;

    @ApiModelProperty("桩号进度")
    private String progress;

    @ApiModelProperty("施工日期")
    private String constructionDate;

    @ApiModelProperty("预计施工日期")
    private String predictDate;

    @ApiModelProperty("项目勘探地质")
    private String explorationGeology;

    @ApiModelProperty("超前预报地质")
    private String predictGeology;

    @ApiModelProperty("'实际地质'")
    private String practicalGeology;

    @ApiModelProperty("'当前施工桩'")
    private String currentConstructionPile;

    @ApiModelProperty("'地质信息'")
    private String geologicalInformation;

}
