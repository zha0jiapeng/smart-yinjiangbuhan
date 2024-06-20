package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.entity.QualityTask;
import com.ruoyi.system.entity.QualityTaskFiles;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.entity.SysRemind;
import com.ruoyi.system.mapper.QualityTaskMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 质量任务(QualityTask)表服务实现类
 *
 * @author makejava
 * @since 2022-12-26 14:01:34
 */
@Service("qualityTaskService")
public class QualityTaskServiceImpl implements QualityTaskService {
    @Resource
    private QualityTaskMapper qualityTaskMapper;

    @Resource
    private SysActingService sysActingService;

    @Resource
    private SysRemindService sysRemindService;

    @Resource
    private ISysUserService sysUserService;

    @Autowired
    private ProjectMppService projectMppService;

    @Autowired
    private QualityTaskFilesService qualityTaskFilesService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QualityTask queryById(Integer id) {
        return this.qualityTaskMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qualityTask 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QualityTask> queryByPage(QualityTask qualityTask) {
        List<QualityTask> qualityTasks = this.qualityTaskMapper.queryAllByLimit(qualityTask);
        if (!CollectionUtils.isEmpty(qualityTasks)) {
            qualityTasks.forEach(val -> {
                QueryWrapper<ProjectMpp> wrapper = new QueryWrapper<>();
                wrapper.eq("proj_id", val.getProjId());
                ProjectMpp projectMpp = projectMppService.getOne(wrapper);
                if (null != projectMpp) {
                    val.setProjName(projectMpp.getTaskName());
                }

                List<QualityTaskFiles> sysHiddenDangerFiles = new ArrayList<>();
                String fileId = val.getFileId();
                if (!StringUtils.isBlank(fileId)) {
                    fileId = fileId.substring(0, fileId.length() - 1);
                    if (fileId.contains(",")) {
                        String[] split = fileId.split(",");
                        QueryWrapper<QualityTaskFiles> filesQueryWrapper = new QueryWrapper<>();
                        filesQueryWrapper.in("id", Arrays.asList(split));
                        sysHiddenDangerFiles.addAll(qualityTaskFilesService.list(filesQueryWrapper));
                    } else {
                        QueryWrapper<QualityTaskFiles> filesQueryWrapper = new QueryWrapper<>();
                        filesQueryWrapper.eq("id", fileId);
                        QualityTaskFiles one = qualityTaskFilesService.getOne(filesQueryWrapper);
                        sysHiddenDangerFiles.add(one);
                    }
                }

                val.setQualityTaskFiles(sysHiddenDangerFiles);
            });
        }

        return qualityTasks;
    }

    /**
     * 新增数据
     *
     * @param qualityTask 实例对象
     * @return 实例对象
     */
    @Override
    public QualityTask insert(QualityTask qualityTask) {
        this.qualityTaskMapper.insert(qualityTask);
        // 生成待办发送到执行人
        List<SysActing> sysActings = new ArrayList<>();
        String executorUserIds = qualityTask.getExecutorUserIds();
        String[] ids = executorUserIds.split(",");
        for (String idStr : ids) {
            Long id = Long.parseLong(idStr);
            SysUser sysUser = sysUserService.selectUserById(id);
            String userName = sysUser.getUserName();
            SysActing sysActing = new SysActing();
            sysActing.setTitleName("质量管理");
            sysActing.setTitleType("质检任务");
            sysActing.setTitleInfo(qualityTask.getTaskName());
            sysActing.setExecutorName(userName);
            sysActing.setExecutorId(id.intValue());
            sysActing.setSourceId(qualityTask.getId());
            sysActing.setOriginatorId(qualityTask.getCreateUserId());
            sysActing.setOriginatorName(qualityTask.getCreateUserName());
            sysActings.add(sysActing);
            sysRemindService.insert(SysRemind.TYPE_PROMPT, "您有一条质量检测任务", id.intValue());
        }
        sysActingService.insertBatch(sysActings);
        return qualityTask;
    }

    /**
     * 修改数据
     *
     * @param qualityTask 实例对象
     * @return 实例对象
     */
    @Override
    public QualityTask update(QualityTask qualityTask) {
        this.qualityTaskMapper.update(qualityTask);
        return this.queryById(qualityTask.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qualityTaskMapper.deleteById(id) > 0;
    }

    @Override
    public String complete(QualityTask qualityTask) {
        this.qualityTaskMapper.update(qualityTask);
        sysActingService.completeActing("质量管理", "质检任务", qualityTask.getId());
        return "完成";
    }

    @Override
    public List<QualityTask> queryByPageAndActing(QualityTask qualityTask) {
        List<QualityTask> list = new ArrayList<>();
        SysActing sysActing = new SysActing();
        sysActing.setTitleName("质量管理");
        sysActing.setState(0);
        sysActing.setExecutorId(SecurityUtils.getUserId().intValue());
        List<SysActing> sysActings = sysActingService.queryByPage(sysActing);
        if (CollUtil.isNotEmpty(sysActings)) {
            for (SysActing acting : sysActings) {
                if (!acting.getTitleType().equals("质检任务")) {
                    continue;
                }
                Integer sourceId = acting.getSourceId();
                QualityTask qualityTask1 = qualityTaskMapper.queryById(sourceId);
                list.add(qualityTask1);
            }
        }
        return list;
    }


}
