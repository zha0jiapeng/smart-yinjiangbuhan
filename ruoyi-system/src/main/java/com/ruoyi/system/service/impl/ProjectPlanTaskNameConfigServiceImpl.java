package com.ruoyi.system.service.impl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.entity.ProjectPlanTaskNameConfig;
import com.ruoyi.system.mapper.ProjectPlanTaskNameConfigMapper;
import com.ruoyi.system.service.ProjectPlanTaskNameConfigService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 计划-进度任务分类配置表(ProjectPlanTaskNameConfig)表服务实现类
 */
@Service("projectPlanTaskNameConfigService")
public class ProjectPlanTaskNameConfigServiceImpl implements ProjectPlanTaskNameConfigService {
    @Resource
    private ProjectPlanTaskNameConfigMapper projectPlanTaskNameConfigMapper;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public ProjectPlanTaskNameConfig queryById(Integer id) {
        return this.projectPlanTaskNameConfigMapper.queryById(id);
    }

    /**
     * 分页查询
     */
    @Override
    public List<ProjectPlanTaskNameConfig> queryByPage(ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        return this.projectPlanTaskNameConfigMapper.queryAllByLimit(projectPlanTaskNameConfig);
    }

    /**
     * 新增数据
     */
    @Override
    public ProjectPlanTaskNameConfig insert(ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        projectPlanTaskNameConfig.setCreatedDate(DateUtils.getNowDate());
        this.projectPlanTaskNameConfigMapper.insert(projectPlanTaskNameConfig);
        return projectPlanTaskNameConfig;
    }

    /**
     * 修改数据
     */
    @Override
    public ProjectPlanTaskNameConfig update(ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        this.projectPlanTaskNameConfigMapper.update(projectPlanTaskNameConfig);
        return this.queryById(projectPlanTaskNameConfig.getId());
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectPlanTaskNameConfigMapper.deleteById(id) > 0;
    }
}
