package com.ruoyi.system.mapper;
import com.ruoyi.system.entity.ProjectPlanTaskNameConfig;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 计划-进度任务分类配置表(ProjectPlanTaskNameConfig)表数据库访问层
 */
public interface ProjectPlanTaskNameConfigMapper {

    /**
     * 通过ID查询单条数据
     */
    ProjectPlanTaskNameConfig queryById(Integer id);

    /**
     * 查询指定行数据
     */
    List<ProjectPlanTaskNameConfig> queryAllByLimit(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 统计总行数
     */
    long count(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 新增数据
     */
    int insert(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     */
    int insertBatch(@Param("entities") List<ProjectPlanTaskNameConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectPlanTaskNameConfig> entities);

    /**
     * 修改数据
     */
    int update(ProjectPlanTaskNameConfig projectPlanTaskNameConfig);

    /**
     * 通过主键删除数据
     */
    int deleteById(Integer id);

}

