package com.ruoyi.system.pojo.dto;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QualityTaskDTO  extends BaseAudit {

    // 任务名称
    private String taskName;

    //任务内容
    private String taskDescribe;

    //进度ID
    //private Integer projId;

    //进度任务名称
    private String projName;

    // 质量任务来源
    private String taskSource;

    // 处理任务状态(0-已处理 1-待处理)
    private String taskState;

    /** 用户昵称 */
    private String nickName;

    /** 部门名称 */
    private String deptName;

    /** 用户名称 */
    private String userName;







}

