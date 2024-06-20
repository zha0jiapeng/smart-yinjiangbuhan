package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.QualityAutomaticConfig;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.entity.QualityProblemHandle;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.entity.SysRemind;
import com.ruoyi.system.mapper.QualityProblemHandleMapper;
import com.ruoyi.system.mapper.QualityProblemMapper;
import com.ruoyi.system.service.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (QualityProblem)表服务实现类
 *
 * @author makejava
 * @since 2022-12-26 16:03:29
 */
@Service("qualityProblemService")
public class QualityProblemServiceImpl implements QualityProblemService {
    @Resource
    private SysRemindService sysRemindService;

    @Resource
    private SysActingService sysActingService;

    @Resource
    private QualityTaskService qualityTaskService;

    @Resource
    private QualityProblemMapper qualityProblemMapper;

    @Resource
    private QualityProblemHandleMapper qualityProblemHandleMapper;

    @Resource
    private QualityAutomaticConfigService qualityAutomaticConfigService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QualityProblem queryById(Integer id) {
        QualityProblem qualityProblem = this.qualityProblemMapper.queryById(id);
        if (null == qualityProblem) {
            return new QualityProblem();
        }
        if (null != qualityProblem.getQualityTaskId()) {
            qualityProblem.setQualityTask(qualityTaskService.queryById(qualityProblem.getQualityTaskId()));
        }
        if (null != qualityProblem.getId()) {
            qualityProblem.setHandles(qualityProblemHandleMapper.queryByProblemId(qualityProblem.getId()));
        }
        return qualityProblem;
    }

    /**
     * 分页查询
     *
     * @param qualityProblem 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QualityProblem> queryByPage(QualityProblem qualityProblem) {
        List<QualityProblem> qualityProblems = this.qualityProblemMapper.queryAllByLimit(qualityProblem);

        if (CollectionUtils.isEmpty(qualityProblems)) {
            return new ArrayList<>();
        }

        List<Integer> problemTypes = qualityProblems.stream().map(QualityProblem::getProblemType).collect(Collectors.toList());
        Map<Integer, QualityAutomaticConfig> configMap = null;
        if (!CollectionUtils.isEmpty(problemTypes)) {
            QueryWrapper<QualityAutomaticConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", problemTypes);
            List<QualityAutomaticConfig> qualityAutomaticConfigs = qualityAutomaticConfigService.list(queryWrapper);
            if (!CollectionUtils.isEmpty(qualityAutomaticConfigs)) {
                configMap = qualityAutomaticConfigs.stream().collect(Collectors.toMap(QualityAutomaticConfig::getId, el -> el, (e1, e2) -> e1));
            }
        }

        for (QualityProblem problem : qualityProblems) {
            if (MapUtils.isNotEmpty(configMap)) {
                QualityAutomaticConfig qualityAutomaticConfig = configMap.get(problem.getProblemType());
                if (null != qualityAutomaticConfig) {
                    problem.setProblemTypeBase(qualityAutomaticConfig.getQualityConfigDetails());
                }
            }

            problemTypes.add(problem.getProblemType());
            problem.setQualityTask(qualityTaskService.queryById(problem.getQualityTaskId()));
            problem.setHandles(qualityProblemHandleMapper.queryByProblemId(problem.getId()));
        }

        return qualityProblems;
    }

    /**
     * 新增数据
     *
     * @param qualityProblem 实例对象
     * @return 实例对象
     */
    @Override
    public QualityProblem insert(QualityProblem qualityProblem) {
        qualityProblem.setCreateUserName(SecurityUtils.getUsername());
        qualityProblem.setCreateUserId(SecurityUtils.getUserId().intValue());
        qualityProblem.setProblemProgress("问题审核");
        this.qualityProblemMapper.insert(qualityProblem);
        List<SysActing> sysActings = new ArrayList<>();
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("质量管理");
        sysActing.setTitleType("质检问题审核");
        sysActing.setTitleInfo(qualityProblem.getProblemName());
        sysActing.setExecutorName(qualityProblem.getProblemAuditUserName());
        sysActing.setExecutorId(qualityProblem.getProblemAuditUserId());
        sysActing.setSourceId(qualityProblem.getId());
        sysActing.setOriginatorId(qualityProblem.getCreateUserId());
        sysActing.setOriginatorName(qualityProblem.getCreateUserName());
        sysActings.add(sysActing);
        sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条质检问题待审核，请尽快审核", qualityProblem.getProblemAuditUserId());
        if (qualityProblem.getProblemCopyUserId() != null) {
            sysRemindService.insert(SysRemind.TYPE_PROMPT, "新增加了一条质检问题", qualityProblem.getProblemCopyUserId());
        }
        // 添加待办提醒
        sysActingService.insertBatch(sysActings);

        return qualityProblem;
    }

    /**
     * 修改数据
     *
     * @param qualityProblem 实例对象
     * @return 实例对象
     */
    @Override
    public QualityProblem update(QualityProblem qualityProblem) {
        this.qualityProblemMapper.update(qualityProblem);
        return this.queryById(qualityProblem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qualityProblemMapper.deleteById(id) > 0;
    }

    /**
     * 责任人回复处理意见
     *
     * @param qualityProblem
     * @return
     */
    @Override
    public String updateOpinion(QualityProblem qualityProblem) {
        QualityProblem byId = qualityProblemMapper.queryById(qualityProblem.getId());
        String handleFileName = qualityProblem.getHandleFileName();
        String handleFileUrl = qualityProblem.getHandleFileUrl();
        String handleImage = qualityProblem.getHandleImage();
        String handleResult = qualityProblem.getHandleResult();
        QualityProblemHandle qualityProblemHandle = new QualityProblemHandle();
        qualityProblemHandle.setHandleFileName(handleFileName);
        qualityProblemHandle.setHandleFileUrl(handleFileUrl);
        qualityProblemHandle.setHandleImage(handleImage);
        qualityProblemHandle.setHandleResult(handleResult);
        qualityProblemHandle.setFrequency(1);
        qualityProblemHandle.setProblemType("问题审核");
        qualityProblemHandle.setProblemId(qualityProblem.getId());
        qualityProblemHandle.setPass(qualityProblem.getPass());
        qualityProblemHandle.setExecUserId(byId.getProblemAuditUserId());
        qualityProblemHandle.setExecUserName(byId.getProblemAuditUserName());
        qualityProblemHandleMapper.insert(qualityProblemHandle);
        if (qualityProblem.getPass() == 1) {
            qualityProblem.setHandleOpinion("未通过审核");
            qualityProblem.setProblemAuditTime(DateUtil.now());
            qualityProblem.setProblemProgress("已关闭");
            this.qualityProblemMapper.update(qualityProblem);
            sysActingService.completeActing("质量管理", "质检问题审核", qualityProblem.getId());
            return "完成";
        }
        qualityProblem.setProblemProgress("待整改");
        qualityProblem.setProblemAuditTime(DateUtil.now());
        this.qualityProblemMapper.update(qualityProblem);
        sysActingService.completeActing("质量管理", "质检问题审核", qualityProblem.getId());
        // 添加整改待办
        List<SysActing> sysActings = new ArrayList<>();
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("质量管理");
        sysActing.setTitleType("质检问题整改");
        sysActing.setTitleInfo(byId.getProblemName());
        sysActing.setExecutorName(byId.getProblemExecuteUserName());
        sysActing.setExecutorId(byId.getProblemExecuteUserId());
        sysActing.setSourceId(byId.getId());
        sysActing.setOriginatorId(byId.getCreateUserId());
        sysActing.setOriginatorName(byId.getCreateUserName());
        sysActings.add(sysActing);
        sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条质检问题整改待整改,请尽快整改", byId.getProblemExecuteUserId());
        sysActingService.insertBatch(sysActings);
        return "完成";
    }

    /**
     * 执行人执行结果
     *
     * @param qualityProblem
     * @return
     */
    @Override
    public String updateResult(QualityProblem qualityProblem) {
        QualityProblem byId = qualityProblemMapper.queryById(qualityProblem.getId());

        String handleFileName = qualityProblem.getHandleFileName();
        String handleFileUrl = qualityProblem.getHandleFileUrl();
        String handleImage = qualityProblem.getHandleImage();
        String handleResult = qualityProblem.getHandleResult();
        QualityProblemHandle qualityProblemHandle = new QualityProblemHandle();
        qualityProblemHandle.setProblemId(qualityProblem.getId());
        List<QualityProblemHandle> qualityProblemHandles = qualityProblemHandleMapper.queryAllByLimit(qualityProblemHandle);
        qualityProblemHandle.setHandleFileName(handleFileName);
        qualityProblemHandle.setHandleFileUrl(handleFileUrl);
        qualityProblemHandle.setHandleImage(handleImage);
        qualityProblemHandle.setHandleResult(handleResult);
        qualityProblemHandle.setFrequency(qualityProblemHandles.size() + 1);
        qualityProblemHandle.setProblemType("问题整改");
        qualityProblemHandle.setPass(qualityProblem.getPass());
        qualityProblemHandle.setExecUserId(byId.getProblemExecuteUserId());
        qualityProblemHandle.setExecUserName(byId.getProblemExecuteUserName());
        qualityProblemHandleMapper.insert(qualityProblemHandle);

        qualityProblem.setProblemProgress("整改审核");
        qualityProblem.setProblemExecuteTime(DateUtil.now());
        this.qualityProblemMapper.update(qualityProblem);
        sysActingService.completeActing("质量管理", "质检问题整改", qualityProblem.getId());
        // 添加确认待办
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("质量管理");
        sysActing.setTitleType("质检整改问题复核");
        sysActing.setTitleInfo(byId.getProblemName());
        sysActing.setExecutorName(byId.getProblemReviewUserName());
        sysActing.setExecutorId(byId.getProblemReviewUserId());
        sysActing.setSourceId(qualityProblem.getId());
        sysActing.setOriginatorId(byId.getCreateUserId());
        sysActing.setOriginatorName(byId.getCreateUserName());
        sysActingService.insert(sysActing);
        sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条已整改的质检问题待复核，请尽快复核", byId.getProblemReviewUserId());
        return "完成";
    }

    /**
     * 发起人确认结果
     *
     * @return
     */
    @Override
    public String updateProgress(QualityProblem qualityProblem) {
        QualityProblem byId = qualityProblemMapper.queryById(qualityProblem.getId());

        String handleFileName = qualityProblem.getHandleFileName();
        String handleFileUrl = qualityProblem.getHandleFileUrl();
        String handleImage = qualityProblem.getHandleImage();
        String handleResult = qualityProblem.getHandleResult();
        QualityProblemHandle qualityProblemHandle = new QualityProblemHandle();
        qualityProblemHandle.setProblemId(qualityProblem.getId());
        List<QualityProblemHandle> qualityProblemHandles = qualityProblemHandleMapper.queryAllByLimit(qualityProblemHandle);
        qualityProblemHandle.setHandleFileName(handleFileName);
        qualityProblemHandle.setHandleFileUrl(handleFileUrl);
        qualityProblemHandle.setHandleImage(handleImage);
        qualityProblemHandle.setHandleResult(handleResult);
        qualityProblemHandle.setFrequency(qualityProblemHandles.size() + 1);
        qualityProblemHandle.setProblemType("问题复核");
        qualityProblemHandle.setPass(qualityProblem.getPass());
        qualityProblemHandle.setExecUserId(byId.getProblemReviewUserId());
        qualityProblemHandle.setExecUserName(byId.getProblemReviewUserName());
        qualityProblemHandleMapper.insert(qualityProblemHandle);

        if (qualityProblem.getPass() == 1) {
            qualityProblem.setProblemReviewTime(DateUtil.now());
            qualityProblem.setProblemProgress("待整改");
            this.qualityProblemMapper.update(qualityProblem);
            sysActingService.completeActing("质量管理", "质检整改问题复核", qualityProblem.getId());
            // 添加整改待办
            List<SysActing> sysActings = new ArrayList<>();
            SysActing sysActing = new SysActing();
            sysActing.setTitleName("质量管理");
            sysActing.setTitleType("质检问题整改");
            sysActing.setTitleInfo(byId.getProblemName());
            sysActing.setExecutorName(byId.getProblemExecuteUserName());
            sysActing.setExecutorId(byId.getProblemExecuteUserId());
            sysActing.setSourceId(byId.getId());
            sysActing.setOriginatorId(byId.getCreateUserId());
            sysActing.setOriginatorName(byId.getCreateUserName());
            sysActings.add(sysActing);
            sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条质检问题整改待整改,请尽快整改", byId.getProblemExecuteUserId());
            sysActingService.insertBatch(sysActings);
            sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条已整改的质检问题未通过复核，已驳回请重新整改", byId.getProblemExecuteUserId());
            return "完成";
        }
        qualityProblem.setProblemProgress("已关闭");
        qualityProblem.setProblemReviewTime(DateUtil.now());
        this.qualityProblemMapper.update(qualityProblem);
        sysActingService.completeActing("质量管理", "质检整改问题复核", qualityProblem.getId());
        return "完成";
    }

    @Override
    public Map<String, Object> countAll() {
        int countTotal = qualityProblemMapper.countTotal();
        DateTime date = DateUtil.date();
        //获取当前月的开始时间结束时间
        DateTime start = DateUtil.beginOfMonth(date);
        DateTime end = DateUtil.endOfMonth(date);
        int countCurrentMonth = qualityProblemMapper.countByMonth(start.toString(), end.toString());
        // 获取上个月的开始时间结束时间
        DateTime lastMonth = DateUtil.lastMonth();
        DateTime lastMonthStart = DateUtil.beginOfMonth(lastMonth);
        DateTime lastMonthEnd = DateUtil.endOfMonth(lastMonth);
        int countLastMonth = qualityProblemMapper.countByMonth(lastMonthStart.toString(), lastMonthEnd.toString());
        // 计算上个月与这个月的百分比
        double percentage;
        if (countCurrentMonth - countLastMonth == 0) {
            percentage = 0;
        } else if (countCurrentMonth == 0 || countLastMonth == 0) {
            percentage = (countCurrentMonth - countLastMonth) * 100;
        } else {
            double c = countCurrentMonth - countLastMonth;
            percentage = c / countLastMonth * 100;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("total", countTotal);
        map.put("currentMonth", countCurrentMonth);
        map.put("percentage", percentage);
        return map;
    }

    @Override
    public List<QualityProblem> queryByPageAndActing(QualityProblem qualityProblem) {
        List<QualityProblem> list = new ArrayList<>();
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("质量管理");
        sysActing.setState(0);
        sysActing.setExecutorId(SecurityUtils.getUserId().intValue());
        List<SysActing> sysActings = sysActingService.queryByPage(sysActing);
        if (CollUtil.isNotEmpty(sysActings)) {
            for (SysActing acting : sysActings) {
                if (acting.getTitleType().equals("质检任务")) {
                    continue;
                }
                Integer sourceId = acting.getSourceId();
                QualityProblem qualityProblem1 = qualityProblemMapper.queryById(sourceId);
                list.add(qualityProblem1);
            }
        }
        return list;
    }

}
