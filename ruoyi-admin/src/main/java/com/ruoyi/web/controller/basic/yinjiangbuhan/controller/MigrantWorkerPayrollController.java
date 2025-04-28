package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IMigrantWorkerPayrollService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 薪资Controller
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@RestController
@RequestMapping("/migrant/worker/payroll")
@Api(tags = {"薪资 Controller"})
public class MigrantWorkerPayrollController extends BaseController {
    @Autowired
    private IMigrantWorkerPayrollService migrantWorkerPayrollService;

    @Resource
    private SysWorkPeopleService workPeopleService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    /**
     * 查询薪资列表
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:list')")
    @GetMapping("/list")
    @ApiOperation("查询薪资列表")
    public TableDataInfo list(MigrantWorkerPayroll migrantWorkerPayroll) {
        startPage();
        List<MigrantWorkerPayroll> list = migrantWorkerPayrollService.selectMigrantWorkerPayrollList(migrantWorkerPayroll);
        return getDataTable(list);
    }

    /**
     * 导出薪资列表
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:export')")
    @Log(title = "薪资", businessType = BusinessType.EXPORT)
    @ApiOperation("导出薪资列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, MigrantWorkerPayroll migrantWorkerPayroll) {
        List<MigrantWorkerPayroll> list = migrantWorkerPayrollService.selectMigrantWorkerPayrollList(migrantWorkerPayroll);
        ExcelUtil<MigrantWorkerPayroll> util = new ExcelUtil<MigrantWorkerPayroll>(MigrantWorkerPayroll.class);
        util.exportExcel(response, list, "薪资数据");
    }

    /**
     * 获取薪资详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:query')")
    @ApiOperation("获取薪资详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "薪资参数", required = true)
                              @PathVariable("id") Long id) {
        return success(migrantWorkerPayrollService.selectMigrantWorkerPayrollById(id));
    }

    /**
     * 新增薪资
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:add')")
    @Log(title = "薪资", businessType = BusinessType.INSERT)
    @ApiOperation("新增薪资")
    @PostMapping
    public AjaxResult add(@RequestBody MigrantWorkerPayroll migrantWorkerPayroll) {
        if (StringUtils.isNotEmpty(migrantWorkerPayroll.getMonth())) {
            migrantWorkerPayroll.setMonth(migrantWorkerPayroll.getMonth() + "-01");
        }
        pushSWZK(migrantWorkerPayroll);
        AjaxResult ajaxResult = migrantWorkerPayrollService.insertMigrantWorkerPayroll(migrantWorkerPayroll);
        return ajaxResult;
    }

    /**
     * 修改薪资
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:edit')")
    @Log(title = "薪资", businessType = BusinessType.UPDATE)
    @ApiOperation("修改薪资")
    @PutMapping
    public AjaxResult edit(@RequestBody MigrantWorkerPayroll migrantWorkerPayroll) {
        return toAjax(migrantWorkerPayrollService.updateMigrantWorkerPayroll(migrantWorkerPayroll));
    }

    /**
     * 删除薪资
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:remove')")
    @Log(title = "薪资", businessType = BusinessType.DELETE)
    @ApiOperation("删除薪资")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "薪资ids参数", required = true)
                             @PathVariable Long[] ids) {
        return toAjax(migrantWorkerPayrollService.deleteMigrantWorkerPayrollByIds(ids));
    }


    /**
     * 查询发放时间薪资列表
     */
    @GetMapping("/paymentDateList")
    @ApiOperation("查询薪资列表")
    public TableDataInfo paymentDateList(MigrantWorkerPayroll migrantWorkerPayroll) {
        startPage();
        List<MigrantWorkerPayroll> list = migrantWorkerPayrollService.selectMigrantWorkerPayrollPaymentDateList(migrantWorkerPayroll);
        return getDataTable(list);
    }


    /**
     * 导出发放时间薪资列表
     */
//    @PreAuthorize("@ss.hasPermi('system:payroll:export')")
//    @Log(title = "薪资", businessType = BusinessType.EXPORT)
//    @ApiOperation("导出发放时间薪资列表Excel")
    @PostMapping("/paymentDate/export")
    public void paymentDateExport(HttpServletResponse response, MigrantWorkerPayroll migrantWorkerPayroll) {
        List<MigrantWorkerPayroll> list = migrantWorkerPayrollService.selectMigrantWorkerPayrollPaymentDateList(migrantWorkerPayroll);
        System.out.println("数据条数list：" + list.size());
        ExcelUtil<MigrantWorkerPayroll> util = new ExcelUtil<MigrantWorkerPayroll>(MigrantWorkerPayroll.class);
        util.exportExcel(response, list, "薪资数据");
    }

    /**
     * 导入农民工薪资列表
     */
//    @PreAuthorize("@ss.hasPermi('system:ledger:import')")
    @Log(title = "农民工薪资", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        List<MigrantWorkerPayroll> migrantWorkerPayrollArrayList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            // 跳过标题行（如果存在）
            if (rowIterator.hasNext()) {
                rowIterator.next();
                rowIterator.next();
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                MigrantWorkerPayroll migrantWorkerPayroll = new MigrantWorkerPayroll();
                String userName = String.valueOf(dataFormatter.formatCellValue(row.getCell(1)));
                String idCard = String.valueOf(dataFormatter.formatCellValue(row.getCell(2)));
                String month = String.valueOf(dataFormatter.formatCellValue(row.getCell(3)));
                String workDay = String.valueOf(dataFormatter.formatCellValue(row.getCell(4)));
                String overWorkDay = String.valueOf(dataFormatter.formatCellValue(row.getCell(5)));
                String wages = String.valueOf(dataFormatter.formatCellValue(row.getCell(6)));
                String giveOutStatus = String.valueOf(dataFormatter.formatCellValue(row.getCell(7)));
                String giveOutDate = String.valueOf(dataFormatter.formatCellValue(row.getCell(8)));
                String isTpgrant = String.valueOf(dataFormatter.formatCellValue(row.getCell(9)));

                migrantWorkerPayroll.setUserName(userName);
                migrantWorkerPayroll.setIdCard(idCard);
                migrantWorkerPayroll.setMonth(month);
                if (StringUtils.isNotEmpty(month)) {
                    migrantWorkerPayroll.setMonth(month + "-01");
                }
                migrantWorkerPayroll.setWorkDay(workDay);
                migrantWorkerPayroll.setOverWorkDay(overWorkDay);
                migrantWorkerPayroll.setWages(wages);
                migrantWorkerPayroll.setGiveOutStatus(giveOutStatus);
                migrantWorkerPayroll.setGiveOutDate(giveOutDate);
                migrantWorkerPayroll.setIsTpgrant(isTpgrant);
                migrantWorkerPayroll.setCreateTime(DateUtils.getNowDate());
                migrantWorkerPayroll.setDelFlag("0");


                //推送
                pushSWZK(migrantWorkerPayroll);


                migrantWorkerPayrollArrayList.add(migrantWorkerPayroll);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 根据需要处理异常
            System.out.println("人员导入数据：异常");
        }
        System.out.println("人员导入数据：" + migrantWorkerPayrollArrayList.toString());
        Boolean result = false;
        if (migrantWorkerPayrollArrayList.size() != 0) {
            result = migrantWorkerPayrollService.saveMigrantWorkerPayrollList(migrantWorkerPayrollArrayList);
        }
        if (result) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }





    public void pushSWZK(MigrantWorkerPayroll migrantWorkerPayroll) {
        // 创建根 Map
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("deviceType", "2001000102");
        rootMap.put("SN", "RY01024718004");
        rootMap.put("dataType", "200100033");
        rootMap.put("wordAreaCode", "");
        rootMap.put("workSurface", "");
        rootMap.put("deviceName", "施工四标智慧工地系统");
        rootMap.put("managementDept", "");

        // 创建 values 列表
        List<Map<String, Object>> values = new ArrayList<>();

        // 创建 values 中的第一个对象
        Map<String, Object> value = new HashMap<>();
        value.put("reportTs", DateUtil.current());

        // 创建 properties 对象
        Map<String, Object> properties = new HashMap<>();

        // 创建 staff 列表
        List<Map<String, Object>> staff = new ArrayList<>();

        // 创建 staff 中的第一个对象
        Map<String, Object> staffMember = new HashMap<>();
        staffMember.put("idCardNo", migrantWorkerPayroll.getIdCard());
        staffMember.put("name", migrantWorkerPayroll.getUserName());
        staffMember.put("month", migrantWorkerPayroll.getMonth());
        staffMember.put("workDay", migrantWorkerPayroll.getWorkDay());
        staffMember.put("overWorkDay", migrantWorkerPayroll.getOverWorkDay());
        staffMember.put("wages", migrantWorkerPayroll.getWages());
        staffMember.put("giveOutStatus", migrantWorkerPayroll.getGiveOutStatus());
        staffMember.put("giveOutDate", migrantWorkerPayroll.getGiveOutDate());
        staffMember.put("isTpgrant", migrantWorkerPayroll.getIsTpgrant());

        // 将 staffMember 添加到 staff 列表
        staff.add(staffMember);

        // 将 staff 列表添加到 properties 对象
        properties.put("staff", staff);

        // 将 properties 对象添加到 value 对象
        value.put("properties", properties);

        // 将 value 对象添加到 values 列表
        values.add(value);

        // 将 values 列表添加到根对象
        rootMap.put("values", values);


        swzkHttpUtils.pushIOT(rootMap);
    }


}
