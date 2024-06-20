package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.PreventionCheckTaskConfigVO;
import com.ruoyi.system.entity.PreventionCheckTaskConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 双重预防-排查任务配置表(PreventionCheckTaskConfig)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-18 13:58:19
 */
public interface PreventionCheckTaskConfigMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionCheckTaskConfig queryById(Integer id);

    /**
     * 查询指定行数据
     * @return 对象列表
     */
    List<PreventionCheckTaskConfigVO> queryAllByLimit(PreventionCheckTaskConfigVO preventionCheckTaskConfigVO);

    /**
     * 统计总行数
     *
     * @param preventionCheckTaskConfig 查询条件
     * @return 总行数
     */
    long count(PreventionCheckTaskConfig preventionCheckTaskConfig);

    /**
     * 新增数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 影响行数
     */
    int insert(PreventionCheckTaskConfig preventionCheckTaskConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionCheckTaskConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionCheckTaskConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionCheckTaskConfig> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionCheckTaskConfig> entities);

    /**
     * 修改数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 影响行数
     */
    int update(PreventionCheckTaskConfig preventionCheckTaskConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<PreventionCheckTaskConfig> queryAll();
}

