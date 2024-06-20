package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.InspectionPlanTaskDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计划任务详情表(InspectionPlanTaskDetails)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-28 17:55:43
 */
public interface InspectionPlanTaskDetailsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPlanTaskDetails queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param inspectionPlanTaskDetails 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<InspectionPlanTaskDetails> queryAllByLimit(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 统计总行数
     *
     * @param inspectionPlanTaskDetails 查询条件
     * @return 总行数
     */
    long count(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 新增数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 影响行数
     */
    int insert(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPlanTaskDetails> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InspectionPlanTaskDetails> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPlanTaskDetails> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<InspectionPlanTaskDetails> entities);

    /**
     * 修改数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 影响行数
     */
    int update(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Integer countCheckStateByTaskId(Integer planTaskId);
}

