package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.ProjectRatio;

import java.util.List;

/**
 * 三维_累计比例(ProjectRatio)表数据库访问层
 *
 * @since 2023-06-07 17:55:24
 */
public interface ProjectRatioMapper {

    /**
     * 通过ID查询单条数据
     */
    ProjectRatio queryById();

    /**
     * 查询指定行数据
     */
    List<ProjectRatio> queryAllByLimit(ProjectRatio projectRatio);

    /**
     * 统计总行数
     */
    long count(ProjectRatio projectRatio);

    /**
     * 新增数据
     */
    int insert(ProjectRatio projectRatio);

    /**
     * 修改数据
     */
    int update(ProjectRatio projectRatio);

    /**
     * 通过主键删除数据
     */
    int deleteById(Integer id);

    List<ProjectRatio> queryAll();

    ProjectRatio queryAllMoney();
}

