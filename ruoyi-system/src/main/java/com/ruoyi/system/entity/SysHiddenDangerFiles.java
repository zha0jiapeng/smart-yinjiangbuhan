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
 * 隐患登记_附件表
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_hidden_danger_files")
@ApiModel(value="SysHiddenDangerFiles对象", description="隐患登记_附件表")
public class SysHiddenDangerFiles extends Model<SysHiddenDangerFiles> {
    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属文件包ID")
    @TableField("file_id")
    private Integer fileId;

    @ApiModelProperty(value = "附件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "附件路径")
    @TableField("file_url")
    private String fileUrl;

    @ApiModelProperty(value = "附件大小")
    @TableField("file_size")
    private Double fileSize;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
