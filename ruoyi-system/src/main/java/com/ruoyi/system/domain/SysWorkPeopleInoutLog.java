package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 人员出入记录
 * @author hu_p
 * @date 2024/6/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_work_people_inout_log")
public class SysWorkPeopleInoutLog extends BaseDomain {

    private Integer id;
    private String sn;
    @TableField(exist = false)
    private String sns;
    @TableField(exist = false)
    private String workType;
    private Integer mode;
    private String logTime;
    private String idCard;
    private String phone;
    private String photoBase64;
    private String photoUrl;
    private String name;

}
