package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionPlanTask;

import java.util.List;

/**
 * 隐患排查计划任务(PreventionPlanTask)表服务接口
 *
 * @author makejava
 * @since 2022-12-17 13:22:34
 */
public interface PreventionPlanTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlanTask queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionPlanTask 筛选条件
     * @return 查询结果
     */
    List<PreventionPlanTask> queryByPage(PreventionPlanTask preventionPlanTask);

    /**
     * 新增数据
     *
     * @param preventionPlanTask 实例对象
     * @return 实例对象
     */
    PreventionPlanTask insert(PreventionPlanTask preventionPlanTask);

    /**
     * 修改数据
     *
     * @param preventionPlanTask 实例对象
     * @return 实例对象
     */
    PreventionPlanTask update(PreventionPlanTask preventionPlanTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PreventionPlanTask queryByPlanId(Integer id);

    void completeTask(PreventionPlanTask preventionPlanTask);
}
