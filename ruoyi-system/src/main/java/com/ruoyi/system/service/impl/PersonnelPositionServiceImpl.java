package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.PersonnelPositionBus;
import com.ruoyi.system.domain.basic.PersonnelPosition;
import com.ruoyi.system.mapper.PersonnelPositionMapper;
import com.ruoyi.system.service.PersonnelPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PersonnelPositionServiceImpl extends ServiceImpl<PersonnelPositionMapper, PersonnelPosition> implements PersonnelPositionService {

    @Resource
    private PersonnelPositionMapper personnelPositionMapper;

    @Override
    public List<PersonnelPositionBus> queryPersonnelPositionRelationPersonnel(PersonnelPosition request) {
        return personnelPositionMapper.queryPersonnelPositionRelationPersonnel(request);
    }

}
