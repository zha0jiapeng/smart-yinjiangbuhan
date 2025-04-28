package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SpecialOperationTypes;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISpecialOperationTypesService;
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
 * 特种作业类型Controller
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
@RestController
@RequestMapping("/special/operation/types")
@Api(tags={"特种作业类型 Controller"})
public class SpecialOperationTypesController extends BaseController
{
    @Autowired
    private ISpecialOperationTypesService specialOperationTypesService;

    /**
     * 查询特种作业类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:types:list')")
    @GetMapping("/list")
    @ApiOperation("查询特种作业类型列表")
    public TableDataInfo list(SpecialOperationTypes specialOperationTypes)
    {
        startPage();
        List<SpecialOperationTypes> list = specialOperationTypesService.selectSpecialOperationTypesList(specialOperationTypes);
        return getDataTable(list);
    }

    /**
     * 导出特种作业类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:types:export')")
    @Log(title = "特种作业类型", businessType = BusinessType.EXPORT)
    @ApiOperation("导出特种作业类型列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpecialOperationTypes specialOperationTypes)
    {
        List<SpecialOperationTypes> list = specialOperationTypesService.selectSpecialOperationTypesList(specialOperationTypes);
        ExcelUtil<SpecialOperationTypes> util = new ExcelUtil<SpecialOperationTypes>(SpecialOperationTypes.class);
        util.exportExcel(response, list, "特种作业类型数据");
    }

    /**
     * 获取特种作业类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:types:query')")
    @ApiOperation("获取特种作业类型详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "特种作业类型参数", required = true)
            @PathVariable("id") Long id)
    {
        return success(specialOperationTypesService.selectSpecialOperationTypesById(id));
    }

    /**
     * 新增特种作业类型
     */
    @PreAuthorize("@ss.hasPermi('system:types:add')")
    @Log(title = "特种作业类型", businessType = BusinessType.INSERT)
    @ApiOperation("新增特种作业类型")
    @PostMapping
    public AjaxResult add(@RequestBody SpecialOperationTypes specialOperationTypes)
    {
        return toAjax(specialOperationTypesService.insertSpecialOperationTypes(specialOperationTypes));
    }

    /**
     * 修改特种作业类型
     */
    @PreAuthorize("@ss.hasPermi('system:types:edit')")
    @Log(title = "特种作业类型", businessType = BusinessType.UPDATE)
    @ApiOperation("修改特种作业类型")
    @PutMapping
    public AjaxResult edit(@RequestBody SpecialOperationTypes specialOperationTypes)
    {
        return toAjax(specialOperationTypesService.updateSpecialOperationTypes(specialOperationTypes));
    }

    /**
     * 删除特种作业类型
     */
    @PreAuthorize("@ss.hasPermi('system:types:remove')")
    @Log(title = "特种作业类型", businessType = BusinessType.DELETE)
    @ApiOperation("删除特种作业类型")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "特种作业类型ids参数", required = true)
            @PathVariable Long[] ids)
    {
        return toAjax(specialOperationTypesService.deleteSpecialOperationTypesByIds(ids));
    }
}
