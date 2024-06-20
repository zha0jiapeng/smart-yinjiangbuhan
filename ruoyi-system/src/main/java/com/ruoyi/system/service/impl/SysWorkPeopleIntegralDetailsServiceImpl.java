package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysWorkPeopleIntegralDetails;
import com.ruoyi.system.mapper.SysWorkPeopleIntegralDetailsMapper;
import com.ruoyi.system.service.SysWorkPeopleIntegralDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysWorkPeopleIntegralDetailsServiceImpl extends ServiceImpl<SysWorkPeopleIntegralDetailsMapper, SysWorkPeopleIntegralDetails>
        implements SysWorkPeopleIntegralDetailsService {
    @Resource
    private SysWorkPeopleIntegralDetailsMapper sysWorkPeopleIntegralDetailsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysWorkPeopleIntegralDetails insert(SysWorkPeopleIntegralDetails sysWorkPeopleIntegralDetails) {
        sysWorkPeopleIntegralDetailsMapper.insert(sysWorkPeopleIntegralDetails);
        return sysWorkPeopleIntegralDetails;
    }

    @Override
    public List<SysWorkPeopleIntegralDetails> queryByPid(Integer pid) {
        QueryWrapper<SysWorkPeopleIntegralDetails> qw = new QueryWrapper<>();
        if (pid != null) {
            qw.eq("pid", pid);
        }
        qw.orderByDesc("id");
        List<SysWorkPeopleIntegralDetails> list = sysWorkPeopleIntegralDetailsMapper.selectList(qw);
        return list;
    }

}
