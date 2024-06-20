package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.QualityProblem;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (QualityProblem)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-26 16:03:29
 */
public interface QualityProblemMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityProblem queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param qualityProblem 查询条件
     * @return 对象列表
     */
    List<QualityProblem> queryAllByLimit(QualityProblem qualityProblem);

    /**
     * 统计总行数
     *
     * @param qualityProblem 查询条件
     * @return 总行数
     */
    long count(QualityProblem qualityProblem);

    /**
     * 新增数据
     *
     * @param qualityProblem 实例对象
     * @return 影响行数
     */
    int insert(QualityProblem qualityProblem);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityProblem> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QualityProblem> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityProblem> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QualityProblem> entities);

    /**
     * 修改数据
     *
     * @param qualityProblem 实例对象
     * @return 影响行数
     */
    int update(QualityProblem qualityProblem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    int countTotal();

    int countByMonth(@Param("start") String start,@Param("end") String end);
}

