package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.PreventionSecurityRiskVO;
import com.ruoyi.system.entity.PreventionSecurityRisk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 双重预防-风险分析清单明细(PreventionSecurityRisk)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-17 11:18:53
 */
public interface PreventionSecurityRiskMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionSecurityRisk queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param preventionSecurityRisk 查询条件
     * @return 对象列表
     */
    List<PreventionSecurityRisk> queryAllByLimit(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 统计总行数
     *
     * @param preventionSecurityRisk 查询条件
     * @return 总行数
     */
    long count(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 新增数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 影响行数
     */
    int insert(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionSecurityRisk> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<PreventionSecurityRisk> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<PreventionSecurityRisk> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<PreventionSecurityRisk> entities);

    /**
     * 修改数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 影响行数
     */
    int update(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<String> countUnit(Integer id);

    List<String> countEvent(Integer id);

    List<String> countControl(Integer id);

    List<String> countTask(Integer id);

    List<PreventionSecurityRisk> queryAll();

    List<PreventionSecurityRiskVO> queryAllVO();

}

