package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.VideoConfig;
import com.ruoyi.system.mapper.VideoConfigMapper;
import com.ruoyi.system.service.VideoConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoConfigServiceImpl  extends ServiceImpl<VideoConfigMapper, VideoConfig>  implements VideoConfigService{

    @Override
    public List<VideoConfig> queryList() {
        return this.list();
    }

}
