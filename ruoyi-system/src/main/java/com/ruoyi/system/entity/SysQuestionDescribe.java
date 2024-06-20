package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日常安全巡查 问题项描述
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_question_describe")
@ApiModel(value="SysQuestionDescribe对象", description="日常安全巡查 问题项描述")
public class SysQuestionDescribe extends Model<SysQuestionDescribe> {
    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "问题描述")
    @TableField("problem_content")
    private String problemContent;

    @ApiModelProperty(value = "创建人名称")
    @TableField("create_name")
    private String createName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
