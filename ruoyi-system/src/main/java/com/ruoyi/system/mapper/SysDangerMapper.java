package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysDanger;

import java.util.List;

public interface SysDangerMapper {

    void create(SysDanger sysDanger);

    List<SysDanger> list(SysDanger sysDanger);

    void delete(Integer id);

    void update(SysDanger sysDanger);

    List<SysDanger> listByLevel();

}
