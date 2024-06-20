package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.SysRemind;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提醒表(SysRemind)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-17 15:16:40
 */
public interface SysRemindMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRemind queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param sysRemind 查询条件d
     * @return 对象列表
     */
    List<SysRemind> queryAllByLimit(SysRemind sysRemind);

    /**
     * 统计总行数
     *
     * @param sysRemind 查询条件
     * @return 总行数
     */
    long count(SysRemind sysRemind);

    /**
     * 新增数据
     *
     * @param sysRemind 实例对象
     * @return 影响行数
     */
    int insert(SysRemind sysRemind);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRemind> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRemind> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRemind> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRemind> entities);

    /**
     * 修改数据
     *
     * @param sysRemind 实例对象
     * @return 影响行数
     */
    int update(SysRemind sysRemind);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

