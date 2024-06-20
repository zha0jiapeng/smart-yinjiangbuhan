package com.ruoyi.web.controller.basic.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ProjectAttendance;
import com.ruoyi.system.domain.WorkDateStorage;
import com.ruoyi.system.service.WorkDateStorageService;
import com.ruoyi.system.service.impl.IotStaffAttendanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("work/data/storage")
public class WorkDateStorageController extends BaseController {

    @Resource
    private WorkDateStorageService workDateStorageService;
    @Resource
    private IotStaffAttendanceServiceImpl iotStaffAttendanceService;

    @GetMapping("/list")
    public TableDataInfo list(WorkDateStorage workDateStorage) {
        startPage();
        QueryWrapper<WorkDateStorage> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(workDateStorage.getPhone())) {
            queryWrapper.eq("phone", workDateStorage.getPhone());
        }
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<WorkDateStorage> listPage = workDateStorageService.list(queryWrapper);
        return getDataTable(listPage);
    }

    @GetMapping("/list/v2")
    public AjaxResult list(ProjectAttendance projectAttendance) {
        return AjaxResult.success(workDateStorageService.attendance(projectAttendance));
    }

    @Log(title = "人员考勤", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<WorkDateStorage> util = new ExcelUtil<>(WorkDateStorage.class);
        List<WorkDateStorage> workDateStorages = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = workDateStorageService.importExcel(workDateStorages, updateSupport, operName);
        return success(message);
    }

    @Log(title = "人员考勤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkDateStorage workDateStorage) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<WorkDateStorage> list = workDateStorageService.list();

        if (!CollectionUtils.isEmpty(list)) {
            for (WorkDateStorage var : list) {
                var.setExportStartDate(simpleDateFormat.format(var.getStartDate()));
                var.setExportEndDate(simpleDateFormat.format(var.getEndDate()));
            }
        }

        ExcelUtil<WorkDateStorage> util = new ExcelUtil<>(WorkDateStorage.class);
        util.exportExcel(response, list, "员工考勤");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<WorkDateStorage> util = new ExcelUtil<>(WorkDateStorage.class);
        util.importTemplateExcel(response, "员工考勤");
    }

    @PostMapping("/kaoqin/test")
    public AjaxResult kaoqinTest() {
        iotStaffAttendanceService.saveScheduled(null, null);
        return AjaxResult.success();
    }

}
