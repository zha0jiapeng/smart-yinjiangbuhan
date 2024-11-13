package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.mapper.SysWorkPeopleMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysWorkPeopleServiceImpl extends ServiceImpl<SysWorkPeopleMapper, SysWorkPeople> implements SysWorkPeopleService {
    @Resource
    private SysWorkPeopleMapper sysWorkPeopleMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public SysWorkPeople insert(SysWorkPeople sysWorkPeople) {
        sysWorkPeopleMapper.insert(sysWorkPeople);
        return sysWorkPeople;
    }

    @Override
    public void updWorkPeopleDeparture(SysWorkPeople sysWorkPeople) {

    }

    @Override
    public SysWorkPeople getOneByIdCardInRedis(String idCard) {
        String idCardRegex = "(^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}(\\d|X|x)$)|(^[1-9]\\d{7}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}$)";
        boolean isValid = ReUtil.isMatch(idCardRegex, idCard);
        if(!isValid) return null;
        HashOperations hashOperations = redisTemplate.opsForHash();
        SysWorkPeople workPeople = (SysWorkPeople) hashOperations.get("workPeople", idCard);
        if(workPeople==null){
            workPeople = getOne(new LambdaQueryWrapper<SysWorkPeople>().eq(SysWorkPeople::getIdCard, idCard));
            hashOperations.put("workPeople", idCard,workPeople);
        }
        return workPeople;
    }
}
