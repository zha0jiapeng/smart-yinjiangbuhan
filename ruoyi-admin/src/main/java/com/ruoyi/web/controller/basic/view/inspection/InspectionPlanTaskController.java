package com.ruoyi.web.controller.basic.view.inspection;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.InspectionPlanTask;
import com.ruoyi.system.service.InspectionPlanTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计划任务表(InspectionPlanTask)表控制层
 *
 * @author makejava
 * @since 2022-11-28 17:55:24
 */
@RestController
@RequestMapping("inspectionPlanTask")
public class InspectionPlanTaskController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionPlanTaskService inspectionPlanTaskService;

    /**
     * 分页查询
     *
     * @param inspectionPlanTask 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionPlanTask inspectionPlanTask) {
        startPage();
        List<InspectionPlanTask> inspectionPlanTasks = inspectionPlanTaskService.queryByPage(inspectionPlanTask);
        return getDataTable(inspectionPlanTasks);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionPlanTaskService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionPlanTask 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(InspectionPlanTask inspectionPlanTask) {
        return AjaxResult.success(this.inspectionPlanTaskService.insert(inspectionPlanTask));
    }

    /**
     * 编辑数据
     *
     * @param inspectionPlanTask 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(InspectionPlanTask inspectionPlanTask) {
        return AjaxResult.success(this.inspectionPlanTaskService.update(inspectionPlanTask));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionPlanTaskService.deleteById(id));
    }

}

