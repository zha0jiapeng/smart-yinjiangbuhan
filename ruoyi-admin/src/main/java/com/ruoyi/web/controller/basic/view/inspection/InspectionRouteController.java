package com.ruoyi.web.controller.basic.view.inspection;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.InspectionPlan;
import com.ruoyi.system.entity.InspectionRoute;
import com.ruoyi.system.service.InspectionPlanService;
import com.ruoyi.system.service.InspectionRouteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 巡检路线表(InspectionRoute)表控制层
 *
 * @author makejava
 * @since 2022-11-25 17:11:06
 */
@RestController
@RequestMapping("inspectionRoute")
public class InspectionRouteController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionRouteService inspectionRouteService;

    @Resource
    private InspectionPlanService inspectionPlanService;

    /**
     * 分页查询
     *
     * @param inspectionRoute 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionRoute inspectionRoute) {
        startPage();
        List<InspectionRoute> inspectionRoutes = this.inspectionRouteService.queryByPage(inspectionRoute);
        return getDataTable(inspectionRoutes);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionRouteService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionRoute 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody InspectionRoute inspectionRoute) {
        return AjaxResult.success(this.inspectionRouteService.insert(inspectionRoute));
    }

    /**
     * 编辑数据
     *
     * @param inspectionRoute 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody InspectionRoute inspectionRoute) {
        return AjaxResult.success(this.inspectionRouteService.update(inspectionRoute));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionRouteService.deleteById(id));
    }

    @PostMapping("/updateStopCheck")
    public AjaxResult updateStopCheck(@RequestBody InspectionRoute inspectionRoute) {
        Integer stopCheck = inspectionRoute.getStopCheck();
        if (stopCheck == 1) {
            InspectionPlan inspectionPlan = new InspectionPlan();
            inspectionPlan.setRouteId(inspectionRoute.getId());
            List<InspectionPlan> inspectionPlans = inspectionPlanService.queryByPage(inspectionPlan);
            if (CollUtil.isNotEmpty(inspectionPlans)) {
                return AjaxResult.error(500,"该巡检路线有计划绑定，无法停检");
            }
        }
        inspectionRouteService.updateStopCheck(inspectionRoute);
        return AjaxResult.success();
    }
}

