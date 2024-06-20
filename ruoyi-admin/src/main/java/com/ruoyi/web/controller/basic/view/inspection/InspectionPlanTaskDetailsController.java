package com.ruoyi.web.controller.basic.view.inspection;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.InspectionPlanTaskDetails;
import com.ruoyi.system.service.InspectionPlanTaskDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 计划任务详情表(InspectionPlanTaskDetails)表控制层
 *
 * @author makejava
 * @since 2022-11-28 17:55:43
 */
@RestController
@RequestMapping("inspectionPlanTaskDetails")
public class InspectionPlanTaskDetailsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionPlanTaskDetailsService inspectionPlanTaskDetailsService;

    /**
     * 分页查询
     *
     * @param inspectionPlanTaskDetails 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        startPage();
        List<InspectionPlanTaskDetails> inspectionPlanTaskDetailsList = this.inspectionPlanTaskDetailsService.queryByPage(inspectionPlanTaskDetails);
        return getDataTable(inspectionPlanTaskDetailsList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionPlanTaskDetailsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionPlanTaskDetails 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        return AjaxResult.success(this.inspectionPlanTaskDetailsService.insert(inspectionPlanTaskDetails));
    }

    /**
     * 编辑数据
     *
     * @param inspectionPlanTaskDetails 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        return AjaxResult.success(this.inspectionPlanTaskDetailsService.update(inspectionPlanTaskDetails));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionPlanTaskDetailsService.deleteById(id));
    }

    /**
     * 完成单个点位任务
     *
     * @return 删除是否成功
     */
    @PostMapping("/completeTask")
    public AjaxResult completeTask(@RequestBody InspectionPlanTaskDetails inspectionPlanTaskDetails) {
        inspectionPlanTaskDetailsService.completeTask(inspectionPlanTaskDetails);
        return AjaxResult.success();
    }


}

