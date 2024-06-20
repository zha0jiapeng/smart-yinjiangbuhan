package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.domain.bim.RateBimDomain;

public interface ScheduleTbmSegmentService extends IService<ScheduleTbmSegment> {

    RateBimDomain.ScheduleTbmSegment queryScheduleTbmSegmentByMonthBase(String monthBase);

}
