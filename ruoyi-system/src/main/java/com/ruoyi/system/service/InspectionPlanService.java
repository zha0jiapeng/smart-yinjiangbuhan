package com.ruoyi.system.service;

import com.ruoyi.system.entity.InspectionPlan;

import java.util.List;

/**
 * (InspectionPlan)表服务接口
 *
 * @author makejava
 * @since 2022-11-25 17:09:56
 */
public interface InspectionPlanService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPlan queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionPlan 筛选条件
     * @return 查询结果
     */
    List<InspectionPlan> queryByPage(InspectionPlan inspectionPlan);

    /**
     * 新增数据
     *
     * @param inspectionPlan 实例对象
     * @return 实例对象
     */
    InspectionPlan insert(InspectionPlan inspectionPlan);

    /**
     * 修改数据
     *
     * @param inspectionPlan 实例对象
     * @return 实例对象
     */
    InspectionPlan update(InspectionPlan inspectionPlan);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
