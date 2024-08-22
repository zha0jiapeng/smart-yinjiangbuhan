package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressPlan;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysConstructionProgressPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 施工进度计划Controller
 * 
 * @author mashir0
 * @date 2024-08-22
 */
@RestController
@RequestMapping("/construction/progress/plan")
public class SysConstructionProgressPlanController extends BaseController
{
    @Autowired
    private ISysConstructionProgressPlanService sysConstructionProgressPlanService;

    @GetMapping("/getTotalPlanByYear")
    public AjaxResult getTotalPlanByYear(String year)
    {
        SysConstructionProgressPlan totalByYear = sysConstructionProgressPlanService.getTotalByYear(year);
        return AjaxResult.success(totalByYear);
    }

    /**
     * 查询施工进度计划列表
     */
    //@PreAuthorize("@ss.hasPermi('system:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        startPage();
        List<SysConstructionProgressPlan> list = sysConstructionProgressPlanService.selectSysConstructionProgressPlanList(sysConstructionProgressPlan);
        return getDataTable(list);
    }

    /**
     * 导出施工进度计划列表
     */
   // @PreAuthorize("@ss.hasPermi('system:plan:export')")
    @Log(title = "施工进度计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        List<SysConstructionProgressPlan> list = sysConstructionProgressPlanService.selectSysConstructionProgressPlanList(sysConstructionProgressPlan);
        ExcelUtil<SysConstructionProgressPlan> util = new ExcelUtil<SysConstructionProgressPlan>(SysConstructionProgressPlan.class);
        util.exportExcel(response, list, "施工进度计划数据");
    }

    /**
     * 获取施工进度计划详细信息
     */
   // @PreAuthorize("@ss.hasPermi('system:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysConstructionProgressPlanService.selectSysConstructionProgressPlanById(id));
    }

    /**
     * 新增施工进度计划
     */
    //@PreAuthorize("@ss.hasPermi('system:plan:add')")
    @Log(title = "施工进度计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        return toAjax(sysConstructionProgressPlanService.insertSysConstructionProgressPlan(sysConstructionProgressPlan));
    }

    /**
     * 修改施工进度计划
     */
   // @PreAuthorize("@ss.hasPermi('system:plan:edit')")
    @Log(title = "施工进度计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        return toAjax(sysConstructionProgressPlanService.updateSysConstructionProgressPlan(sysConstructionProgressPlan));
    }

    /**
     * 删除施工进度计划
     */
   // @PreAuthorize("@ss.hasPermi('system:plan:remove')")
    @Log(title = "施工进度计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysConstructionProgressPlanService.deleteSysConstructionProgressPlanByIds(ids));
    }
}
