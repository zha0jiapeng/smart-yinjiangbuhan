package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.VideoConfig;

import java.util.List;

public interface VideoConfigService extends IService<VideoConfig> {

    List<VideoConfig> queryList();

}
