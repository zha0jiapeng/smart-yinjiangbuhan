package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.QualityTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 质量任务(QualityTask)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-26 14:01:31
 */
public interface QualityTaskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityTask queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param qualityTask 查询条件
     * @return 对象列表
     */
    List<QualityTask> queryAllByLimit(QualityTask qualityTask);

    /**
     * 统计总行数
     *
     * @param qualityTask 查询条件
     * @return 总行数
     */
    long count(QualityTask qualityTask);

    /**
     * 新增数据
     *
     * @param qualityTask 实例对象
     * @return 影响行数
     */
    int insert(QualityTask qualityTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QualityTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QualityTask> entities);

    /**
     * 修改数据
     *
     * @param qualityTask 实例对象
     * @return 影响行数
     */
    int update(QualityTask qualityTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

