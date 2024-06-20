package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.PointProjectVO;
import com.ruoyi.system.entity.InspectionPlanTask;
import com.ruoyi.system.entity.InspectionPlanTaskDetails;
import com.ruoyi.system.mapper.InspectionPlanTaskDetailsMapper;
import com.ruoyi.system.service.InspectionPlanTaskDetailsService;
import com.ruoyi.system.service.InspectionPlanTaskService;
import com.ruoyi.system.service.InspectionPointService;
import com.ruoyi.system.service.SysActingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计划任务详情表(InspectionPlanTaskDetails)表服务实现类
 *
 * @author makejava
 * @since 2022-11-28 17:55:43
 */
@Service("inspectionPlanTaskDetailsService")
public class InspectionPlanTaskDetailsServiceImpl implements InspectionPlanTaskDetailsService {
    @Resource
    private InspectionPlanTaskDetailsMapper inspectionPlanTaskDetailsMapper;

    @Resource
    private InspectionPointService inspectionPointService;

    @Resource
    private InspectionPlanTaskService inspectionPlanTaskService;

    @Resource
    private SysActingService sysActingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionPlanTaskDetails queryById(Integer id) {
        return this.inspectionPlanTaskDetailsMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionPlanTaskDetails 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionPlanTaskDetails> queryByPage(InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList = inspectionPlanTaskDetailsMapper.queryAllByLimit(inspectionPlanTaskDetails);
        inspectionPlanTaskDetailsList.forEach(i -> {
            Integer pointId = i.getPointId();
            PointProjectVO pointAndProjectById = inspectionPointService.getPointAndProjectById(pointId);
            i.setFormJson(pointAndProjectById.getFormData());
        });
        return inspectionPlanTaskDetailsList;
    }

    /**
     * 新增数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlanTaskDetails insert(InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        this.inspectionPlanTaskDetailsMapper.insert(inspectionPlanTaskDetails);
        return inspectionPlanTaskDetails;
    }

    /**
     * 修改数据
     *
     * @param inspectionPlanTaskDetails 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlanTaskDetails update(InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        this.inspectionPlanTaskDetailsMapper.update(inspectionPlanTaskDetails);
        return this.queryById(inspectionPlanTaskDetails.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionPlanTaskDetailsMapper.deleteById(id) > 0;
    }

    @Override
    public void insertBatch(List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList) {
        this.inspectionPlanTaskDetailsMapper.insertBatch(inspectionPlanTaskDetailsList);
    }

    /**
     * 完成任务
     * @param inspectionPlanTaskDetails
     */
    @Override
    public void completeTask(InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        inspectionPlanTaskDetails.setCheckUser(SecurityUtils.getUsername());
        inspectionPlanTaskDetails.setCheckTime(DateUtil.now());
        inspectionPlanTaskDetails.setCheckState("已排查");
        this.inspectionPlanTaskDetailsMapper.update(inspectionPlanTaskDetails);
        Integer planTaskId = inspectionPlanTaskDetails.getPlanTaskId();
        // 统计还剩多少未排查任务
        Integer count = inspectionPlanTaskDetailsMapper.countCheckStateByTaskId(planTaskId);
        // 未剩余排查任务---完成计划任务---完成待办
        if (count == 0) {
            InspectionPlanTask inspectionPlanTask = new InspectionPlanTask();
            inspectionPlanTask.setId(planTaskId);
            inspectionPlanTask.setPlanTaskState("已完成");
            inspectionPlanTaskService.update(inspectionPlanTask);
            sysActingService.completeActing("巡检","巡检任务",planTaskId);
        }
    }
}
