package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.PersonnelPositionBus;
import com.ruoyi.system.domain.basic.PersonnelPosition;

import java.util.List;

public interface PersonnelPositionService extends IService<PersonnelPosition> {

    /**
     * 人员定位关联员工接口
     *
     * @param request 参数;
     * @return 结果;
     */
    List<PersonnelPositionBus> queryPersonnelPositionRelationPersonnel(PersonnelPosition request);

}
