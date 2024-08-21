package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AlarmType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmTypeService;
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
 * 报警类型表Controller
 * 
 * @author liang
 * @date 2024-08-21
 */
@RestController
@RequestMapping("/system/type")
@Api(tags={"报警类型表 Controller"})
public class AlarmTypeController extends BaseController
{
    @Autowired
    private IAlarmTypeService alarmTypeService;

    /**
     * 查询报警类型表列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    @ApiOperation("查询报警类型表列表")
    public TableDataInfo list(AlarmType alarmType)
    {
        startPage();
        List<AlarmType> list = alarmTypeService.selectAlarmTypeList(alarmType);
        return getDataTable(list);
    }

    /**
     * 导出报警类型表列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @Log(title = "报警类型表", businessType = BusinessType.EXPORT)
    @ApiOperation("导出报警类型表列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlarmType alarmType)
    {
        List<AlarmType> list = alarmTypeService.selectAlarmTypeList(alarmType);
        ExcelUtil<AlarmType> util = new ExcelUtil<AlarmType>(AlarmType.class);
        util.exportExcel(response, list, "报警类型表数据");
    }

    /**
     * 获取报警类型表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @ApiOperation("获取报警类型表详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "报警类型表参数", required = true)
            @PathVariable("id") Long id)
    {
        return success(alarmTypeService.selectAlarmTypeById(id));
    }

    /**
     * 新增报警类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "报警类型表", businessType = BusinessType.INSERT)
    @ApiOperation("新增报警类型表")
    @PostMapping
    public AjaxResult add(@RequestBody AlarmType alarmType)
    {
        return toAjax(alarmTypeService.insertAlarmType(alarmType));
    }

    /**
     * 修改报警类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "报警类型表", businessType = BusinessType.UPDATE)
    @ApiOperation("修改报警类型表")
    @PutMapping
    public AjaxResult edit(@RequestBody AlarmType alarmType)
    {
        return toAjax(alarmTypeService.updateAlarmType(alarmType));
    }

    /**
     * 删除报警类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "报警类型表", businessType = BusinessType.DELETE)
    @ApiOperation("删除报警类型表")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "报警类型表ids参数", required = true)
            @PathVariable Long[] ids)
    {
        return toAjax(alarmTypeService.deleteAlarmTypeByIds(ids));
    }
}
