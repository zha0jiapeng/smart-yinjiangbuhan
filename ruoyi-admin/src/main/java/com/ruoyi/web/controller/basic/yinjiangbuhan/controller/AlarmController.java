package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;



import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Alarm;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAlarmService;
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
 * 报警Controller
 *
 * @author liang
 * @date 2024-08-21
 */
@RestController
@RequestMapping("/system/alarm")
@Api(tags = {"报警 Controller"})
public class AlarmController extends BaseController {
    @Autowired
    private IAlarmService alarmService;

    /**
     * 查询报警列表
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:list')")
    @GetMapping("/list")
    @ApiOperation("查询报警列表")
    public TableDataInfo list(Alarm alarm) {
        startPage();
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        return getDataTable(list);
    }

    /**
     * 导出报警列表
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:export')")
    @Log(title = "报警", businessType = BusinessType.EXPORT)
    @ApiOperation("导出报警列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Alarm alarm) {
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        ExcelUtil<Alarm> util = new ExcelUtil<Alarm>(Alarm.class);
        util.exportExcel(response, list, "报警数据");
    }

    /**
     * 获取报警详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:query')")
    @ApiOperation("获取报警详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "报警参数", required = true)
                              @PathVariable("id") Long id) {
        return success(alarmService.selectAlarmById(id));
    }

    /**
     * 新增报警
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:add')")
    @Log(title = "报警", businessType = BusinessType.INSERT)
    @ApiOperation("新增报警")
    @PostMapping
    public AjaxResult add(@RequestBody Alarm alarm) {
        return toAjax(alarmService.insertAlarm(alarm));
    }

    /**
     * 修改报警
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:edit')")
    @Log(title = "报警", businessType = BusinessType.UPDATE)
    @ApiOperation("修改报警")
    @PutMapping
    public AjaxResult edit(@RequestBody Alarm alarm) {
        return toAjax(alarmService.updateAlarm(alarm));
    }

    /**
     * 删除报警
     */
//    @PreAuthorize("@ss.hasPermi('system:alarm:remove')")
    @Log(title = "报警", businessType = BusinessType.DELETE)
    @ApiOperation("删除报警")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "报警ids参数", required = true)
                             @PathVariable Long[] ids) {
        return toAjax(alarmService.deleteAlarmByIds(ids));
    }

    /**
     * 报警数量
     */
    @ApiOperation("报警数量")
    @GetMapping("/alarmsNumber")
    public AjaxResult alarmsNumber() {
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.eq("alarm_status", 0);
        int count = alarmService.count(alarmQueryWrapper);
        return success(count);
    }

    /**
     * 报警数量
     */
    @ApiOperation("报警设备数量")
    @GetMapping("/alarmsDeviceNumber")
    public AjaxResult alarmsDeviceNumber() {
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.select("alarm_point");
        alarmQueryWrapper.eq("alarm_status", 0);
        alarmQueryWrapper.groupBy("alarm_point");
        List<Alarm> alarmList = alarmService.list(alarmQueryWrapper);
        return success(alarmList.size());
    }

    /**
     * 报警设备列表
     */
    @ApiOperation("报警设备列表")
    @GetMapping("/alarmDeviceList")
    public TableDataInfo alarmDeviceList() {
        startPage();
        Alarm alarm = new Alarm();
        alarm.setAlarmStatus(0);
        List<JSONObject> list = alarmService.selectAlarmDeviceList(alarm);
        TableDataInfo pageInfo = getDataTable(list);
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.select("alarm_point");
        alarmQueryWrapper.eq("alarm_status", 0);
        alarmQueryWrapper.groupBy("alarm_point");
        List<Alarm> alarmList = alarmService.list(alarmQueryWrapper);
        pageInfo.setTotal(alarmList.size());
        return pageInfo;
    }

    /**
     * 报警列表
     */
    @ApiOperation("报警列表")
    @GetMapping("/alarmList")
    public TableDataInfo alarmList(Long alarmPoint) {
        startPage();
        Alarm alarm = new Alarm();
        alarm.setAlarmStatus(0);
        alarm.setAlarmPoint(alarmPoint);
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        return getDataTable(list);
    }

    /**
     * 报警状态修改
     */
    @ApiOperation("报警状态修改")
    @GetMapping("/alarmStatusModification")
    public AjaxResult alarmStatusModification(Long id) {
        QueryWrapper<Alarm> alarmQueryWrapper = new QueryWrapper<>();
        alarmQueryWrapper.eq("id", id);
        Alarm alarm = alarmService.getOne(alarmQueryWrapper);
        alarm.setAlarmStatus(2);
        return toAjax(alarmService.updateAlarm(alarm));
    }

}
