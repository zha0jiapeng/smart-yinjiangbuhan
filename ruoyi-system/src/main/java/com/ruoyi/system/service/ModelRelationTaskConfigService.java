package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ModelRelationTaskConfig;

import java.util.List;

public interface ModelRelationTaskConfigService extends IService<ModelRelationTaskConfig> {

    List<ModelRelationTaskConfig> queryModelRelationTaskConfigByType(Integer code);

}
