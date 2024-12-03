package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysVentilatorMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 通风机监测数据Controller
 * 
 * @author mashir0
 * @date 2024-08-24
 */
@RestController
    @RequestMapping("/system/ventilator/monitor")
public class SysVentilatorMonitorController extends BaseController
{
    @Autowired
    private ISysVentilatorMonitorService sysVentilatorMonitorService;

    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {
            // Read the Excel file
            List<SysVentilatorMonitor> sysVentilatorMonitors = EasyExcel.read(file.getInputStream())
                    .head(SysVentilatorMonitor.class)
                    .sheet()
                    .doReadSync();
            for (SysVentilatorMonitor sysVentilatorMonitor : sysVentilatorMonitors) {
                //sysVentilatorMonitor.setIsOpen(sysVentilatorMonitor.getIsOpenStr().equals("开启")?1:0);
            }
            sysVentilatorMonitorService.saveBatch(sysVentilatorMonitors);
            return AjaxResult.success("导入成功");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getRandomData")
    public SysVentilatorMonitor getRandomData()
    {
       return sysVentilatorMonitorService.getRandomRecord();
    }


    @GetMapping("/getCurve")
    public List<Map<String,Object>> getCurve()
    {
        return sysVentilatorMonitorService.getCurve();
    }
    /**
     * 查询通风机监测数据列表
     */
    //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysVentilatorMonitor sysVentilatorMonitor)
    {
        startPage();
        List<SysVentilatorMonitor> list = sysVentilatorMonitorService.selectSysVentilatorMonitorList(sysVentilatorMonitor);
        return getDataTable(list);
    }

    /**
     * 导出通风机监测数据列表
     */
    //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:export')")
    @Log(title = "通风机监测数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysVentilatorMonitor sysVentilatorMonitor)
    {
        List<SysVentilatorMonitor> list = sysVentilatorMonitorService.selectSysVentilatorMonitorList(sysVentilatorMonitor);
        ExcelUtil<SysVentilatorMonitor> util = new ExcelUtil<SysVentilatorMonitor>(SysVentilatorMonitor.class);
        util.exportExcel(response, list, "通风机监测数据数据");
    }

    /**
     * 获取通风机监测数据详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysVentilatorMonitorService.selectSysVentilatorMonitorById(id));
    }

    /**
     * 新增通风机监测数据
     */
   //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:add')")
    @Log(title = "通风机监测数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysVentilatorMonitor sysVentilatorMonitor)
    {
        return toAjax(sysVentilatorMonitorService.insertSysVentilatorMonitor(sysVentilatorMonitor));
    }

    /**
     * 修改通风机监测数据
     */
    //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:edit')")
    @Log(title = "通风机监测数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysVentilatorMonitor sysVentilatorMonitor)
    {
        return toAjax(sysVentilatorMonitorService.updateSysVentilatorMonitor(sysVentilatorMonitor));
    }

    /**
     * 删除通风机监测数据
     */
    //@PreAuthorize("@ss.hasPermi('system:ventilator/monitor:remove')")
    @Log(title = "通风机监测数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysVentilatorMonitorService.deleteSysVentilatorMonitorByIds(ids));
    }
}
