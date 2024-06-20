package com.ruoyi.system.service;

import com.ruoyi.system.entity.InspectionPlanTask;

import java.util.List;

/**
 * 计划任务表(InspectionPlanTask)表服务接口
 *
 * @author makejava
 * @since 2022-11-28 17:55:24
 */
public interface InspectionPlanTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPlanTask queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionPlanTask 筛选条件
     * @return 查询结果
     */
    List<InspectionPlanTask> queryByPage(InspectionPlanTask inspectionPlanTask);

    /**
     * 新增数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 实例对象
     */
    InspectionPlanTask insert(InspectionPlanTask inspectionPlanTask);

    /**
     * 修改数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 实例对象
     */
    InspectionPlanTask update(InspectionPlanTask inspectionPlanTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    InspectionPlanTask queryByPlanId(Integer id);
}
