package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.basic.SysContract;
import com.ruoyi.system.mapper.SysContractMapper;
import com.ruoyi.system.service.ISysContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysContractServiceImpl extends ServiceImpl<SysContractMapper, SysContract> implements ISysContractService {

    @Autowired
    private SysContractMapper sysContractMapper;

    @Override
    public SysContract insert(SysContract sysContract) {
        sysContractMapper.insert(sysContract);
        return sysContract;
    }
}
