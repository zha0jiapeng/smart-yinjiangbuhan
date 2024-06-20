package com.ruoyi.system.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.entity.SysQuestionDescribe;

/**
 * <p>
 * 日常安全巡查 问题项描述 服务类
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
public interface SysQuestionDescribeService extends IService<SysQuestionDescribe> {

    void create(SysQuestionDescribe sysQuestionDescribe);
}
