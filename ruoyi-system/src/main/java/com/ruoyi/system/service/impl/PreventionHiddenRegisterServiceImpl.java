package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.PreventionHiddenRegister;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.mapper.PreventionHiddenRegisterMapper;
import com.ruoyi.system.service.PreventionHiddenRegisterService;
import com.ruoyi.system.service.SysActingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 隐患录入记录表(PreventionHiddenRegister)表服务实现类
 *
 * @author makejava
 * @since 2022-11-22 17:20:34
 */
@Service("preventionHiddenRegisterService")
public class PreventionHiddenRegisterServiceImpl implements PreventionHiddenRegisterService {
    @Resource
    private PreventionHiddenRegisterMapper preventionHiddenRegisterDao;

    @Resource
    private SysActingService sysActingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionHiddenRegister queryById(Integer id) {
        return this.preventionHiddenRegisterDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionHiddenRegister 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionHiddenRegister> queryByPage(PreventionHiddenRegister preventionHiddenRegister) {
        return preventionHiddenRegisterDao.queryAllByLimit(preventionHiddenRegister);
    }

    /**
     * 新增数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PreventionHiddenRegister insert(PreventionHiddenRegister preventionHiddenRegister) {
        if (preventionHiddenRegister.getHiddenSource().equals("三录入")) {
            preventionHiddenRegister.setHiddenProgress("待修改");
            this.preventionHiddenRegisterDao.insert(preventionHiddenRegister);
            return preventionHiddenRegister;
        }
        preventionHiddenRegister.setRegisterUserId(SecurityUtils.getUserId().intValue());
        preventionHiddenRegister.setRegisterUserName(SecurityUtils.getUsername());
        this.preventionHiddenRegisterDao.insert(preventionHiddenRegister);
        // 添加审核待办
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("隐患");
        sysActing.setTitleType("审核");
        sysActing.setTitleInfo(preventionHiddenRegister.getHiddenCheckType());
        sysActing.setExecutorName(preventionHiddenRegister.getRectificationUserName());
        sysActing.setExecutorId(preventionHiddenRegister.getRectificationUserId());
        sysActing.setSourceId(preventionHiddenRegister.getId());
        sysActing.setOriginatorId(preventionHiddenRegister.getRegisterUserId());
        sysActing.setOriginatorName(preventionHiddenRegister.getRegisterUserName());
        sysActingService.insert(sysActing);
        return preventionHiddenRegister;
    }

    /**
     * 修改数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionHiddenRegister update(PreventionHiddenRegister preventionHiddenRegister) {
        if (preventionHiddenRegister.getHiddenSource().equals("三录入") && preventionHiddenRegister.getHiddenProgress().equals("待修改")) {
            preventionHiddenRegister.setRegisterUserId(SecurityUtils.getUserId().intValue());
            preventionHiddenRegister.setRegisterUserName(SecurityUtils.getUsername());
            preventionHiddenRegister.setHiddenProgress("待审核");
            // 添加审核待办
            SysActing sysActing = new SysActing();
            sysActing.setTitleName("隐患");
            sysActing.setTitleType("审核");
            sysActing.setTitleInfo(preventionHiddenRegister.getHiddenCheckType());
            sysActing.setExecutorName(preventionHiddenRegister.getRectificationUserName());
            sysActing.setExecutorId(preventionHiddenRegister.getRectificationUserId());
            sysActing.setSourceId(preventionHiddenRegister.getId());
            sysActing.setOriginatorId(preventionHiddenRegister.getRegisterUserId());
            sysActing.setOriginatorName(preventionHiddenRegister.getRegisterUserName());
            sysActingService.insert(sysActing);
        }
        this.preventionHiddenRegisterDao.update(preventionHiddenRegister);
        return this.queryById(preventionHiddenRegister.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionHiddenRegisterDao.deleteById(id) > 0;
    }

    @Override
    public Map<String, List<Map<String, Object>>> countHidden(Integer deptId) {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        // 根据进度统计隐患数量
        List<Map<String, Object>> progressMap = preventionHiddenRegisterDao.countProgress(deptId);
        map.put("hidden",progressMap);
        // 查询排查任务
        List<Map<String, Object>> taskMap = preventionHiddenRegisterDao.countTask(deptId);
        map.put("task",taskMap);
        return map;
    }

    /**
     *  流转
     * @param preventionHiddenRegister
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRectificationUserName(PreventionHiddenRegister preventionHiddenRegister) {
        if (!preventionHiddenRegister.getHiddenProgress().equals("待整改")) {
            throw new ServiceException("当前状态不是待整改状态，无法流转",500);
        }
        preventionHiddenRegisterDao.update(preventionHiddenRegister);
        preventionHiddenRegister = preventionHiddenRegisterDao.queryById(preventionHiddenRegister.getId());
        // 修改待办执行人
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("隐患");
        sysActing.setTitleType("整改");
        sysActing.setSourceId(preventionHiddenRegister.getId());
        sysActing.setExecutorName(preventionHiddenRegister.getRectificationUserName());
        sysActing.setExecutorId(preventionHiddenRegister.getRegisterUserId());
        sysActingService.updateExecutorUser(sysActing);
    }

    /**
     *  隐患整改完成---添加复核待办提醒
     * @param preventionHiddenRegister
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRectification(PreventionHiddenRegister preventionHiddenRegister) {
        preventionHiddenRegister.setHiddenCompleteTime(DateUtil.now());
        preventionHiddenRegister.setHiddenProgress("待复核");
        preventionHiddenRegisterDao.update(preventionHiddenRegister);
        preventionHiddenRegister = preventionHiddenRegisterDao.queryById(preventionHiddenRegister.getId());
        // 完成整改待办
        sysActingService.completeActing("隐患","整改",preventionHiddenRegister.getId());
        // 创建复核待办
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("隐患");
        sysActing.setTitleType("复核");
        sysActing.setTitleInfo(preventionHiddenRegister.getHiddenCheckType());
        sysActing.setExecutorName(preventionHiddenRegister.getReviewUserName());
        sysActing.setExecutorId(preventionHiddenRegister.getReviewUserId());
        sysActing.setSourceId(preventionHiddenRegister.getId());
        sysActing.setOriginatorId(preventionHiddenRegister.getRegisterUserId());
        sysActing.setOriginatorName(preventionHiddenRegister.getRegisterUserName());
        sysActingService.insert(sysActing);
    }

    /**
     *  隐患复核完成 --- 完成隐患待办任务
     * @param preventionHiddenRegister
     */
    @Override
    public void updateReview(PreventionHiddenRegister preventionHiddenRegister) {
        preventionHiddenRegister.setHiddenReviewTime(DateUtil.now());
        preventionHiddenRegister.setHiddenProgress("完成");
        preventionHiddenRegisterDao.update(preventionHiddenRegister);
        sysActingService.completeActing("隐患","复核",preventionHiddenRegister.getId());
    }

    /**
     *  审核
     * @param preventionHiddenRegister
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateHiddenState(PreventionHiddenRegister preventionHiddenRegister) {
        if (preventionHiddenRegister.getHiddenState() == 1) {
            preventionHiddenRegister.setHiddenProgress("待整改");
        } else {
            preventionHiddenRegister.setHiddenProgress("已驳回");
        }
        preventionHiddenRegisterDao.update(preventionHiddenRegister);
        preventionHiddenRegister = preventionHiddenRegisterDao.queryById(preventionHiddenRegister.getId());
        // 审核完成-完成待办任务-待整改状态-创建整改待办提醒
        sysActingService.completeActing("隐患","审核",preventionHiddenRegister.getId());
        if (preventionHiddenRegister.getHiddenState() == 1) {
            SysActing sysActing = new SysActing();
            sysActing.setTitleName("隐患");
            sysActing.setTitleType("整改");
            sysActing.setTitleInfo(preventionHiddenRegister.getHiddenCheckType());
            sysActing.setExecutorName(preventionHiddenRegister.getRectificationUserName());
            sysActing.setExecutorId(preventionHiddenRegister.getRectificationUserId());
            sysActing.setSourceId(preventionHiddenRegister.getId());
            sysActing.setOriginatorId(preventionHiddenRegister.getRegisterUserId());
            sysActing.setOriginatorName(preventionHiddenRegister.getRegisterUserName());
            sysActingService.insert(sysActing);
        }
    }
}
