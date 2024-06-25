package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人员出入记录
 * @author hu_p
 * @date 2024/6/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_work_people_inout_log")
public class SysWorkPeopleInoutLog extends BaseDomain {

    /**
     * 日志业务主键
     */
    private String inoutId;
    private Integer sysWorkPeopleId;
    private String sn;
    private Integer mode;
    private String logTime;
    private String idCard;
    private String phone;
    private String name;

}
