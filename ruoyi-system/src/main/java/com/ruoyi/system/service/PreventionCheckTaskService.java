package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PreventionCheckTaskVO;
import com.ruoyi.system.entity.PreventionCheckTask;

import java.util.List;

/**
 * 排查任务表(PreventionCheckTask)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 14:04:46
 */
public interface PreventionCheckTaskService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionCheckTask queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionCheckTask 筛选条件
     * @return 查询结果
     */
    List<PreventionCheckTask> queryByPage(PreventionCheckTask preventionCheckTask);

    /**
     * 新增数据
     *
     * @param preventionCheckTask 实例对象
     * @return 实例对象
     */
    PreventionCheckTask insert(PreventionCheckTask preventionCheckTask);

    /**
     * 修改数据
     *
     * @param preventionCheckTask 实例对象
     * @return 实例对象
     */
    PreventionCheckTask update(PreventionCheckTask preventionCheckTask);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PreventionCheckTask queryByConfigId(Integer id);

    PreventionCheckTaskVO getTaskInfoById(Integer taskId);

    void updateTaskState(PreventionCheckTask preventionCheckTask);

}
