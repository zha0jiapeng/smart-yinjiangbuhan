package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.PreventionPlanTask;
import com.ruoyi.system.mapper.PreventionPlanTaskMapper;
import com.ruoyi.system.service.PreventionPlanTaskService;
import com.ruoyi.system.service.SysActingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 隐患排查计划任务(PreventionPlanTask)表服务实现类
 *
 * @author makejava
 * @since 2022-12-17 13:22:34
 */
@Service("preventionPlanTaskService")
public class PreventionPlanTaskServiceImpl implements PreventionPlanTaskService {
    @Resource
    private PreventionPlanTaskMapper preventionPlanTaskMapper;

    @Resource
    private SysActingService sysActingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionPlanTask queryById(Integer id) {
        return this.preventionPlanTaskMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionPlanTask 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionPlanTask> queryByPage(PreventionPlanTask preventionPlanTask) {
        return this.preventionPlanTaskMapper.queryAllByLimit(preventionPlanTask);
    }

    /**
     * 新增数据
     *
     * @param preventionPlanTask 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlanTask insert(PreventionPlanTask preventionPlanTask) {
        this.preventionPlanTaskMapper.insert(preventionPlanTask);
        return preventionPlanTask;
    }

    /**
     * 修改数据
     *
     * @param preventionPlanTask 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlanTask update(PreventionPlanTask preventionPlanTask) {
        this.preventionPlanTaskMapper.update(preventionPlanTask);
        return this.queryById(preventionPlanTask.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionPlanTaskMapper.deleteById(id) > 0;
    }

    @Override
    public PreventionPlanTask queryByPlanId(Integer id) {
        return preventionPlanTaskMapper.queryByPlanId(id);
    }

    @Override
    public void completeTask(PreventionPlanTask preventionPlanTask) {
        preventionPlanTask.setCheckUserId(SecurityUtils.getUserId().intValue());
        preventionPlanTask.setCheckUserName(SecurityUtils.getUsername());
        preventionPlanTask.setCheckState("已排查");
        preventionPlanTask.setCheckTime(DateUtil.now());
        preventionPlanTaskMapper.update(preventionPlanTask);
        sysActingService.completeActing("隐患","计划任务",preventionPlanTask.getId());
    }
}
