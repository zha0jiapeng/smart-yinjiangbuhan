package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_hidden_danger_log")
public class SysHiddenDangerLog extends BaseDomain {

    private Integer pid;

    private String fileUrl;

    private String changeDescribe;

}
