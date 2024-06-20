package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.basic.PmsProjectPlanning;
import com.ruoyi.system.domain.basic.ProgressBingRelation;
import com.ruoyi.system.mapper.ProgressBingRelationMapper;
import com.ruoyi.system.service.ProgressBingRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProgressBingRelationServiceImpl extends ServiceImpl<ProgressBingRelationMapper, ProgressBingRelation> implements ProgressBingRelationService {
    @Autowired
    private ProgressBingRelationMapper progressBingRelationMapper;

    public List<ProgressBingRelation> selectList(ProgressBingRelation request) {
        QueryWrapper<ProgressBingRelation> queryWrapper = new QueryWrapper<>();
        return progressBingRelationMapper.selectList(queryWrapper);
    }

}
