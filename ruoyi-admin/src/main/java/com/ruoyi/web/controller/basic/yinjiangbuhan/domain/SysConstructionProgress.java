package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 施工对象 sys_construction_progress
 * 
 * @author mashir0
 * @date 2024-08-21
 */
@Data
public class SysConstructionProgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 主隧洞总长度 */
    @Excel(name = "主隧洞总长度")
    private Long mainHoleLength;

    /** 支隧洞总长度 */
    @Excel(name = "支隧洞总长度")
    private Long sideHoleLength;

    /** 总工期（天） */
    @Excel(name = "总工期")
    private Long totalDuration;

    /** 已施工（天） */
    @TableField(exist = false)
    private Long constructedDuration;

    /** 开始施工日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始施工日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdDate;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifyBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyDate;

    /** 逻辑删除标识 0删除 1正常 */
    @Excel(name = "逻辑删除标识 0删除 1正常")
    private Long yn;

}
