package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.PreventionPlan;
import com.ruoyi.system.entity.PreventionPlanTask;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.mapper.PreventionPlanMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 隐患排查计划(PreventionPlan)表服务实现类
 *
 * @author makejava
 * @since 2022-12-17 13:21:45
 */
@Service("preventionPlanService")
public class PreventionPlanServiceImpl implements PreventionPlanService {
    @Resource
    private PreventionPlanMapper preventionPlanMapper;

    @Resource
    private PreventionPlanTaskService preventionPlanTaskService;

    @Autowired
    private IFileManageService fileManageService;

    @Resource
    private ISysUserService userService;

    @Resource
    private SysActingService sysActingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionPlan queryById(Integer id) {
        return this.preventionPlanMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionPlan 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionPlan> queryByPage(PreventionPlan preventionPlan) {
        List<PreventionPlan> preventionPlans = this.preventionPlanMapper.queryAllByLimit(preventionPlan);
        preventionPlans.forEach(i -> {
            String fileId = i.getFileId();
            if (StrUtil.isNotEmpty(fileId)) {
                String[] split = fileId.split(",");
                List<Integer> ids = new ArrayList<>();
                for (int i1 = 0; i1 < split.length; i1++) {
                    ids.add(Integer.valueOf(split[i1]));
                }
                i.setFileManageList(fileManageService.getListByIds(ids));
            }
        });
        return preventionPlans;
    }

    /**
     * 新增数据
     *
     * @param preventionPlan 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlan insert(PreventionPlan preventionPlan) {
        preventionPlan.setPlanState(1);
        preventionPlan.setCreateId(SecurityUtils.getUserId().intValue());
        preventionPlan.setCreateUser(SecurityUtils.getUsername());
        this.preventionPlanMapper.insert(preventionPlan);
        return preventionPlan;
    }

    /**
     * 修改数据
     *
     * @param preventionPlan 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlan update(PreventionPlan preventionPlan) {
        this.preventionPlanMapper.update(preventionPlan);
        return this.queryById(preventionPlan.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionPlanMapper.deleteById(id) > 0;
    }

//    @Scheduled(cron = "0/60 * * * * ?")
    public void scheduledPlan() {
        PreventionPlan preventionPlan = new PreventionPlan();
        preventionPlan.setPlanState(0);
        List<PreventionPlan> preventionPlans = preventionPlanMapper.queryAllByLimit(preventionPlan);
        preventionPlans.forEach(i -> {
            String planStartTime = i.getCheckStartTime();
            DateTime parse = DateUtil.parse(planStartTime, "yyyy-MM-dd HH:mm:ss");
            Date date = parse.toJdkDate();
            DateTime now = DateUtil.date();
            boolean after = date.after(now);
            String planEndTime = i.getCheckEndTime();
            DateTime endTime = DateUtil.parse(planEndTime, "yyyy-MM-dd HH:mm:ss");
            Date end = endTime.toJdkDate();
            boolean endAfter = end.after(now);
            if (!after && endAfter) {
                String cycle = i.getCycle();
                String unit = i.getUnit();
                DateTime offset = DateUtil.date().offset(PreventionCheckTaskConfigServiceImpl.strUnitDate(unit), Integer.valueOf(cycle));
                PreventionPlanTask byPlanId = preventionPlanTaskService.queryByPlanId(i.getId());
                PreventionPlanTask preventionPlanTask = new PreventionPlanTask();
                if (byPlanId == null) {
                    preventionPlanTask.setPreventionPlanId(i.getId());
                    preventionPlanTask.setPlanUserIds(i.getUserNames());
                    preventionPlanTask.setPlanUserIds(i.getUserIds());
                    preventionPlanTask.setCheckState("未排查");
                    preventionPlanTask.setTaskStartTime(now.toString());
                    preventionPlanTask.setTaskEndTime(offset.toString());
                    preventionPlanTaskService.insert(preventionPlanTask);
                } else {
                    String taskEndTime = byPlanId.getTaskEndTime();
                    DateTime dateTime = DateUtil.parse(taskEndTime, "yyyy-MM-dd HH:mm:ss");
                    Date date1 = dateTime.toJdkDate();
                    boolean after1 = date1.after(now);
                    if (!after1) {
                        preventionPlanTask.setPreventionPlanId(i.getId());
                        preventionPlanTask.setPlanUserIds(i.getUserNames());
                        preventionPlanTask.setPlanUserIds(i.getUserIds());
                        preventionPlanTask.setCheckState("未排查");
                        preventionPlanTask.setTaskStartTime(now.toString());
                        preventionPlanTask.setTaskEndTime(offset.toString());
                        preventionPlanTaskService.insert(preventionPlanTask);
                    }
                }
                if (preventionPlanTask.getId() != null) {
                    // 新增待办
                    List<SysActing> sysActings = new ArrayList<>();
                    // 获取任务详情
                    String planUserIds = preventionPlanTask.getPlanUserIds();
                    String[] ids = planUserIds.split(",");
                    for (String idStr : ids) {
                        Long id = Long.parseLong(idStr);
                        SysUser sysUser = userService.selectUserById(id);
                        String userName = sysUser.getUserName();
                        SysActing sysActing = new SysActing();
                        sysActing.setTitleName("隐患");
                        sysActing.setTitleType("计划任务");
                        sysActing.setTitleInfo(i.getPlanName());
                        sysActing.setExecutorName(userName);
                        sysActing.setExecutorId(id.intValue());
                        sysActing.setSourceId(preventionPlanTask.getId());
                        sysActing.setOriginatorId(i.getCreateId());
                        sysActing.setOriginatorName(i.getCreateUser());
                        sysActings.add(sysActing);
                    }
                    sysActingService.insertBatch(sysActings);
                }
            }
        });
    }

}
