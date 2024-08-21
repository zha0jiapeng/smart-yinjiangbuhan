package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 施工日志Controller
 * 
 * @author mashir0
 * @date 2024-08-20
 */
@RestController
@RequestMapping("/construction/progress/log")
public class SysConstructionProgressLogController extends BaseController
{
    @Autowired
    private ISysConstructionProgressLogService sysConstructionProgressLogService;

    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {

            // Read the Excel file
            List<SysConstructionProgressLog> sysConstructionProgressLogs = EasyExcel.read(file.getInputStream())
                    .head(SysConstructionProgressLog.class)
                    .sheet()
                    .doReadSync();
            for (SysConstructionProgressLog log : sysConstructionProgressLogs) {
                SysConstructionProgressLog one = sysConstructionProgressLogService.getOne(new LambdaQueryWrapper<SysConstructionProgressLog>().eq(SysConstructionProgressLog::getLogDate, log.getLogDate()));
                if(one!=null) log.setId(one.getId());
                if(log.getDrillBlastingStart()!=null && log.getDrillBlastingEnd()!=null) {
                    BigDecimal drillBlasting = convertKNotation(log.getDrillBlastingStart()).subtract(convertKNotation(log.getDrillBlastingEnd())).setScale(2, RoundingMode.HALF_UP);
                    log.setDrillBlasting(drillBlasting.abs());
                }
                if(log.getSideTopArchStart()!=null && log.getSideTopArchEnd()!=null) {
                    BigDecimal sideTopArch = convertKNotation(log.getSideTopArchStart()).subtract(convertKNotation(log.getSideTopArchEnd())).setScale(2, RoundingMode.HALF_UP);
                    log.setSideTopArch(sideTopArch.abs());
                }
                if(log.getLiningCastingStart()!=null && log.getLiningCastingEnd()!=null) {
                    BigDecimal liningCasting = convertKNotation(log.getLiningCastingStart()).subtract(convertKNotation(log.getLiningCastingEnd())).setScale(2, RoundingMode.HALF_UP);
                    log.setLiningCasting(liningCasting.abs());
                }
                if(log.getTmb5Start()!=null && log.getTmb5End()!=null) {
                    BigDecimal tmb5 = convertKNotation(log.getTmb5Start()).subtract(convertKNotation(log.getTmb5End())).setScale(2, RoundingMode.HALF_UP);
                    log.setTmb5(tmb5.abs());
                }
                if(log.getTmb6Start()!=null && log.getTmb6End()!=null) {
                    BigDecimal tmb6 = convertKNotation(log.getTmb6Start()).subtract(convertKNotation(log.getTmb6End())).setScale(2, RoundingMode.HALF_UP);
                    log.setTmb6(tmb6.abs());
                }
                log.setCreatedDate(new Date());
                log.setModifyDate(new Date());
            }
            sysConstructionProgressLogService.saveOrUpdateBatch(sysConstructionProgressLogs);
            return AjaxResult.success("导入成功");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BigDecimal convertKNotation(String input) {
        // Remove the "K" prefix
        String[] parts = input.substring(1).split("\\+");

        // Parse the parts into numbers
        int kilometers = Integer.parseInt(parts[0]);  // "80" -> 80
        double meters = Double.parseDouble(parts[1]); // "110.0" -> 110.0

        // Combine the parts into the final result
        return new BigDecimal(kilometers * 1000 + meters); // 80 * 1000 + 110.0 -> 80110.0
    }

    @GetMapping("/getCurve")
    public AjaxResult getCurve(Integer type, String text)
    {
        List<Map<String, Object>> curve = sysConstructionProgressLogService.getCurve(type, text);
        return AjaxResult.success(curve);
    }

    @GetMapping("/getTotal")
    public AjaxResult getTotal(Integer type,@RequestParam(required = false) String text)
    {
        List<String> expectedHoleTypes = Arrays.asList("支洞", "主隧洞");
        List<Map<String, Object>> sum = sysConstructionProgressLogService.getSum(type, text);

        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        for (Map<String, Object> entry : sum) {
            String holeType = (String) entry.get("hole_type");
            resultMap.put(holeType, entry);
        }
        for (String expectedType : expectedHoleTypes) {
            if (!resultMap.containsKey(expectedType)) {
                // 如果缺失，创建默认值的Map并添加到结果中
                Map<String, Object> defaultEntry = new HashMap<>();
                defaultEntry.put("hole_type", expectedType);
                defaultEntry.put("total_excavation", 0.0);  // 默认值，视具体需求而定
                defaultEntry.put("total_lining", 0.0);      // 默认值，视具体需求而定
                sum.add(defaultEntry);
            }
        }
        return AjaxResult.success(sum);
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
