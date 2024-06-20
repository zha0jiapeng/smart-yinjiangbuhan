package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.basic.ProjectInvestAmount;
import com.ruoyi.system.mapper.ProjectInvestAmountMapper;
import com.ruoyi.system.service.ProjectInvestAmountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProjectInvestAmountServiceImpl
        extends ServiceImpl<ProjectInvestAmountMapper, ProjectInvestAmount>
        implements ProjectInvestAmountService {

    @Resource
    private ProjectInvestAmountMapper projectInvestAmountMapper;

    @Override
    public List<ProjectInvestAmount> queryAllProjectInvestAmount(ProjectInvestAmount projectInvestAmount) {
        QueryWrapper<ProjectInvestAmount> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_date");
        return this.list(queryWrapper);
    }

    @Override
    public ProjectInvestAmount queryOneProjectInvestAmount(ProjectInvestAmount projectInvestAmount) {
        QueryWrapper<ProjectInvestAmount> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_date");
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public void insertProjectInvestAmountService(ProjectInvestAmount projectInvestAmount) {
        ProjectInvestAmount projectInvestAmountSave = new ProjectInvestAmount();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());

        projectInvestAmountSave.setUploadDate(format);
        projectInvestAmountSave.setInvestAmount(projectInvestAmount.getInvestAmount());
        projectInvestAmountSave.setEstimateAmount(projectInvestAmount.getEstimateAmount());
        projectInvestAmountSave.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        projectInvestAmountSave.setCreatedDate(new Date());
        projectInvestAmountSave.setYn(YnEnum.Y.getCode());
        this.save(projectInvestAmountSave);
    }

}
