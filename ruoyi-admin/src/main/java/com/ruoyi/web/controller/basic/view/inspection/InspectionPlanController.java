package com.ruoyi.web.controller.basic.view.inspection;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.InspectionPlan;
import com.ruoyi.system.service.InspectionPlanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionPlan)表控制层
 *
 * @author makejava
 * @since 2022-11-25 17:09:56
 */
@RestController
@RequestMapping("inspectionPlan")
public class InspectionPlanController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionPlanService inspectionPlanService;

    /**
     * 分页查询
     *
     * @param inspectionPlan 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionPlan inspectionPlan) {
        startPage();
        List<InspectionPlan> inspectionPlans = this.inspectionPlanService.queryByPage(inspectionPlan);
        return getDataTable(inspectionPlans);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionPlanService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionPlan 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody InspectionPlan inspectionPlan) {
        return AjaxResult.success(this.inspectionPlanService.insert(inspectionPlan));
    }

    /**
     * 编辑数据
     *
     * @param inspectionPlan 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody InspectionPlan inspectionPlan) {
        return AjaxResult.success(this.inspectionPlanService.update(inspectionPlan));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionPlanService.deleteById(id));
    }

}

