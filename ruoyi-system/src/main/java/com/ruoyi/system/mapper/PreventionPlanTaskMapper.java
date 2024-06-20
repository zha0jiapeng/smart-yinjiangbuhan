package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.PreventionPlanTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 隐患排查计划任务(PreventionPlanTask)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-17 13:22:34
 */
public interface PreventionPlanTaskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlanTask queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionPlanTask 查询条件
     * @return 对象列表
     */
    List<PreventionPlanTask> queryAllByLimit(PreventionPlanTask preventionPlanTask);

    /**
     * 统计总行数
     *
     * @param preventionPlanTask 查询条件
     * @return 总行数
     */
    long count(PreventionPlanTask preventionPlanTask);

    /**
     * 新增数据
     *
     * @param preventionPlanTask 实例对象
     * @return 影响行数
     */
    int insert(PreventionPlanTask preventionPlanTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlanTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionPlanTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlanTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionPlanTask> entities);

    /**
     * 修改数据
     *
     * @param preventionPlanTask 实例对象
     * @return 影响行数
     */
    int update(PreventionPlanTask preventionPlanTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    PreventionPlanTask queryByPlanId(Integer id);
}

