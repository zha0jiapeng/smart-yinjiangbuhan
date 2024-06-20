package com.ruoyi.web.controller.basic.view.qualityManager;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.QualityAutomaticConfig;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.entity.QualityTask;
import com.ruoyi.system.entity.QualityTaskDTO;
import com.ruoyi.system.entity.QualityTaskFiles;
import com.ruoyi.system.service.*;
import com.ruoyi.system.service.impl.QualityAutomaticConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 质量任务(QualityTask)表控制层
 *
 * @author makejava
 * @since 2022-12-26 14:01:31
 */
@Slf4j
@RestController
@RequestMapping("qualityTask")
public class QualityTaskController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private RedisCache redisCache;
    @Autowired
    private ProjectMppService projectMppService;
    @Resource
    private QualityTaskService qualityTaskService;
    @Resource
    private QualityProblemService qualityProblemService;
    @Autowired
    private QualityTaskFilesService qualityTaskFilesService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private ISysDeptService iSysDeptService;

    @Autowired
    private QualityAutomaticConfigServiceImpl qualityAutomaticConfigService;

    /**
     * 分页查询
     *
     * @param qualityTask 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(QualityTask qualityTask) {
        startPage();
        List<QualityTask> qualityTasks = this.qualityTaskService.queryByPage(qualityTask);
        return getDataTable(qualityTasks);
    }

    /**
     * 分页查询代我处理
     *
     * @param qualityTask 筛选条件
     * @return 查询结果
     */
    @GetMapping("/listActing")
    public TableDataInfo queryByPageAndActing(QualityTask qualityTask) {
        startPage();
        List<QualityTask> qualityTasks = this.qualityTaskService.queryByPageAndActing(qualityTask);
        return getDataTable(qualityTasks);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.qualityTaskService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qualityTask 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody QualityTask qualityTask) {
        qualityTask.setTaskSource("人工录入");
        String number = new SimpleDateFormat("yyMMdd").format(new Date());
        if (redisCache.hasKey(number)) {
            Object cacheObject = redisCache.getCacheObject(number);
            int no = Integer.parseInt((String) cacheObject);
            int newEquipment = ++no;
            redisCache.setCacheObject(number, String.format("%03d", newEquipment));
            qualityTask.setTaskNumberTwo(String.format(number + "%03d", newEquipment));
        } else {
            redisCache.setCacheObject(number, "001");
            number = number + "001";
            qualityTask.setTaskNumberTwo(number);
        }
        return AjaxResult.success(this.qualityTaskService.insert(qualityTask));
    }

    /**
     * 编辑数据
     *
     * @param qualityTask 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody QualityTask qualityTask) {
        return AjaxResult.success(this.qualityTaskService.update(qualityTask));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.qualityTaskService.deleteById(id));
    }

    /**
     * 完成任务
     *
     * @param qualityTask 实体
     * @return 编辑结果
     */
    @PostMapping("/complete")
    public AjaxResult complete(@RequestBody QualityTask qualityTask) {
        return AjaxResult.success(this.qualityTaskService.complete(qualityTask));
    }

    /**
     * 验收
     *
     * @param qualityTask 实体
     * @return 编辑结果
     */
    @PostMapping("/checkQuality")
    public AjaxResult checkQuality(@RequestBody QualityTask qualityTask) {
        QualityProblem qualityProblem = new QualityProblem();
        qualityProblem.setQualityTaskId(qualityTask.getId());
        List<QualityProblem> qualityProblems = qualityProblemService.queryByPage(qualityProblem);
        boolean check = false;
        if (!CollectionUtils.isEmpty(qualityProblems)) {
            for (QualityProblem i : qualityProblems) {
                String problemProgress = i.getProblemProgress();
                if (!problemProgress.equals("已关闭")) {
                    check = true;
                    break;
                }
            }
        }
        if (check) {
            return AjaxResult.error(500, "该任务下还有未关闭的问题，请把问题关闭后再次验收");
        }
        return AjaxResult.success(this.qualityTaskService.update(qualityTask));
    }

    @Log(title = "质量任务", businessType = BusinessType.EXPORT)
    @PostMapping("/exportData")
    public void export(HttpServletResponse response, QualityTask qualityTask) {
        List<QualityTask> list = qualityTaskService.queryByPage(qualityTask);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(val -> {
                QueryWrapper<ProjectMpp> wrapper = new QueryWrapper<>();
                wrapper.eq("proj_id", val.getProjId());
                ProjectMpp projectMpp = projectMppService.getOne(wrapper);
                if (null != projectMpp) {
                    val.setProjName(projectMpp.getTaskName());
                }
                if (null != val.getTaskState()) {
                    if (val.getTaskState() == 0) {
                        val.setTaskStateBase("待处理");
                    }
                    if (val.getTaskState() == 1) {
                        val.setTaskStateBase("已处理");
                    }
                }
                if (StringUtils.isNotEmpty(val.getExecutorUserIds())) {
                    SysUser sysUser = iSysUserService.selectUserById(Long.valueOf(val.getExecutorUserIds()));
                    if (null != sysUser) {
                        val.setNickName(sysUser.getNickName());
                        if (null != sysUser.getDeptId()) {
                            SysDept sysDept = iSysDeptService.selectDeptById(sysUser.getDeptId());
                            if (null != sysDept) {
                                val.setDeptName(sysDept.getDeptName());
                            }
                        }
                    }
                }
            });
        }
        ExcelUtil<QualityTask> util = new ExcelUtil<>(QualityTask.class);
        util.exportExcel(response, list, "质量任务(");
    }

    /**
     * 上传验收文件
     *
     * @param qualityTask 实体
     * @return 上传结果
     */
    @PostMapping("/upload/acceptance")
    public AjaxResult uploadAcceptance(@RequestBody QualityTask qualityTask) {
        if (null == qualityTask) {
            return AjaxResult.error("参数异常！");
        }
        if (null == qualityTask.getId()) {
            return AjaxResult.error("参数异常！");
        }
        if (CollectionUtils.isEmpty(qualityTask.getQualityTaskFiles())) {
            return AjaxResult.error("参数异常！");
        }
        StringBuilder key = new StringBuilder();
        if (!CollectionUtils.isEmpty(qualityTask.getQualityTaskFiles())) {
            for (QualityTaskFiles qualityTaskFiles : qualityTask.getQualityTaskFiles()) {
                qualityTaskFilesService.save(qualityTaskFiles);
                key.append(qualityTaskFiles.getId());
                key.append(",");
            }
        }

        if (!StringUtils.isEmpty(key.toString())) {
            QualityTask qualityTaskUpdate = new QualityTask();
            qualityTaskUpdate.setId(qualityTask.getId());
            qualityTaskUpdate.setFileId(key.toString());
            qualityTaskService.update(qualityTaskUpdate);
        }

        return AjaxResult.success();
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/qualityAdd")
    public AjaxResult add(@RequestBody QualityTaskDTO qualityTaskDTO) {
        log.info("请求参数 qualityAdd:{}", JSON.toJSON(qualityTaskDTO));

        List<QualityProblem> qualityProblemList = qualityTaskDTO.getQualityProblemList();
        if (!CollectionUtils.isEmpty(qualityProblemList)) {
            qualityProblemList.forEach(val -> {
                QualityAutomaticConfig qualityAutomaticConfig = new QualityAutomaticConfig();
                QualityAutomaticConfig configServiceById = qualityAutomaticConfigService.getById(val.getProblemType());
                qualityAutomaticConfig.setQualityConfigDetails(configServiceById.getQualityConfigDetails());
                qualityAutomaticConfigService.check(qualityAutomaticConfig, val.getProblemInfo());
            });
        }

        StringBuilder key = new StringBuilder();

        QualityTask queryById = qualityTaskService.queryById(qualityTaskDTO.getPid());
        if (null != queryById && !StringUtils.isEmpty(queryById.getTaskProblemId())) {
            key.append(queryById.getTaskProblemId());
        }

        if (!CollectionUtils.isEmpty(qualityTaskDTO.getQualityProblemList())) {
            for (QualityProblem qualityTask : qualityTaskDTO.getQualityProblemList()) {
                qualityTask.setCheckStatus(0);
                qualityProblemService.insert(qualityTask);
                key.append(qualityTask.getId());
                key.append(",");
            }
        }

        if (!com.ruoyi.assessment.core.utils.StringUtils.isBlank(key.toString())) {
            QualityTask qualityTask = new QualityTask();
            qualityTask.setId(qualityTaskDTO.getPid());
            qualityTask.setTaskProblemId(key.toString());
            this.qualityTaskService.update(qualityTask);
        }

        return AjaxResult.success();
    }

    @GetMapping("/qualityList")
    public AjaxResult qualityList(QualityTaskDTO qualityTaskDTO) {
        log.info("查询质量问题:{}", JSON.toJSON(qualityTaskDTO));
        QualityTask qualityTask = qualityTaskService.queryById(qualityTaskDTO.getPid());
        List<QualityProblem> list = new ArrayList<>();
        if (qualityTask != null) {
            if (!com.ruoyi.assessment.core.utils.StringUtils.isBlank(qualityTask.getTaskProblemId())) {
                String taskProblemId = qualityTask.getTaskProblemId();
                taskProblemId = taskProblemId.substring(0, taskProblemId.length() - 1);
                if (taskProblemId.contains(",")) {
                    String[] split = taskProblemId.split(",");
                    QualityProblem qualityProblem = new QualityProblem();
                    qualityProblem.setIds(Arrays.asList(split));

                    log.info("查询质量问题 结果 :{}", qualityProblem);

                    list.addAll(qualityProblemService.queryByPage(qualityProblem));
                } else {
                    QualityProblem qualityProblem = qualityProblemService.queryById(Integer.valueOf(taskProblemId));
                    list.add(qualityProblem);
                }
            }
        }
        qualityTaskDTO.setQualityProblemList(list);
        return AjaxResult.success(qualityTaskDTO);
    }

    @Log(title = "质量任务(", businessType = BusinessType.EXPORT)
    @PostMapping("/exportDataTwo")
    public void export(HttpServletResponse response, String[] ids) {
        List<QualityTask> qualityTaskList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>(Arrays.asList(ids));
        if (!CollectionUtils.isEmpty(list)) {
            for (String id : list) {
                QualityTask qualityTask = qualityTaskService.queryById(Integer.valueOf(id));
                qualityTaskList.add(qualityTask);
            }
        }

        if (!CollectionUtils.isEmpty(qualityTaskList)) {
            qualityTaskList.forEach(val -> {
                QueryWrapper<ProjectMpp> wrapper = new QueryWrapper<>();
                wrapper.eq("proj_id", val.getProjId());
                ProjectMpp projectMpp = projectMppService.getOne(wrapper);
                if (null != projectMpp) {
                    val.setProjName(projectMpp.getTaskName());
                }
                if (null != val.getTaskState()) {
                    if (val.getTaskState() == 0) {
                        val.setTaskStateBase("待处理");
                    }
                    if (val.getTaskState() == 1) {
                        val.setTaskStateBase("已处理");
                    }
                }
                if (StringUtils.isNotEmpty(val.getExecutorUserIds())) {
                    SysUser sysUser = iSysUserService.selectUserById(Long.valueOf(val.getExecutorUserIds()));
                    if (null != sysUser) {
                        val.setNickName(sysUser.getNickName());
                        if (null != sysUser.getDeptId()) {
                            SysDept sysDept = iSysDeptService.selectDeptById(sysUser.getDeptId());
                            if (null != sysDept) {
                                val.setDeptName(sysDept.getDeptName());
                            }
                        }
                    }
                }
            });
        }

        ExcelUtil<QualityTask> util = new ExcelUtil<>(QualityTask.class);
        util.exportExcel(response, qualityTaskList, "质量任务");
    }

}

