package com.ruoyi.system.service;
import com.ruoyi.system.entity.ProjectPlanTaskNameConfig;
import java.util.List;

/**
 * 计划-进度任务分类配置表(ProjectPlanTaskNameConfig)表服务接口
 */
public interface ProjectPlanTaskNameConfigService {

    /**
     * 通过ID查询单条数据
     */
    ProjectPlanTaskNameConfig queryById(Integer id);

    /**
     * 分页查询
     */
    List<ProjectPlanTaskNameConfig> queryByPage(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 新增数据
     */
    ProjectPlanTaskNameConfig insert(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 修改数据
     */
    ProjectPlanTaskNameConfig update(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 通过主键删除数据
     */
    boolean deleteById(Integer id);

}
