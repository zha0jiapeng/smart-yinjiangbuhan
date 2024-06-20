package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_project_personnel")
public class SysProjectPersonnel extends BaseDomain {
    /**
     * 人员名称
     */
    private String personnelName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证号
     */
    private String cardNumber;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 人员类型
     */
    private Integer personnelType;
}
