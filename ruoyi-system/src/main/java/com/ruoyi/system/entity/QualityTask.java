package com.ruoyi.system.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 质量任务(QualityTask)实体类
 *
 * @author makejava
 * @since 2023-01-19 15:27:19
 */
@Data
public class QualityTask implements Serializable {
    private static final long serialVersionUID = 708770771327074975L;

    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "附件表ID")
    @TableField("file_id")
    private String fileId;
    /**
     * 进度ID
     */
    @TableField("proj_id")
    private Integer projId;
    /**
     * 任务名称
     */
    @TableField("task_name")
    @Excel(name = "任务名称",sort = 1)
    private String taskName;
    /**
     * 任务内容
     */
    @TableField("task_describe")
    @Excel(name = "任务内容",sort = 2)
    private String taskDescribe;
    /**
     * 进度任务名称
     */
    @TableField("proj_name")
    @Excel(name = "任务进度",sort = 3)
    private String projName;

    @TableField("task_source")
    @Excel(name = "任务来源",sort = 4)
    private String taskSource;
    /**
     * 任务状态(0-已处理 1-待处理)
     */
    @TableField("task_state")
    private Integer taskState;

    @TableField(exist = false)
    //@Excel(name = "任务状态",sort = 5)
    private String taskStateBase;

    /**
     * 执行人名称
     */
    @TableField("executor_user_names")
    @Excel(name = "用户名称",sort = 5)
    private String executorUserNames;
    /**
     * 模板ID
     */
    @TableField("template_id")
    private Integer templateId;
    /**
     * 模板名称
     */
    @TableField("template_name")
    private String templateName;
    /**
     * 模板表单内容
     */
    @TableField("template_form_data")
    private String templateFormData;
    /**
     * 发起人名称
     */
    @TableField("create_user_name")
    private String createUserName;
    /**
     * 发起人ID
     */
    @TableField("create_user_id")
    private Integer createUserId;
    /**
     * 执行人IDS
     */
    @TableField("executor_user_ids")
    private String executorUserIds;
    /**
     * 检查结果(0-合格 1-不合格)
     */
    @TableField("check_result")
    private Integer checkResult;
    /**
     * 验收文件路径
     */
    @TableField("check_quality_file_url")
    private String checkQualityFileUrl;
    /**
     * 验收文件名称
     */
    @TableField("check_quality_file_name")
    private String checkQualityFileName;
    /**
     * 验收图片
     */
    @TableField("check_quality_image_url")
    private String checkQualityImageUrl;
    /**
     * 模型ID
     */
    @TableField("model_id")
    private Integer modelId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 表单样式
     */
    @TableField("template_form_style")
    private String templateFormStyle;
    /**
     * 质检问题ID
     */
    @TableField("task_problem_Id")
    private String taskProblemId;
    /**
     * 质检问题ID
     */
    @TableField("task_number_two")
    private String taskNumberTwo;

    @Excel(name = "部门名称",sort = 6)
    @TableField("dept_name")
    private String deptName;

    @Excel(name = "用户昵称",sort = 7)
    private String nickName;

    @TableField(exist = false)
    private String typeOne;


    /**
     * 审核状态(0-未处理 1-审核中 2已通过)
     */
    @TableField("check_status")
    private Integer checkStatus;


    @TableField(exist = false)
    private List<QualityTaskFiles> qualityTaskFiles;

    public List<QualityTaskFiles> getQualityTaskFiles() {
        return qualityTaskFiles;
    }

    public void setQualityTaskFiles(List<QualityTaskFiles> qualityTaskFiles) {
        this.qualityTaskFiles = qualityTaskFiles;
    }

}

