package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.QualityAutomaticConfig;
import com.ruoyi.system.mapper.QualityAutomaticConfigMapper;
import com.ruoyi.system.service.QualityAutomaticConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class QualityAutomaticConfigServiceImpl extends ServiceImpl<QualityAutomaticConfigMapper, QualityAutomaticConfig> implements QualityAutomaticConfigService {

    @Resource
    private QualityAutomaticConfigMapper qualityAutomaticConfigMapper;

    public void check(QualityAutomaticConfig qualityAutomaticConfig, String param) {

        QueryWrapper<QualityAutomaticConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("quality_config_details",param);
        queryWrapper.eq("quality_config_type", 2);
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        QualityAutomaticConfig automaticConfig = qualityAutomaticConfigMapper.selectOne(queryWrapper);

        if(null == automaticConfig){
            queryWrapper.clear();
            queryWrapper.eq("quality_config_details",qualityAutomaticConfig.getQualityConfigDetails());
            queryWrapper.eq("quality_config_type", 2);
            queryWrapper.eq("yn", YnEnum.Y.getCode());
            automaticConfig = qualityAutomaticConfigMapper.selectOne(queryWrapper);

            QualityAutomaticConfig qualityAutomaticConfigAdd = new QualityAutomaticConfig();
            qualityAutomaticConfigAdd.setPid(automaticConfig.getId());
            qualityAutomaticConfigAdd.setQualityConfigType("2");
            qualityAutomaticConfigAdd.setQualityConfigDetails(param);

            qualityAutomaticConfigAdd.setCreatedDate(new Date());
            qualityAutomaticConfigAdd.setModifyBy(SecurityUtils.getLoginUser().getUsername());
            qualityAutomaticConfigAdd.setModifyDate(new Date());
            qualityAutomaticConfigAdd.setYn(YnEnum.Y.getCode());

            qualityAutomaticConfigMapper.insert(qualityAutomaticConfigAdd);

        }
    }

}
