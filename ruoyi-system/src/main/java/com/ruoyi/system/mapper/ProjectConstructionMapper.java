package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.ProjectConstruction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目施工报表(ProjectConstruction)表数据库访问层
 * @since 2023-06-01 14:05:20
 */
public interface ProjectConstructionMapper {

    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    ProjectConstruction queryById(Integer id);

    /**
     * 查询指定行数据
     * @return 对象列表
     */
    List<ProjectConstruction> queryAllByLimit(ProjectConstruction projectConstruction);

    List<ProjectConstruction> queryAll();


    List<ProjectConstruction> reportChart(ProjectConstruction projectConstruction);

    /**
     * 统计总行数
     * @return 总行数
     */
    long count(ProjectConstruction projectConstruction);

    /**
     * 新增数据
     * @return 影响行数
     */
    int insert(ProjectConstruction projectConstruction);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProjectConstruction> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     * @param entities List<ProjectConstruction> 实例对象列表
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectConstruction> entities);

    /**
     * 修改数据
     * @return 影响行数
     */
    int update(ProjectConstruction projectConstruction);

    /**
     * 通过主键删除数据
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过主键删除数据
     * @return 影响行数
     */
    void delete();


















    Float getZhengChang();
}

