package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDanger;
import com.ruoyi.system.mapper.SysDangerMapper;
import com.ruoyi.system.service.ISysDangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDangerServiceImpl implements ISysDangerService {

    @Autowired
    private SysDangerMapper sysDangerMapper;

    @Override
    public void create(SysDanger sysDanger) {
        sysDangerMapper.create(sysDanger);
    }

    @Override
    public List<SysDanger> list(SysDanger sysDanger) {
        return sysDangerMapper.list(sysDanger);
    }

    @Override
    public void delete(Integer id) {
        sysDangerMapper.delete(id);
    }

    @Override
    public void update(SysDanger sysDanger) {
        sysDangerMapper.update(sysDanger);
    }

    @Override
    public List<SysDanger> listByLevel() {
        return sysDangerMapper.listByLevel();
    }

    @Override
    public List<Map<String, Object>> getGeoData() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<SysDanger> list = sysDangerMapper.list(new SysDanger());
        for (SysDanger sysDanger : list) {
            String addressInfo = sysDanger.getAddressInfo();
            if (StringUtils.isEmpty(addressInfo)) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            String dangerLevel = sysDanger.getDangerLevel();
            String s = colourLevel(dangerLevel);
            map.put("colour",s);
            map.put("index",addressInfo);
            mapList.add(map);
        }
        return mapList;
    }
    public String colourLevel(String level) {

        switch (level) {
            case "一级":
                return "rgba(0,0,255,0.5)";
            case "二级":
                return "rgba(255,255,0,0.5)";
            case "三级":
                return "rgba(255,97,0,0.5)";
            case "四级":
                return "rgba(255,0,0,0.5)";
        }
        return "rgba(255,255,255,0.3)";
    }
}
