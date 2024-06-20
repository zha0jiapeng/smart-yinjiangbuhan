package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionPlanFile;

import java.util.List;

/**
 * (PreventionPlanFile)表服务接口
 *
 * @author makejava
 * @since 2022-12-17 13:22:15
 */
public interface PreventionPlanFileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionPlanFile queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionPlanFile 筛选条件
     * @return 查询结果
     */
    List<PreventionPlanFile> queryByPage(PreventionPlanFile preventionPlanFile);

    /**
     * 新增数据
     *
     * @param preventionPlanFile 实例对象
     * @return 实例对象
     */
    PreventionPlanFile insert(PreventionPlanFile preventionPlanFile);

    /**
     * 修改数据
     *
     * @param preventionPlanFile 实例对象
     * @return 实例对象
     */
    PreventionPlanFile update(PreventionPlanFile preventionPlanFile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
