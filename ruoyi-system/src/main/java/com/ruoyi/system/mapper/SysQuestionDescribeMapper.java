package com.ruoyi.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.SysQuestionDescribe;

/**
 * <p>
 * 日常安全巡查 问题项描述 Mapper 接口
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
public interface SysQuestionDescribeMapper extends BaseMapper<SysQuestionDescribe> {

    void create(SysQuestionDescribe sysQuestionDescribe);
}
