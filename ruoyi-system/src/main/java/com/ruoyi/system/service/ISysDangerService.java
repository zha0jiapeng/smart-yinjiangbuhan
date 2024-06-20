package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysDanger;

import java.util.List;
import java.util.Map;

public interface ISysDangerService {

    void create(SysDanger sysDanger);

    List<SysDanger> list(SysDanger sysDanger);

    void delete(Integer id);

    void update(SysDanger sysDanger);

    List<SysDanger> listByLevel();

    List<Map<String, Object>> getGeoData();

}
