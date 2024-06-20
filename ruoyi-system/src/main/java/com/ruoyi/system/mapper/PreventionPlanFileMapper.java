package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.PreventionPlanFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PreventionPlanFile)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-17 13:22:15
 */
public interface PreventionPlanFileMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlanFile queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionPlanFile 查询条件
     * @return 对象列表
     */
    List<PreventionPlanFile> queryAllByLimit(PreventionPlanFile preventionPlanFile);

    /**
     * 统计总行数
     *
     * @param preventionPlanFile 查询条件
     * @return 总行数
     */
    long count(PreventionPlanFile preventionPlanFile);

    /**
     * 新增数据
     *
     * @param preventionPlanFile 实例对象
     * @return 影响行数
     */
    int insert(PreventionPlanFile preventionPlanFile);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlanFile> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionPlanFile> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionPlanFile> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionPlanFile> entities);

    /**
     * 修改数据
     *
     * @param preventionPlanFile 实例对象
     * @return 影响行数
     */
    int update(PreventionPlanFile preventionPlanFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

