package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.PreventionPlanTask;
import com.ruoyi.system.service.PreventionPlanTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 隐患排查计划任务(PreventionPlanTask)表控制层
 *
 * @author makejava
 * @since 2022-12-17 13:22:34
 */
@RestController
@RequestMapping("preventionPlanTask")
public class PreventionPlanTaskController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionPlanTaskService preventionPlanTaskService;

    /**
     * 分页查询
     *
     * @param preventionPlanTask 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionPlanTask preventionPlanTask) {
        startPage();
        List<PreventionPlanTask> preventionPlanTasks = this.preventionPlanTaskService.queryByPage(preventionPlanTask);
        return getDataTable(preventionPlanTasks);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionPlanTaskService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionPlanTask 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionPlanTask preventionPlanTask) {
        return AjaxResult.success(this.preventionPlanTaskService.insert(preventionPlanTask));
    }

    /**
     * 编辑数据
     *
     * @param preventionPlanTask 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionPlanTask preventionPlanTask) {
        return AjaxResult.success(this.preventionPlanTaskService.update(preventionPlanTask));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionPlanTaskService.deleteById(id));
    }

    @PostMapping("/completeTask")
    public AjaxResult completeTask(@RequestBody PreventionPlanTask preventionPlanTask) {
        preventionPlanTaskService.completeTask(preventionPlanTask);
        return AjaxResult.success();
    }
}

