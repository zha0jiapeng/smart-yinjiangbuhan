package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysEvents;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 事件Controller
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@RestController
@RequestMapping("/sysEvent")
public class SysEventController extends BaseController
{
    @Autowired
    private ISysEventsService sysEventsService;

    /**
     * 查询事件列表
     */
    //@PreAuthorize("@ss.hasPermi('system:education:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEvents sysEvents)
    {
        startPage();
        List<SysEvents> list = sysEventsService.selectSysEventsList(sysEvents);
        return getDataTable(list);
    }

    /**
     * 导出事件列表
     */
    //@PreAuthorize("@ss.hasPermi('system:education:export')")
    @Log(title = "事件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEvents sysEvents)
    {
        List<SysEvents> list = sysEventsService.selectSysEventsList(sysEvents);
        ExcelUtil<SysEvents> util = new ExcelUtil<SysEvents>(SysEvents.class);
        util.exportExcel(response, list, "事件数据");
    }

    /**
     * 获取事件详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:education:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysEventsService.selectSysEventsById(id));
    }

    /**
     * 新增事件
     */
    //@PreAuthorize("@ss.hasPermi('system:education:add')")
    @Log(title = "事件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEvents sysEvents)
    {
        return toAjax(sysEventsService.insertSysEvents(sysEvents));
    }

    /**
     * 修改事件
     */
  //  @PreAuthorize("@ss.hasPermi('system:education:edit')")
    @Log(title = "事件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEvents sysEvents)
    {
        return toAjax(sysEventsService.updateSysEvents(sysEvents));
    }

    /**
     * 删除事件
     */
   // @PreAuthorize("@ss.hasPermi('system:education:remove')")
    @Log(title = "事件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysEventsService.deleteSysEventsByIds(ids));
    }
}
