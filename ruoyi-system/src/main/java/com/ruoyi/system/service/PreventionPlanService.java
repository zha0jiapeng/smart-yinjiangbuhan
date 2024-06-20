package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionPlan;

import java.util.List;

/**
 * 隐患排查计划(PreventionPlan)表服务接口
 *
 * @author makejava
 * @since 2022-12-17 13:21:45
 */
public interface PreventionPlanService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlan queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionPlan 筛选条件
     * @return 查询结果
     */
    List<PreventionPlan> queryByPage(PreventionPlan preventionPlan);

    /**
     * 新增数据
     *
     * @param preventionPlan 实例对象
     * @return 实例对象
     */
    PreventionPlan insert(PreventionPlan preventionPlan);

    /**
     * 修改数据
     *
     * @param preventionPlan 实例对象
     * @return 实例对象
     */
    PreventionPlan update(PreventionPlan preventionPlan);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
