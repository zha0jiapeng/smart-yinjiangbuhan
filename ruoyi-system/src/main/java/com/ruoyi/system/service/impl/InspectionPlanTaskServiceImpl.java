package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.InspectionPlanTask;
import com.ruoyi.system.mapper.InspectionPlanTaskMapper;
import com.ruoyi.system.service.InspectionPlanTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计划任务表(InspectionPlanTask)表服务实现类
 *
 * @author makejava
 * @since 2022-11-28 17:55:24
 */
@Service("inspectionPlanTaskService")
public class InspectionPlanTaskServiceImpl implements InspectionPlanTaskService {
    @Resource
    private InspectionPlanTaskMapper inspectionPlanTaskMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionPlanTask queryById(Integer id) {
        return this.inspectionPlanTaskMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionPlanTask 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionPlanTask> queryByPage(InspectionPlanTask inspectionPlanTask) {
        return inspectionPlanTaskMapper.queryAllByLimit(inspectionPlanTask);
    }

    /**
     * 新增数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlanTask insert(InspectionPlanTask inspectionPlanTask) {
        this.inspectionPlanTaskMapper.insert(inspectionPlanTask);
        return inspectionPlanTask;
    }

    /**
     * 修改数据
     *
     * @param inspectionPlanTask 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlanTask update(InspectionPlanTask inspectionPlanTask) {
        this.inspectionPlanTaskMapper.update(inspectionPlanTask);
        return this.queryById(inspectionPlanTask.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionPlanTaskMapper.deleteById(id) > 0;
    }

    @Override
    public InspectionPlanTask queryByPlanId(Integer id) {
        return inspectionPlanTaskMapper.queryByPlanId(id);
    }
}
