package com.ruoyi.system.domain.basic;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_worker_app_login")
public class Worker extends Model<Worker> {
    /**
     * 主键
     */
    private Integer id;
    /**
    /**
     * 工人名称
     */
    private String workName;
    /**
     * 工人手机号
     */
    private String workPhone;
    /**
     * 工人身份证号
     */
    private String cardCode;
    /**
     * 试卷id集合
     */
    @TableField(exist = false)
    private String volumeIds;
    /**
     * 创建人id
     */
    private Integer createdById;
    /**
     * 创建人名称
     */
    private String createdByName;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人id
     */
    private Integer modifyById;
    /**
     * 更新人名称
     */
    private String modifyByName;
    /**
     * 更新时间
     */
    private Timestamp modifyDate;
    /**
     * 逻辑删除标识 1正常 0 删除
     */
    private Integer yn;
}
