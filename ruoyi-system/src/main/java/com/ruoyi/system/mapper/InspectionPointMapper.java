package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.vo.PointProjectVO;
import com.ruoyi.system.entity.InspectionPoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (InspectionPoint)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-25 17:10:29
 */
public interface InspectionPointMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPoint queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param inspectionPoint 查询条件
     * @return 对象列表
     */
    List<InspectionPoint> queryAllByLimit(InspectionPoint inspectionPoint);

    /**
     * 统计总行数
     *
     * @param inspectionPoint 查询条件
     * @return 总行数
     */
    long count(InspectionPoint inspectionPoint);

    /**
     * 新增数据
     *
     * @param inspectionPoint 实例对象
     * @return 影响行数
     */
    int insert(InspectionPoint inspectionPoint);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPoint> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InspectionPoint> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InspectionPoint> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<InspectionPoint> entities);

    /**
     * 修改数据
     *
     * @param inspectionPoint 实例对象
     * @return 影响行数
     */
    int update(InspectionPoint inspectionPoint);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    PointProjectVO getPointAndProjectById(Integer id);
}

