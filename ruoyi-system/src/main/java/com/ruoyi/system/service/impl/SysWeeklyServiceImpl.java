package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.CycleEnum;
import com.ruoyi.system.domain.basic.SysWeekly;
import com.ruoyi.system.service.SysWeeklyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
public class SysWeeklyServiceImpl implements SysWeeklyService {

    @Override
    public SysWeekly sumProject(CycleEnum cycleEnum) {
        SysWeekly sysWeekly = new SysWeekly();
        if (cycleEnum.equals(CycleEnum.DAY)) {
            sysWeekly.setSumDate(new Date());
        } else if (cycleEnum.equals(CycleEnum.WEEK)) {
            //当前日期 - 7天
        }
        return null;
    }

}
