package com.ruoyi.system.service;

import com.ruoyi.system.entity.QualityTask;

import java.util.List;

/**
 * 质量任务(QualityTask)表服务接口
 *
 * @author makejava
 * @since 2022-12-26 14:01:34
 */
public interface QualityTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityTask queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qualityTask 筛选条件
     * @return 查询结果
     */
    List<QualityTask> queryByPage(QualityTask qualityTask);

    /**
     * 新增数据
     *
     * @param qualityTask 实例对象
     * @return 实例对象
     */
    QualityTask insert(QualityTask qualityTask);

    /**
     * 修改数据
     *
     * @param qualityTask 实例对象
     * @return 实例对象
     */
    QualityTask update(QualityTask qualityTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    String complete(QualityTask qualityTask);

    List<QualityTask> queryByPageAndActing(QualityTask qualityTask);


}
