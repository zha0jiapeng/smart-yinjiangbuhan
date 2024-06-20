package com.ruoyi.system.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.SysQuestionDescribe;
import com.ruoyi.system.mapper.SysQuestionDescribeMapper;
import com.ruoyi.system.service.SysQuestionDescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日常安全巡查 问题项描述 服务实现类
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
@Service
public class SysQuestionDescribeServiceImpl extends ServiceImpl<SysQuestionDescribeMapper, SysQuestionDescribe> implements SysQuestionDescribeService {

    @Autowired
    private SysQuestionDescribeMapper sysQuestionDescribeMapper;
    @Override
    public void create(SysQuestionDescribe sysQuestionDescribe) {
        sysQuestionDescribeMapper.create(sysQuestionDescribe);
    }
}
