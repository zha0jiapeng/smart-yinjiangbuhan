package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.ProjectInvestCondition;

import java.util.List;

public interface ProjectInvestConditionService extends IService<ProjectInvestCondition> {

    List<ProjectInvestCondition> queryProjectInvestCondition(ProjectInvestCondition projectInvestCondition);

    void saveProjectInvestCondition(ProjectInvestCondition projectInvestCondition);

    void updateProjectInvestCondition(ProjectInvestCondition projectInvestCondition);

    void deleteProjectInvestCondition(ProjectInvestCondition projectInvestCondition);

}
