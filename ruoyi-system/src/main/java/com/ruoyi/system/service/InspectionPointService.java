package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PointProjectVO;
import com.ruoyi.system.entity.InspectionPoint;

import java.util.List;

/**
 * (InspectionPoint)表服务接口
 *
 * @author makejava
 * @since 2022-11-25 17:10:29
 */
public interface InspectionPointService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPoint queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionPoint 筛选条件
     * @return 查询结果
     */
    List<InspectionPoint> queryByPage(InspectionPoint inspectionPoint);

    /**
     * 新增数据
     *
     * @param inspectionPoint 实例对象
     * @return 实例对象
     */
    InspectionPoint insert(InspectionPoint inspectionPoint) throws Exception;

    /**
     * 修改数据
     *
     * @param inspectionPoint 实例对象
     * @return 实例对象
     */
    InspectionPoint update(InspectionPoint inspectionPoint);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PointProjectVO getPointAndProjectById(Integer id);

    void updateStopCheck(InspectionPoint inspectionPoint);
}
