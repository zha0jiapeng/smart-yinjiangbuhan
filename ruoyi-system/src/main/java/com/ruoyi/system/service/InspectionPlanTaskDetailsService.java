package com.ruoyi.system.service;

import com.ruoyi.system.entity.InspectionPlanTaskDetails;

import java.util.List;

/**
 * 计划任务详情表(InspectionPlanTaskDetails)表服务接口
 *
 * @author makejava
 * @since 2022-11-28 17:55:43
 */
public interface InspectionPlanTaskDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionPlanTaskDetails queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionPlanTaskDetails 筛选条件
     * @return 查询结果
     */
    List<InspectionPlanTaskDetails> queryByPage(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 新增数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 实例对象
     */
    InspectionPlanTaskDetails insert(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 修改数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 实例对象
     */
    InspectionPlanTaskDetails update(InspectionPlanTaskDetails inspectionPlanTaskDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void insertBatch(List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList);

    void completeTask(InspectionPlanTaskDetails inspectionPlanTaskDetails);
}
