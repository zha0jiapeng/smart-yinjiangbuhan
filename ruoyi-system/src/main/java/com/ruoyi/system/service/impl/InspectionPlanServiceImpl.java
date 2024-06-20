package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.InspectionPlanMapper;
import com.ruoyi.system.service.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (InspectionPlan)表服务实现类
 *
 * @author makejava
 * @since 2022-11-25 17:09:56
 */
@Service("inspectionPlanService")
public class InspectionPlanServiceImpl implements InspectionPlanService {
    @Resource
    private InspectionPlanMapper inspectionPlanDao;

    @Resource
    private InspectionPlanTaskService inspectionPlanTaskService;

    @Resource
    private InspectionPlanTaskDetailsService inspectionPlanTaskDetailsService;

    @Resource
    private InspectionPointService inspectionPointService;

    @Resource
    private ISysUserService userService;

    @Resource
    private SysActingService sysActingService;

    @Resource
    private SysRemindService sysRemindService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionPlan queryById(Integer id) {
        return this.inspectionPlanDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionPlan 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionPlan> queryByPage(InspectionPlan inspectionPlan) {
        return inspectionPlanDao.queryAllByLimit(inspectionPlan);
    }

    /**
     * 新增数据
     *
     * @param inspectionPlan 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlan insert(InspectionPlan inspectionPlan) {
        this.inspectionPlanDao.insert(inspectionPlan);
        return inspectionPlan;
    }

    /**
     * 修改数据
     *
     * @param inspectionPlan 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionPlan update(InspectionPlan inspectionPlan) {
        this.inspectionPlanDao.update(inspectionPlan);
        return this.queryById(inspectionPlan.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionPlanDao.deleteById(id) > 0;
    }

    //TODO
//    @Scheduled(cron = "0/60 * * * * ?")
    public void createTask() {
        InspectionPlan inspectionPlan = new InspectionPlan();
        inspectionPlan.setPlanEnable(0);
        List<InspectionPlan> inspectionPlans = inspectionPlanDao.queryAllByLimit(inspectionPlan);
        if (CollUtil.isNotEmpty(inspectionPlans)) {
            inspectionPlans.forEach(i -> {
                String planStartTime = i.getPlanStartTime();
                DateTime parse = DateUtil.parse(planStartTime, "yyyy-MM-dd HH:mm:ss");
                Date date = parse.toJdkDate();
                DateTime now = DateUtil.date();
                boolean after = date.after(now);
                String planEndTime = i.getPlanEndTime();
                DateTime endTime = DateUtil.parse(planEndTime, "yyyy-MM-dd HH:mm:ss");
                Date end = endTime.toJdkDate();
                boolean endAfter = end.after(now);
                if (!after && endAfter) {
                    String cycle = i.getCycle();
                    String unit = i.getUnit();
                    DateTime offset = DateUtil.date().offset(strUnitDate(unit), Integer.valueOf(cycle));
                    InspectionPlanTask byPlanId = inspectionPlanTaskService.queryByPlanId(i.getId());
                    InspectionPlanTask inspectionPlanTask = new InspectionPlanTask();
                    if (byPlanId == null) {
                        inspectionPlanTask.setPlanId(i.getId());
                        inspectionPlanTask.setPlanUserNames(i.getPlanUserNames());
                        inspectionPlanTask.setPlanUserIds(i.getPlanUserIds());
                        inspectionPlanTask.setRouteId(i.getRouteId());
                        inspectionPlanTask.setRouteName(i.getRouteName());
                        inspectionPlanTask.setStartTime(now.toString());
                        inspectionPlanTask.setEndTime(offset.toString());
                        inspectionPlanTask.setPlanTaskState("未完成");
                        inspectionPlanTaskService.insert(inspectionPlanTask);
                        createTaskDetails(inspectionPlanTask);
                    } else {
                        String byPlanIdEndTime = byPlanId.getEndTime();
                        DateTime dateTime = DateUtil.parse(byPlanIdEndTime, "yyyy-MM-dd HH:mm:ss");
                        Date date1 = dateTime.toJdkDate();
                        boolean after1 = date1.after(now);
                        if (!after1) {
                            inspectionPlanTask.setPlanId(i.getId());
                            inspectionPlanTask.setPlanUserNames(i.getPlanUserNames());
                            inspectionPlanTask.setPlanUserIds(i.getPlanUserIds());
                            inspectionPlanTask.setRouteId(i.getRouteId());
                            inspectionPlanTask.setRouteName(i.getRouteName());
                            inspectionPlanTask.setStartTime(now.toString());
                            inspectionPlanTask.setEndTime(offset.toString());
                            inspectionPlanTask.setPlanTaskState("未完成");
                            inspectionPlanTaskService.insert(inspectionPlanTask);
                            createTaskDetails(inspectionPlanTask);
                        }
                    }
                    if (inspectionPlanTask.getId() != null) {
                        // 新增待办
                        List<SysActing> sysActings = new ArrayList<>();
                        // 获取任务详情
                        String planUserIds = inspectionPlanTask.getPlanUserIds();
                        String[] ids = planUserIds.split(",");
                        for (String idStr : ids) {
                            Long id = Long.parseLong(idStr);
                            SysUser sysUser = userService.selectUserById(id);
                            String userName = sysUser.getUserName();
                            SysActing sysActing = new SysActing();
                            sysActing.setTitleName("巡检");
                            sysActing.setTitleType("巡检任务");
                            sysActing.setTitleInfo(i.getPlanName());
                            sysActing.setExecutorName(userName);
                            sysActing.setExecutorId(id.intValue());
                            sysActing.setSourceId(inspectionPlanTask.getId());
                            sysActing.setOriginatorId(1);
                            sysActing.setOriginatorName("admin");
                            sysActings.add(sysActing);
                        }
                        sysActingService.insertBatch(sysActings);
                    }
                }
            });
        }
    }

    public static DateField strUnitDate(String unit) {

        if (unit.equals("天")) {
            return DateField.DAY_OF_MONTH;
        }
        if (unit.equals("月")) {
            return DateField.MONTH;
        }
        if (unit.equals("小时")) {
            return DateField.HOUR_OF_DAY;
        }
        if (unit.equals("年")) {
            return DateField.YEAR;
        }
        return null;
    }

//    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void checkPoint() {
        InspectionPlanTask inspectionPlanTask = new InspectionPlanTask();
        inspectionPlanTask.setPlanTaskState("未完成");
        List<InspectionPlanTask> inspectionPlanTasks = inspectionPlanTaskService.queryByPage(inspectionPlanTask);
        inspectionPlanTasks.forEach(i -> {
            DateTime now = DateUtil.date();
            String planEndTime = i.getEndTime();
            DateTime endTime = DateUtil.parse(planEndTime, "yyyy-MM-dd HH:mm:ss");
            Date end = endTime.toJdkDate();
            boolean endAfter = end.after(now);
            if (!endAfter) {
                InspectionPlan inspectionPlan = inspectionPlanDao.queryById(i.getPlanId());
                InspectionPlanTaskDetails inspectionPlanTaskDetails = new InspectionPlanTaskDetails();
                inspectionPlanTaskDetails.setPlanTaskId(i.getId());
                inspectionPlanTaskDetails.setCheckState("未排查");
                List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList = inspectionPlanTaskDetailsService.queryByPage(inspectionPlanTaskDetails);
                if (CollUtil.isNotEmpty(inspectionPlanTaskDetailsList)) {
                    inspectionPlanTaskDetailsList.forEach(taskDetails -> {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("该");
                        stringBuffer.append(inspectionPlan.getPlanName());
                        stringBuffer.append("巡检计划有漏捡点位: ");
                        stringBuffer.append(taskDetails.getPointName());
                        String planUserIds = i.getPlanUserIds();
                        String[] split = planUserIds.split(",");
                        for (int i1 = 0; i1 < split.length; i1++) {
                            sysRemindService.insert(SysRemind.TYPE_MONITOR,stringBuffer.toString(),Integer.valueOf(split[i1]));
                        }
                    });
                }
            }
        });
    }

    public void createTaskDetails(InspectionPlanTask inspectionPlanTask) {
        Integer routeId = inspectionPlanTask.getRouteId();
        InspectionPoint inspectionPoint = new InspectionPoint();
        inspectionPoint.setRouteId(routeId);
        List<InspectionPoint> inspectionPoints = inspectionPointService.queryByPage(inspectionPoint);
        List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList = new ArrayList<>();
        inspectionPoints.forEach(i -> {
            if (i.getStopCheck() == 0) {
                InspectionPlanTaskDetails inspectionPlanTaskDetails = new InspectionPlanTaskDetails();
                inspectionPlanTaskDetails.setPlanTaskId(inspectionPlanTask.getId());
                inspectionPlanTaskDetails.setPointId(i.getId());
                inspectionPlanTaskDetails.setPointName(i.getPointName());
                inspectionPlanTaskDetails.setCheckState("未排查");
                inspectionPlanTaskDetailsList.add(inspectionPlanTaskDetails);
            }
        });
        inspectionPlanTaskDetailsService.insertBatch(inspectionPlanTaskDetailsList);
    }
}
