package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.entity.CurrentConstruction;

public interface CurrentConstructionService extends IService<CurrentConstruction> {

    void updateCurrentConstruction(CurrentConstruction currentConstruction);


    void bimNewListSchedule();
}
