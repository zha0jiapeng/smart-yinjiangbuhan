package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.SysPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 人员信息表(SysPersonnel)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-25 14:31:11
 */
public interface SysPersonnelMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPersonnel queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param sysPersonnel 查询条件
     * @return 对象列表
     */
    List<SysPersonnel> queryAllByLimit(SysPersonnel sysPersonnel);

    /**
     * 查询指定行数据
     *
     * @param sysPersonnel 查询条件
     * @return 对象列表
     */
    List<SysPersonnel> queryAll(SysPersonnel sysPersonnel);

    /**
     * 统计总行数
     *
     * @param sysPersonnel 查询条件
     * @return 总行数
     */
    long count(SysPersonnel sysPersonnel);

    /**
     * 新增数据
     *
     * @param sysPersonnel 实例对象
     * @return 影响行数
     */
    int insert(SysPersonnel sysPersonnel);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysPersonnel> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysPersonnel> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysPersonnel> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysPersonnel> entities);

    /**
     * 修改数据
     *
     * @param sysPersonnel 实例对象
     * @return 影响行数
     */
    int update(SysPersonnel sysPersonnel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<SysPersonnel> queryAllPersonnel(@Param("dayBegin") String dayBegin,@Param("dayEnd")  String dayEnd);
}

