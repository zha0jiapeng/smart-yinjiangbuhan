package com.ruoyi.system.mapper;

import com.ruoyi.system.entity.PreventionDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 双重预防-装置管理(PreventionDevice)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-17 11:18:35
 */
public interface PreventionDeviceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionDevice queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionDevice 查询条件
     * @return 对象列表
     */
    List<PreventionDevice> queryAllByLimit(PreventionDevice preventionDevice);

    /**
     * 统计总行数
     *
     * @param preventionDevice 查询条件
     * @return 总行数
     */
    long count(PreventionDevice preventionDevice);

    /**
     * 新增数据
     * @return 影响行数
     */
    int insert(PreventionDevice preventionDevice);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionDevice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionDevice> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionDevice> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionDevice> entities);

    /**
     * 修改数据
     *
     * @param preventionDevice 实例对象
     * @return 影响行数
     */
    int update(PreventionDevice preventionDevice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Integer getCheckCount(@Param("dangerName") String dangerName,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<Map<String, Object>> hiddenCount(@Param("dangerName") String dangerName,@Param("startTime") String startTime,@Param("endTime") String endTime);

    List<PreventionDevice> listByLevel();

}

