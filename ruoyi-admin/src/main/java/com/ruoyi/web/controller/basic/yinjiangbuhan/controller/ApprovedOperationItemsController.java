package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ApprovedOperationItems;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IApprovedOperationItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 准操项目Controller
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
@RestController
@RequestMapping("/approved/operation/items")
@Api(tags={"准操项目 Controller"})
public class ApprovedOperationItemsController extends BaseController
{
    @Autowired
    private IApprovedOperationItemsService approvedOperationItemsService;

    /**
     * 查询准操项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:items:list')")
    @GetMapping("/list")
    @ApiOperation("查询准操项目列表")
    public TableDataInfo list(ApprovedOperationItems approvedOperationItems)
    {
        startPage();
        List<ApprovedOperationItems> list = approvedOperationItemsService.selectApprovedOperationItemsList(approvedOperationItems);
        return getDataTable(list);
    }

    /**
     * 导出准操项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:items:export')")
    @Log(title = "准操项目", businessType = BusinessType.EXPORT)
    @ApiOperation("导出准操项目列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApprovedOperationItems approvedOperationItems)
    {
        List<ApprovedOperationItems> list = approvedOperationItemsService.selectApprovedOperationItemsList(approvedOperationItems);
        ExcelUtil<ApprovedOperationItems> util = new ExcelUtil<ApprovedOperationItems>(ApprovedOperationItems.class);
        util.exportExcel(response, list, "准操项目数据");
    }

    /**
     * 获取准操项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:items:query')")
    @ApiOperation("获取准操项目详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "准操项目参数", required = true)
            @PathVariable("id") Long id)
    {
        return success(approvedOperationItemsService.selectApprovedOperationItemsById(id));
    }

    /**
     * 新增准操项目
     */
    @PreAuthorize("@ss.hasPermi('system:items:add')")
    @Log(title = "准操项目", businessType = BusinessType.INSERT)
    @ApiOperation("新增准操项目")
    @PostMapping
    public AjaxResult add(@RequestBody ApprovedOperationItems approvedOperationItems)
    {
        return toAjax(approvedOperationItemsService.insertApprovedOperationItems(approvedOperationItems));
    }

    /**
     * 修改准操项目
     */
    @PreAuthorize("@ss.hasPermi('system:items:edit')")
    @Log(title = "准操项目", businessType = BusinessType.UPDATE)
    @ApiOperation("修改准操项目")
    @PutMapping
    public AjaxResult edit(@RequestBody ApprovedOperationItems approvedOperationItems)
    {
        return toAjax(approvedOperationItemsService.updateApprovedOperationItems(approvedOperationItems));
    }

    /**
     * 删除准操项目
     */
    @PreAuthorize("@ss.hasPermi('system:items:remove')")
    @Log(title = "准操项目", businessType = BusinessType.DELETE)
    @ApiOperation("删除准操项目")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "准操项目ids参数", required = true)
            @PathVariable Long[] ids)
    {
        return toAjax(approvedOperationItemsService.deleteApprovedOperationItemsByIds(ids));
    }
}
