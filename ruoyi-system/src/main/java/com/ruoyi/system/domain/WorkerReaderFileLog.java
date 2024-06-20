package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("secure_study_file_config")
public class WorkerReaderFileLog extends BaseDomain {

    private Integer configId;

    private String name;

    private String phone;

    private String idCard;

    private Integer personnelId;

    private Date readerStartDate;

    private Date readerEndDate;

    private BigDecimal score;

}
