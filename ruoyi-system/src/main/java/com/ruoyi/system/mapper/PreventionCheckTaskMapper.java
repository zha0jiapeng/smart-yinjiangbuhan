package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.PreventionCheckTaskVO;
import com.ruoyi.system.entity.PreventionCheckTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 排查任务表(PreventionCheckTask)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-18 14:04:46
 */
public interface PreventionCheckTaskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionCheckTask queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionCheckTask 查询条件
     * @return 对象列表
     */
    List<PreventionCheckTask> queryAllByLimit(PreventionCheckTask preventionCheckTask);

    /**
     * 统计总行数
     *
     * @param preventionCheckTask 查询条件
     * @return 总行数
     */
    long count(PreventionCheckTask preventionCheckTask);

    /**
     * 新增数据
     *
     * @param preventionCheckTask 实例对象
     * @return 影响行数
     */
    int insert(PreventionCheckTask preventionCheckTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionCheckTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionCheckTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionCheckTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionCheckTask> entities);

    /**
     * 修改数据
     *
     * @param preventionCheckTask 实例对象
     * @return 影响行数
     */
    int update(PreventionCheckTask preventionCheckTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    PreventionCheckTask queryByConfigId(Integer id);

    PreventionCheckTaskVO getTaskInfoById(Integer taskId);
}

