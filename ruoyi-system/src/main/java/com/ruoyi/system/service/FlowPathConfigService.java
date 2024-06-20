package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.FlowPathConfig;

public interface FlowPathConfigService extends IService<FlowPathConfig> {

    FlowPathConfig insert(FlowPathConfig flowPathConfig);

}
