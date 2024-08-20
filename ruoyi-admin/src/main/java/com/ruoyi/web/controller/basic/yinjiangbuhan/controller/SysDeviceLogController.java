package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysDeviceLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysDeviceLogService;
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
 * 设备日志Controller
 * 
 * @author ruoyi
 * @date 2024-08-20
 */
@RestController
@RequestMapping("/system/log")
@Api(tags={"设备日志 Controller"})
public class SysDeviceLogController extends BaseController
{
    @Autowired
    private ISysDeviceLogService sysDeviceLogService;

    /**
     * 查询设备日志列表
     */
//    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    @ApiOperation("查询设备日志列表")
    public TableDataInfo list(SysDeviceLog sysDeviceLog)
    {
        startPage();
        List<SysDeviceLog> list = sysDeviceLogService.selectSysDeviceLogList(sysDeviceLog);
        return getDataTable(list);
    }

    /**
     * 导出设备日志列表
     */
//    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "设备日志", businessType = BusinessType.EXPORT)
    @ApiOperation("导出设备日志列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDeviceLog sysDeviceLog)
    {
        List<SysDeviceLog> list = sysDeviceLogService.selectSysDeviceLogList(sysDeviceLog);
        ExcelUtil<SysDeviceLog> util = new ExcelUtil<SysDeviceLog>(SysDeviceLog.class);
        util.exportExcel(response, list, "设备日志数据");
    }

    /**
     * 获取设备日志详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @ApiOperation("获取设备日志详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "设备日志参数", required = true)
            @PathVariable("id") Long id)
    {
        return success(sysDeviceLogService.selectSysDeviceLogById(id));
    }

    /**
     * 新增设备日志
     */
//    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "设备日志", businessType = BusinessType.INSERT)
    @ApiOperation("新增设备日志")
    @PostMapping
    public AjaxResult add(@RequestBody SysDeviceLog sysDeviceLog)
    {
        return toAjax(sysDeviceLogService.insertSysDeviceLog(sysDeviceLog));
    }

    /**
     * 修改设备日志
     */
//    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "设备日志", businessType = BusinessType.UPDATE)
    @ApiOperation("修改设备日志")
    @PutMapping
    public AjaxResult edit(@RequestBody SysDeviceLog sysDeviceLog)
    {
        return toAjax(sysDeviceLogService.updateSysDeviceLog(sysDeviceLog));
    }

    /**
     * 删除设备日志
     */
//    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "设备日志", businessType = BusinessType.DELETE)
    @ApiOperation("删除设备日志")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "设备日志ids参数", required = true)
            @PathVariable Long[] ids)
    {
        return toAjax(sysDeviceLogService.deleteSysDeviceLogByIds(ids));
    }
}
