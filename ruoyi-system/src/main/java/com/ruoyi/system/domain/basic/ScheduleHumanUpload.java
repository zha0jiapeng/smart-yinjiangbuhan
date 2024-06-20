package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("schedule_human_upload")
public class ScheduleHumanUpload extends BaseDomain {

    /**
     * 上报日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;
    /**
     * 周计划进度
     */
    private String weekPlanSchedule;
    /**
     * 周实际进度
     */
    private String weekActualSchedule;
    /**
     * 计划累计进度
     */
    private String planAccumulateAccumulate;
    /**
     * 实际累计进度
     */
    private String realityAccumulateAccumulate;
    /**
     * 周计划人力
     */
    private String weekPlanHuman;
    /**
     * 周实际人力
     */
    private String weekRealityHuman;

    @TableField(exist = false)
    private Integer orderByAscOrDesc;
}
