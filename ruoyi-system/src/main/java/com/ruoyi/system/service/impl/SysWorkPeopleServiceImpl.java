package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.mapper.SysWorkPeopleMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysWorkPeopleServiceImpl extends ServiceImpl<SysWorkPeopleMapper, SysWorkPeople> implements SysWorkPeopleService {
    @Resource
    private SysWorkPeopleMapper sysWorkPeopleMapper;

    @Override
    public SysWorkPeople insert(SysWorkPeople sysWorkPeople) {
        sysWorkPeopleMapper.insert(sysWorkPeople);
        return sysWorkPeople;
    }

    @Override
    public void updWorkPeopleDeparture(SysWorkPeople sysWorkPeople) {

    }
}
