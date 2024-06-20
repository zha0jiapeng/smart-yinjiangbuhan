package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "project_mpp_track")
public class ProjectMppTrack {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 任务ID
     */
    @TableField(value = "pid")
    private Integer projId;
    /**
     * 完成进度
     */
    private String completeProgress;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 上报人
     */
    private String escalationPeople;
    /**
     * 上报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date escalationDate;
}
