package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.PersonnelPositionBus;
import com.ruoyi.system.domain.basic.PersonnelPosition;

import java.util.List;

public interface PersonnelPositionMapper extends BaseMapper<PersonnelPosition> {

    /**
     * 人员定位关联员工接口
     *
     * @param request 参数;
     * @return 结果;
     */
    List<PersonnelPositionBus> queryPersonnelPositionRelationPersonnel(PersonnelPosition request);

}
