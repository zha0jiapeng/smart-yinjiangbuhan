package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.CurrentConstruction;

public interface CurrentConstructionMapper extends BaseMapper<CurrentConstruction> {

    void updateCurrentConstruction(CurrentConstruction currentConstruction);

}
