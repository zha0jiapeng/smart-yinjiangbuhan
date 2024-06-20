package com.ruoyi.system.domain.bim;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "施工人力历史")
@Data
@TableName("construction_people_history")
public class ConstructionPeopleHistory extends BaseDomain {

    private Date doDate;

    private Double planPeople;

    private Double realityPeople;

}
