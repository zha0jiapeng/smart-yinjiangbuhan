package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 站内信 sys_user_notice
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user_notice")
public class SysUserNotice extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String noticeTitle;

    /**
     * 类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    /**
     * 消息推送给的用户ID
     */
    private String userId;

    /**
     * 模块名称
     */
    private String modelName;

    /**
     * 读取用户ID
     */
    private String readUserId;

    /**
     * 阅读时间
     */
    private String readTime;

    /**
     * 备注
     */
    private String remark;

    private Integer portSort;
}
