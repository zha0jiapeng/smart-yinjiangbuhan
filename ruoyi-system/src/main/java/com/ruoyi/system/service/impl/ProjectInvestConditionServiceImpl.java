package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.basic.ProjectInvestCondition;
import com.ruoyi.system.mapper.ProjectInvestConditionMapper;
import com.ruoyi.system.service.ProjectInvestConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProjectInvestConditionServiceImpl extends ServiceImpl<ProjectInvestConditionMapper, ProjectInvestCondition> implements ProjectInvestConditionService {
    @Override
    public List<ProjectInvestCondition> queryProjectInvestCondition(ProjectInvestCondition projectInvestCondition) {
        QueryWrapper<ProjectInvestCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_date");
        return this.list(queryWrapper);
    }

    @Override
    public void saveProjectInvestCondition(ProjectInvestCondition projectInvestCondition) {

        BigDecimal projectQuantity = new BigDecimal(projectInvestCondition.getProjectQuantity());
        BigDecimal unitPrice = new BigDecimal(projectInvestCondition.getUnitPrice());

        projectInvestCondition.setTotalAmount(projectQuantity.multiply(unitPrice).setScale(2, RoundingMode.DOWN));
        projectInvestCondition.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        projectInvestCondition.setCreatedDate(new Date());
        projectInvestCondition.setYn(YnEnum.Y.getCode());

        this.save(projectInvestCondition);
    }

    @Override
    public void updateProjectInvestCondition(ProjectInvestCondition projectInvestCondition) {

        BigDecimal projectQuantity = new BigDecimal(projectInvestCondition.getProjectQuantity());
        BigDecimal unitPrice = new BigDecimal(projectInvestCondition.getUnitPrice());

        projectInvestCondition.setTotalAmount(projectQuantity.multiply(unitPrice).setScale(2, RoundingMode.DOWN));

        projectInvestCondition.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        projectInvestCondition.setModifyDate(new Date());

        this.updateById(projectInvestCondition);
    }

    @Override
    public void deleteProjectInvestCondition(ProjectInvestCondition projectInvestCondition) {
        this.removeById(projectInvestCondition.getId());
    }
}
