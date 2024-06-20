package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.vo.PreventionCheckTaskConfigVO;
import com.ruoyi.system.domain.vo.PreventionCheckTaskVO;
import com.ruoyi.system.entity.PreventionCheckTask;
import com.ruoyi.system.entity.PreventionCheckTaskConfig;
import com.ruoyi.system.entity.PreventionSecurityRisk;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.mapper.PreventionCheckTaskConfigMapper;
import com.ruoyi.system.service.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 双重预防-排查任务配置表(PreventionCheckTaskConfig)表服务实现类
 *
 * @author makejava
 * @since 2022-11-18 13:58:19
 */
@Service("preventionCheckTaskConfigService")
public class PreventionCheckTaskConfigServiceImpl implements PreventionCheckTaskConfigService {
    @Resource
    private PreventionCheckTaskConfigMapper preventionCheckTaskConfigDao;

    @Resource
    private PreventionCheckTaskService preventionCheckTaskService;

    @Resource
    private PreventionSecurityRiskService preventionSecurityRiskService;

    @Resource
    private SysActingService sysActingService;

    @Resource
    private ISysUserService userService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionCheckTaskConfig queryById(Integer id) {
        return this.preventionCheckTaskConfigDao.queryById(id);
    }

    /**
     * 分页查询
     * @return 查询结果
     */
    @Override
    public List<PreventionCheckTaskConfigVO> queryByPage(PreventionCheckTaskConfigVO preventionCheckTaskConfigVO) {
        List<PreventionCheckTaskConfigVO> preventionCheckTaskConfigVOS = preventionCheckTaskConfigDao.queryAllByLimit(preventionCheckTaskConfigVO);
        preventionCheckTaskConfigVOS.forEach(i -> {
            Integer id = i.getId();
            PreventionCheckTask preventionCheckTask = preventionCheckTaskService.queryByConfigId(id);
            if (preventionCheckTask != null) {
                i.setTaskState(preventionCheckTask.getTaskState());
            } else {
                i.setTaskState("未排查");
            }
        });
        return preventionCheckTaskConfigVOS;
    }

    /**
     * 新增数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionCheckTaskConfig insert(PreventionCheckTaskConfig preventionCheckTaskConfig) {
        this.preventionCheckTaskConfigDao.insert(preventionCheckTaskConfig);
        return preventionCheckTaskConfig;
    }

    /**
     * 修改数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionCheckTaskConfig update(PreventionCheckTaskConfig preventionCheckTaskConfig) {
        this.preventionCheckTaskConfigDao.update(preventionCheckTaskConfig);
        return this.queryById(preventionCheckTaskConfig.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionCheckTaskConfigDao.deleteById(id) > 0;
    }

    @Override
    public void insertBatch(List<PreventionCheckTaskConfig> preventionCheckTaskConfigList) {
        preventionCheckTaskConfigDao.insertBatch(preventionCheckTaskConfigList);
    }


    //TODO
//    @Scheduled(cron = "0/60 * * * * ?")
    public void scheduledConfig() {
        List<PreventionCheckTaskConfig> preventionCheckTaskConfigs = preventionCheckTaskConfigDao.queryAll();
        preventionCheckTaskConfigs.forEach(i -> {
            // 是否配置
            Integer whetherConfig = i.getWhetherConfig();
            // 是否发布
            Integer whetherRelease = i.getWhetherRelease();
            if (whetherConfig == 0 && whetherRelease == 0) {
                String taskStartTime = i.getTaskStartTime();
                DateTime parse = DateUtil.parse(taskStartTime, "yyyy-MM-dd HH:mm:ss");
                Date date = parse.toJdkDate();
                DateTime now = DateUtil.date();
                boolean after = date.after(now);
                if (!after) {
                    PreventionSecurityRisk preventionSecurityRisk = preventionSecurityRiskService.queryById(i.getSecurityRiskId());
                    String cycle = preventionSecurityRisk.getCycle();
                    // 小时 月 天 年
                    String unit = preventionSecurityRisk.getUnit();
                    DateTime offset = DateUtil.date().offset(strUnitDate(unit), Integer.valueOf(cycle));
                    PreventionCheckTask byConfigId = preventionCheckTaskService.queryByConfigId(i.getId());
                    PreventionCheckTask preventionCheckTask = new PreventionCheckTask();
                    if (byConfigId == null) {
                        preventionCheckTask.setCheckTaskConfigId(i.getId());
                        preventionCheckTask.setStartTime(now.toString());
                        preventionCheckTask.setEndTime(offset.toString());
                        preventionCheckTask.setCheckUserName(i.getCheckUserNames());
                        preventionCheckTask.setTaskState("待排查");
                        preventionCheckTaskService.insert(preventionCheckTask);
                    } else {
                        String endTime = byConfigId.getEndTime();
                        DateTime dateTime = DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
                        Date date1 = dateTime.toJdkDate();
                        boolean after1 = date1.after(now);
                        if (!after1) {
                            if (byConfigId.getTaskState().equals("待排查")) {
                                byConfigId.setTaskState("已超时");
                                preventionCheckTaskService.update(byConfigId);
                            }
                            preventionCheckTask.setCheckTaskConfigId(i.getId());
                            preventionCheckTask.setStartTime(now.toString());
                            preventionCheckTask.setEndTime(offset.toString());
                            preventionCheckTask.setCheckUserName(i.getCheckUserNames());
                            preventionCheckTask.setTaskState("待排查");
                            preventionCheckTaskService.insert(preventionCheckTask);
                        }
                    }
                    if (preventionCheckTask != null && preventionCheckTask.getId() != null) {
                        // 新增待办
                        List<SysActing> sysActings = new ArrayList<>();
                        // 获取任务详情
                        Integer taskId = preventionCheckTask.getId();
                        PreventionCheckTaskVO preventionCheckTaskVO = preventionCheckTaskService.getTaskInfoById(taskId);
                        String checkUserIds = i.getCheckUserIds();
                        String[] ids = checkUserIds.split(",");
                        for (String idStr : ids) {
                            Long id = Long.parseLong(idStr);
                            SysUser sysUser = userService.selectUserById(id);
                            String userName = sysUser.getUserName();
                            SysActing sysActing = new SysActing();
                            sysActing.setTitleName("隐患");
                            sysActing.setTitleType("排查任务");
                            sysActing.setTitleInfo(preventionCheckTaskVO.getDeviceName());
                            sysActing.setExecutorName(userName);
                            sysActing.setExecutorId(id.intValue());
                            sysActing.setSourceId(taskId);
                            sysActing.setOriginatorId(preventionCheckTaskVO.getUserId());
                            sysActing.setOriginatorName(preventionCheckTaskVO.getUserName());
                            sysActings.add(sysActing);
                        }
                        sysActingService.insertBatch(sysActings);
                    }
                }
            }
        });
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
}
