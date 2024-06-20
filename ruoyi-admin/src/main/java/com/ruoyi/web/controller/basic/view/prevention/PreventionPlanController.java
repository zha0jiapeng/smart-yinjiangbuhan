package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.PreventionPlan;
import com.ruoyi.system.service.PreventionPlanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 隐患排查计划(PreventionPlan)表控制层
 *
 * @author makejava
 * @since 2022-12-17 13:21:33
 */
@RestController
@RequestMapping("preventionPlan")
public class PreventionPlanController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionPlanService preventionPlanService;

    /**
     * 分页查询
     *
     * @param preventionPlan 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionPlan preventionPlan) {
        startPage();
        List<PreventionPlan> preventionPlans = this.preventionPlanService.queryByPage(preventionPlan);
        return getDataTable(preventionPlans);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionPlanService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionPlan 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionPlan preventionPlan) {
        return AjaxResult.success(this.preventionPlanService.insert(preventionPlan));
    }

    /**
     * 编辑数据
     *
     * @param preventionPlan 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionPlan preventionPlan) {
        return AjaxResult.success(this.preventionPlanService.update(preventionPlan));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionPlanService.deleteById(id));
    }

}

