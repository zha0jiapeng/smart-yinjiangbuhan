package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.InspectionPlanTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划任务表(InspectionPlanTask)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-28 17:55:24
 */
public interface InspectionPlanTaskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPlanTask queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param inspectionPlanTask 查询条件
     * @return 对象列表
     */
    List<InspectionPlanTask> queryAllByLimit(InspectionPlanTask inspectionPlanTask);

    /**
     * 统计总行数
     *
     * @param inspectionPlanTask 查询条件
     * @return 总行数
     */
    long count(InspectionPlanTask inspectionPlanTask);

    /**
     * 新增数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 影响行数
     */
    int insert(InspectionPlanTask inspectionPlanTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPlanTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InspectionPlanTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPlanTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<InspectionPlanTask> entities);

    /**
     * 修改数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 影响行数
     */
    int update(InspectionPlanTask inspectionPlanTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    InspectionPlanTask queryByPlanId(Integer id);
}

