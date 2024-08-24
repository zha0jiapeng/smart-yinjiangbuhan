package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 报警类型表对象 alarm_type
 *
 * @author liang
 * @date 2024-08-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("alarm_type")
@ApiModel(value = "AlarmType", description = "报警类型表对象 alarm_type")
public class AlarmType implements Serializable {

    private static final long serialVersionUID=1L;


    /**  */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long id;

    /** 报警类型 */
    @ApiModelProperty(value = "报警类型")
    private String typeName;

    /** 广播语音id */
    @ApiModelProperty(value = "广播语音id")
    private Long broadcastVoiceId;

    /** 广播设备id */
    @ApiModelProperty(value = "广播设备id")
    private String broadcastDeviceId;

    /** 逻辑删除标识 0删除 1正常 */
    @ApiModelProperty(value = "逻辑删除标识 0删除 1正常")
    private Long yn;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

}