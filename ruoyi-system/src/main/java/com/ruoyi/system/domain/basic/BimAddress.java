package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bim_address")
public class BimAddress extends BaseDomain{

    private String pileNumber;

    private String projProspectQuality;

    private String projProspectQualityDetailed;

    private String speedProspectQuality;

    private String speedProspectQualityDetailed;

    private String realityProspectQuality;

    private String realityProspectQualityDetailed;

}
