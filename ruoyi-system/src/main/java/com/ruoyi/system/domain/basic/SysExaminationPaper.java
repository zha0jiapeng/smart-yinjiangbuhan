package com.ruoyi.system.domain.basic;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_examination_paper")
public class SysExaminationPaper extends Model<SysExaminationPaper> {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 试卷id集合
     */
    private String volumeIds;
    /**
     * 考卷二维码id
     */
    private String enclosureId;
    /**
     * 考卷二维码名称
     */
    private String enclosureName;
    /**
     * 考卷二维码url
     */
    private String enclosureUrl;
    /**
     * 考卷描述
     */
    private String describeValue;
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
