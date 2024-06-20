package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("secure_study_file_config")
public class SecureStudyFileConfig extends BaseDomain {

    private String fileId;

    private String fileName;

    private Integer fileType;

    private String viewDuration;

    private String paperId;

    private String paperName;

    private String enclosureName;

    private String zrCode;

}
