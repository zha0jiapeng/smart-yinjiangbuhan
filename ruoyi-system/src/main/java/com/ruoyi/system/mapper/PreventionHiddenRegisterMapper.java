package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.PreventionHiddenRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 隐患录入记录表(PreventionHiddenRegister)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-22 17:20:34
 */
public interface PreventionHiddenRegisterMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionHiddenRegister queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionHiddenRegister 查询条件
     * @return 对象列表
     */
    List<PreventionHiddenRegister> queryAllByLimit(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 统计总行数
     *
     * @param preventionHiddenRegister 查询条件
     * @return 总行数
     */
    long count(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 新增数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 影响行数
     */
    int insert(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionHiddenRegister> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionHiddenRegister> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionHiddenRegister> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionHiddenRegister> entities);

    /**
     * 修改数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 影响行数
     */
    int update(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Map<String, Object>> countProgress(Integer deptId);

    List<Map<String, Object>> countTask(Integer deptId);
}

