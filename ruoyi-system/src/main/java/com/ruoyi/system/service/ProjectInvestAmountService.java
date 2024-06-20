package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.ProjectInvestAmount;

import java.util.List;

public interface ProjectInvestAmountService extends IService<ProjectInvestAmount> {

    List<ProjectInvestAmount> queryAllProjectInvestAmount(ProjectInvestAmount projectInvestAmount);

    ProjectInvestAmount queryOneProjectInvestAmount(ProjectInvestAmount projectInvestAmount);

    void insertProjectInvestAmountService(ProjectInvestAmount projectInvestAmount);

}
