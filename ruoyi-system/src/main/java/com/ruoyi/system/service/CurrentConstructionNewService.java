package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.entity.CurrentConstructionNew;

import java.util.List;

public interface CurrentConstructionNewService extends IService<CurrentConstructionNew> {

    /**
     * 计算掘进里程
     */
    public void calculate(ScheduleTbmSegment scheduleTbmSegment);

    public void updateCurrentConstructionNew(List<CurrentConstructionNew> currentConstructionNews);

}
