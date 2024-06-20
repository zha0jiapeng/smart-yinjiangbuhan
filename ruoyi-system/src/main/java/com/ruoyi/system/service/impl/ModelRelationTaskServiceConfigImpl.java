package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.ModelRelationTask;
import com.ruoyi.system.domain.ModelRelationTaskConfig;
import com.ruoyi.system.mapper.ModelRelationTaskConfigMapper;
import com.ruoyi.system.mapper.ModelRelationTaskMapper;
import com.ruoyi.system.service.ModelRelationTaskConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelRelationTaskServiceConfigImpl
        extends ServiceImpl<ModelRelationTaskConfigMapper, ModelRelationTaskConfig>
        implements ModelRelationTaskConfigService {

    @Resource
    private ModelRelationTaskMapper modelRelationTaskMapper;

    @Override
    public List<ModelRelationTaskConfig> queryModelRelationTaskConfigByType(Integer code) {

        boolean flag = true;

        QueryWrapper<ModelRelationTask> query = new QueryWrapper<>();
        query.eq("yn", YnEnum.Y.getCode());
        List<ModelRelationTask> relationTasks = modelRelationTaskMapper.selectList(query);
        if (CollectionUtils.isEmpty(relationTasks)) {
            flag = false;
        }

        QueryWrapper<ModelRelationTaskConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type", code);
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ModelRelationTaskConfig> modelRelationTaskConfigs = this.list(queryWrapper);

        if (!CollectionUtils.isEmpty(modelRelationTaskConfigs) && flag) {
            List<ModelRelationTaskConfig> modelRelationTaskConfigList = new ArrayList<>();
            Map<String, List<ModelRelationTask>> listMap = relationTasks.stream().collect(Collectors.groupingBy(ModelRelationTask::getTaskId));

            for (ModelRelationTaskConfig var : modelRelationTaskConfigs) {
                //获取到处理数据,获取不到,返回原数据
                List<ModelRelationTask> modelRelationTasks = listMap.get(var.getTaskId());
                if (CollectionUtils.isEmpty(modelRelationTasks)) {
                    modelRelationTaskConfigList.add(var);
                } else {
                    List<String> list = modelRelationTasks.stream().map(ModelRelationTask::getModelId).collect(Collectors.toList());
                    var.setModelIds(list);
                    modelRelationTaskConfigList.add(var);
                }
            }

            return modelRelationTaskConfigList;
        }

        return modelRelationTaskConfigs;
    }
}
