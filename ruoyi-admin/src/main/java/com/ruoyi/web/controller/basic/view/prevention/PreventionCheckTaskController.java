package com.ruoyi.web.controller.basic.view.prevention;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.vo.PreventionCheckTaskVO;
import com.ruoyi.system.entity.PreventionCheckTask;
import com.ruoyi.system.service.PreventionCheckTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 排查任务表(PreventionCheckTask)表控制层
 *
 * @author makejava
 * @since 2022-11-18 14:04:46
 */
@RestController
@RequestMapping("preventionCheckTask")
public class PreventionCheckTaskController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionCheckTaskService preventionCheckTaskService;

    /**
     * 分页查询
     *
     * @param preventionCheckTask 筛选条件
     * @return 查询结果
     */
    @GetMapping("list")
    public TableDataInfo queryByPage(PreventionCheckTask preventionCheckTask) {
        startPage();
        List<PreventionCheckTask> preventionCheckTasks = this.preventionCheckTaskService.queryByPage(preventionCheckTask);
        return getDataTable(preventionCheckTasks);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        PreventionCheckTaskVO taskInfoById = preventionCheckTaskService.getTaskInfoById(id);
        return AjaxResult.success(taskInfoById);
    }

    /**
     * 新增数据
     *
     * @param preventionCheckTask 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionCheckTask preventionCheckTask) {
        return AjaxResult.success(this.preventionCheckTaskService.insert(preventionCheckTask));
    }

    /**
     * 编辑数据
     *
     * @param preventionCheckTask 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionCheckTask preventionCheckTask) {
        return AjaxResult.success(this.preventionCheckTaskService.update(preventionCheckTask));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionCheckTaskService.deleteById(id));
    }

    /**
     * 修改任务状态
     *
     * @param preventionCheckTask 实体
     * @return 编辑结果
     */
    @PostMapping("/updateTaskState")
    public AjaxResult updateTaskState(@RequestBody PreventionCheckTask preventionCheckTask) {
        preventionCheckTask.setCheckUserName(SecurityUtils.getUsername());
        preventionCheckTask.setCheckTime(DateUtil.now());
        preventionCheckTask.setTaskState("已排查");
        this.preventionCheckTaskService.updateTaskState(preventionCheckTask);
        return AjaxResult.success();
    }

    /**
     *  根据任务ID查询该任务详情信息
     * @param id
     * @return
     */
    @GetMapping("/getTaskInfoById")
    public AjaxResult getTaskInfoById(Integer id) {
        PreventionCheckTaskVO taskInfoById = preventionCheckTaskService.getTaskInfoById(id);
        return AjaxResult.success(taskInfoById);
    }

}

