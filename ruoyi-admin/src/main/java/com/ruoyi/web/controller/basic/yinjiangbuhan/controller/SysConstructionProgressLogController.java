package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysConstructionProgressLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 施工日志Controller
 * 
 * @author mashir0
 * @date 2024-08-20
 */
@RestController
@RequestMapping("/system/constructionProgressLog")
public class SysConstructionProgressLogController extends BaseController
{
    @Autowired
    private ISysConstructionProgressLogService sysConstructionProgressLogService;

    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {
            // Read the Excel file
            List<SysConstructionProgressLog> staffList = EasyExcel.read(file.getInputStream())
                    .head(SysConstructionProgressLog.class)
                    .sheet()
                    .doReadSync();
            sysConstructionProgressLogService.saveBatch(staffList);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 查询施工日志列表
     */
    //@PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysConstructionProgressLog sysConstructionProgressLog)
    {
        startPage();
        List<SysConstructionProgressLog> list = sysConstructionProgressLogService.selectSysConstructionProgressLogList(sysConstructionProgressLog);
        return getDataTable(list);
    }

    /**
     * 导出施工日志列表
     */
    //@PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "施工日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysConstructionProgressLog sysConstructionProgressLog)
    {
        List<SysConstructionProgressLog> list = sysConstructionProgressLogService.selectSysConstructionProgressLogList(sysConstructionProgressLog);
        ExcelUtil<SysConstructionProgressLog> util = new ExcelUtil<SysConstructionProgressLog>(SysConstructionProgressLog.class);
        util.exportExcel(response, list, "施工日志数据");
    }

    /**
     * 获取施工日志详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysConstructionProgressLogService.selectSysConstructionProgressLogById(id));
    }

    /**
     * 新增施工日志
     */
   // @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "施工日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysConstructionProgressLog sysConstructionProgressLog)
    {
        return toAjax(sysConstructionProgressLogService.insertSysConstructionProgressLog(sysConstructionProgressLog));
    }

    /**
     * 修改施工日志
     */
    //@PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "施工日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysConstructionProgressLog sysConstructionProgressLog)
    {
        return toAjax(sysConstructionProgressLogService.updateSysConstructionProgressLog(sysConstructionProgressLog));
    }

    /**
     * 删除施工日志
     */
    //@PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "施工日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysConstructionProgressLogService.deleteSysConstructionProgressLogByIds(ids));
    }
}
