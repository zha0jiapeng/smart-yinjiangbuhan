package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.QualityTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 质量模板表(QualityTemplate)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-25 14:48:58
 */
public interface QualityTemplateMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityTemplate queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param qualityTemplate 查询条件
     * @return 对象列表
     */
    List<QualityTemplate> queryAllByLimit(QualityTemplate qualityTemplate);

    /**
     * 统计总行数
     *
     * @param qualityTemplate 查询条件
     * @return 总行数
     */
    long count(QualityTemplate qualityTemplate);

    /**
     * 新增数据
     *
     * @param qualityTemplate 实例对象
     * @return 影响行数
     */
    int insert(QualityTemplate qualityTemplate);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityTemplate> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QualityTemplate> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QualityTemplate> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QualityTemplate> entities);

    /**
     * 修改数据
     *
     * @param qualityTemplate 实例对象
     * @return 影响行数
     */
    int update(QualityTemplate qualityTemplate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

