package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.PreventionPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 隐患排查计划(PreventionPlan)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-17 13:21:33
 */
public interface PreventionPlanMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlan queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionPlan 查询条件
     * @return 对象列表
     */
    List<PreventionPlan> queryAllByLimit(PreventionPlan preventionPlan);

    /**
     * 统计总行数
     *
     * @param preventionPlan 查询条件
     * @return 总行数
     */
    long count(PreventionPlan preventionPlan);

    /**
     * 新增数据
     *
     * @param preventionPlan 实例对象
     * @return 影响行数
     */
    int insert(PreventionPlan preventionPlan);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlan> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionPlan> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlan> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionPlan> entities);

    /**
     * 修改数据
     *
     * @param preventionPlan 实例对象
     * @return 影响行数
     */
    int update(PreventionPlan preventionPlan);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<PreventionPlan> queryAll();
}

