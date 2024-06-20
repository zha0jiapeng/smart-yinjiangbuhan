package com.ruoyi.web.controller.basic.view.inspection;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.PointProjectVO;
import com.ruoyi.system.entity.InspectionPlan;
import com.ruoyi.system.entity.InspectionPoint;
import com.ruoyi.system.entity.InspectionRoute;
import com.ruoyi.system.service.InspectionPlanService;
import com.ruoyi.system.service.InspectionPointService;
import com.ruoyi.system.service.InspectionRouteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionPoint)表控制层
 *
 * @author makejava
 * @since 2022-11-25 17:10:29
 */
@RestController
@RequestMapping("inspectionPoint")
public class InspectionPointController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionPointService inspectionPointService;

    @Resource
    private InspectionPlanService inspectionPlanService;

    @Resource
    private InspectionRouteService inspectionRouteService;

    /**
     * 分页查询
     *
     * @param inspectionPoint 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionPoint inspectionPoint) {
        startPage();
        List<InspectionPoint> inspectionPoints = this.inspectionPointService.queryByPage(inspectionPoint);
        return getDataTable(inspectionPoints);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionPointService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionPoint 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody InspectionPoint inspectionPoint) throws Exception {
        return AjaxResult.success(this.inspectionPointService.insert(inspectionPoint));
    }

    /**
     * 编辑数据
     *
     * @param inspectionPoint 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody InspectionPoint inspectionPoint) {
        return AjaxResult.success(this.inspectionPointService.update(inspectionPoint));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionPointService.deleteById(id));
    }

    @GetMapping("/info")
    public AjaxResult getPointAndProjectById(Integer id) {
        PointProjectVO pointProjectVO = inspectionPointService.getPointAndProjectById(id);
        return AjaxResult.success(pointProjectVO);
    }

    @PostMapping("/updateStopCheck")
    public AjaxResult updateStopCheck(@RequestBody InspectionPoint inspectionPoint) {
        Integer id = inspectionPoint.getId();
        Integer stopCheck = inspectionPoint.getStopCheck();
        if (stopCheck == 1) {
            InspectionPoint byId = inspectionPointService.queryById(id);
            InspectionRoute inspectionRoute = inspectionRouteService.queryById(byId.getRouteId());
            InspectionPlan inspectionPlan = new InspectionPlan();
            inspectionPlan.setRouteId(inspectionRoute.getId());
            List<InspectionPlan> inspectionPlans = inspectionPlanService.queryByPage(inspectionPlan);
            if (CollUtil.isNotEmpty(inspectionPlans)) {
                return AjaxResult.error(500,"该点位的巡检路线有计划绑定，无法停检");
            }
        }
        inspectionPointService.updateStopCheck(inspectionPoint);
        return AjaxResult.success();
    }

}

