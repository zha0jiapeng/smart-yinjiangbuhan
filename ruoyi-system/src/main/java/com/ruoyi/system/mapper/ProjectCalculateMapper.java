package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.ProjectCalculate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 三维进度统计数据(ProjectCalculate)表数据库访问层
 *
 * @since 2023-06-01 15:02:18
 */
public interface ProjectCalculateMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     */
    ProjectCalculate queryById(Integer id);

    /**
     * 查询指定行数据
     */
    List<ProjectCalculate> queryAllByLimit(ProjectCalculate projectCalculate);

    List<ProjectCalculate> queryAll();

    /**
     * 统计总行数
     *
     * @param projectCalculate 查询条件
     */
    long count(ProjectCalculate projectCalculate);

    /**
     * 新增数据
     *
     * @param projectCalculate 实例对象
     */
    int insert(ProjectCalculate projectCalculate);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectCalculate> 实例对象列表
     */
    int insertBatch(@Param("entities") List<ProjectCalculate> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectCalculate> 实例对象列表
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectCalculate> entities);

    /**
     * 修改数据
     *
     * @param projectCalculate 实例对象
     */
    int update(ProjectCalculate projectCalculate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     */
    int deleteById(Integer id);

}

