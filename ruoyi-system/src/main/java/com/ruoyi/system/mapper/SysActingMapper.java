package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.SysActing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代办表(SysActing)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-29 15:23:34
 */
public interface SysActingMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysActing queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param sysActing 查询条件
     * @return 对象列表
     */
    List<SysActing> queryAllByLimit(SysActing sysActing);

    /**
     * 统计总行数
     *
     * @param sysActing 查询条件
     * @return 总行数
     */
    long count(SysActing sysActing);

    /**
     * 新增数据
     *
     * @param sysActing 实例对象
     * @return 影响行数
     */
    int insert(SysActing sysActing);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysActing> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysActing> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysActing> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysActing> entities);

    /**
     * 修改数据
     *
     * @param sysActing 实例对象
     * @return 影响行数
     */
    int update(SysActing sysActing);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    void completeActing(SysActing sysActing);

    void updateExecutorUser(SysActing sysActing);
}

