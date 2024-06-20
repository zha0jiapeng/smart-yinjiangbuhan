package com.ruoyi.system.service;

import com.ruoyi.system.entity.InspectionRoute;

import java.util.List;

/**
 * 巡检路线表(InspectionRoute)表服务接口
 *
 * @author makejava
 * @since 2022-11-25 17:11:06
 */
public interface InspectionRouteService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionRoute queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionRoute 筛选条件
     * @return 查询结果
     */
    List<InspectionRoute> queryByPage(InspectionRoute inspectionRoute);

    /**
     * 新增数据
     *
     * @param inspectionRoute 实例对象
     * @return 实例对象
     */
    InspectionRoute insert(InspectionRoute inspectionRoute);

    /**
     * 修改数据
     *
     * @param inspectionRoute 实例对象
     * @return 实例对象
     */
    InspectionRoute update(InspectionRoute inspectionRoute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void updateStopCheck(InspectionRoute inspectionRoute);
}
