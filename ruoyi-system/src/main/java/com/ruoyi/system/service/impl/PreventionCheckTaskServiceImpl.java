package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.vo.PreventionCheckTaskVO;
import com.ruoyi.system.entity.PreventionCheckTask;
import com.ruoyi.system.mapper.PreventionCheckTaskMapper;
import com.ruoyi.system.service.PreventionCheckTaskService;
import com.ruoyi.system.service.SysActingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 排查任务表(PreventionCheckTask)表服务实现类
 *
 * @author makejava
 * @since 2022-11-18 14:04:46
 */
@Service("preventionCheckTaskService")
public class PreventionCheckTaskServiceImpl implements PreventionCheckTaskService {

    @Resource
    private PreventionCheckTaskMapper preventionCheckTaskDao;

    @Resource
    private SysActingService sysActingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionCheckTask queryById(Integer id) {
        return this.preventionCheckTaskDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionCheckTask 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionCheckTask> queryByPage(PreventionCheckTask preventionCheckTask) {
        return preventionCheckTaskDao.queryAllByLimit(preventionCheckTask);
    }

    /**
     * 新增数据
     *
     * @param preventionCheckTask 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionCheckTask insert(PreventionCheckTask preventionCheckTask) {
        this.preventionCheckTaskDao.insert(preventionCheckTask);
        return preventionCheckTask;
    }

    /**
     * 修改数据
     *
     * @param preventionCheckTask 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionCheckTask update(PreventionCheckTask preventionCheckTask) {
        this.preventionCheckTaskDao.update(preventionCheckTask);
        return this.queryById(preventionCheckTask.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionCheckTaskDao.deleteById(id) > 0;
    }

    @Override
    public PreventionCheckTask queryByConfigId(Integer id) {
        return preventionCheckTaskDao.queryByConfigId(id);
    }

    @Override
    public PreventionCheckTaskVO getTaskInfoById(Integer taskId) {
        return preventionCheckTaskDao.getTaskInfoById(taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTaskState(PreventionCheckTask preventionCheckTask) {
        this.preventionCheckTaskDao.update(preventionCheckTask);
        // 更新待办任务的状态
        sysActingService.completeActing("隐患","排查任务",preventionCheckTask.getId());
    }
}
