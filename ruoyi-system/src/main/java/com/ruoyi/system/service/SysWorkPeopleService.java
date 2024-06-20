package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysWorkPeople;

public interface SysWorkPeopleService extends IService<SysWorkPeople> {

    SysWorkPeople insert(SysWorkPeople sysWorkPeople);

    void updWorkPeopleDeparture(SysWorkPeople sysWorkPeople);

}
