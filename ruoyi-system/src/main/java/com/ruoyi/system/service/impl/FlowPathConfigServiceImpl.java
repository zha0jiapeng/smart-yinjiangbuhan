package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.FlowPathConfig;
import com.ruoyi.system.mapper.FlowPathConfigMapper;
import com.ruoyi.system.service.FlowPathConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FlowPathConfigServiceImpl extends ServiceImpl<FlowPathConfigMapper, FlowPathConfig> implements FlowPathConfigService {


    @Resource
    private FlowPathConfigMapper flowPathConfigMapper;

    @Override
    public FlowPathConfig insert(FlowPathConfig flowPathConfig) {
        flowPathConfigMapper.insert(flowPathConfig);
        return flowPathConfig;
    }

}
