package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IMigrantWorkerPayrollService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
@Api(tags={"薪资 Controller"})
public class MigrantWorkerPayrollController extends BaseController
{
    @Autowired
    private IMigrantWorkerPayrollService migrantWorkerPayrollService;

    @Resource
    private SysWorkPeopleService workPeopleService;

    /**
     * 查询薪资列表
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:list')")
    @GetMapping("/list")
    @ApiOperation("查询薪资列表")
    public TableDataInfo list(MigrantWorkerPayroll migrantWorkerPayroll)
    {
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
    public void export(HttpServletResponse response, MigrantWorkerPayroll migrantWorkerPayroll)
    {
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
            @PathVariable("id") Long id)
    {
        return success(migrantWorkerPayrollService.selectMigrantWorkerPayrollById(id));
    }

    /**
     * 新增薪资
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:add')")
    @Log(title = "薪资", businessType = BusinessType.INSERT)
    @ApiOperation("新增薪资")
    @PostMapping
    public AjaxResult add(@RequestBody MigrantWorkerPayroll migrantWorkerPayroll)
    {
        return migrantWorkerPayrollService.insertMigrantWorkerPayroll(migrantWorkerPayroll);
    }

    /**
     * 修改薪资
     */
    @PreAuthorize("@ss.hasPermi('system:payroll:edit')")
    @Log(title = "薪资", businessType = BusinessType.UPDATE)
    @ApiOperation("修改薪资")
    @PutMapping
    public AjaxResult edit(@RequestBody MigrantWorkerPayroll migrantWorkerPayroll)
    {
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
            @PathVariable Long[] ids)
    {
        return toAjax(migrantWorkerPayrollService.deleteMigrantWorkerPayrollByIds(ids));
    }



    /**
     * 查询发放时间薪资列表
     */
    @GetMapping("/paymentDateList")
    @ApiOperation("查询薪资列表")
    public TableDataInfo paymentDateList(MigrantWorkerPayroll migrantWorkerPayroll)
    {
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
    public void paymentDateExport(HttpServletResponse response, MigrantWorkerPayroll migrantWorkerPayroll)
    {
        List<MigrantWorkerPayroll> list = migrantWorkerPayrollService.selectMigrantWorkerPayrollPaymentDateList(migrantWorkerPayroll);
        System.out.println("数据条数list："+list.size());
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
                Long sysWorkPeopleId = Long.valueOf(dataFormatter.formatCellValue(row.getCell(1)));
                String phone = dataFormatter.formatCellValue(row.getCell(2));
                String userName = dataFormatter.formatCellValue(row.getCell(3));
                String month = dataFormatter.formatCellValue(row.getCell(4));
                BigDecimal grossSalary = BigDecimal.valueOf(Long.valueOf(dataFormatter.formatCellValue(row.getCell(5))));
                BigDecimal netSalary = BigDecimal.valueOf(Long.valueOf(dataFormatter.formatCellValue(row.getCell(6))));
                String paymentDate = dataFormatter.formatCellValue(row.getCell(7));
                String payrollNumber = dataFormatter.formatCellValue(row.getCell(8));
                String workType = dataFormatter.formatCellValue(row.getCell(9));
                String idCard = dataFormatter.formatCellValue(row.getCell(10));
                String company = dataFormatter.formatCellValue(row.getCell(11));
                migrantWorkerPayroll.setSysWorkPeopleId(sysWorkPeopleId);
                migrantWorkerPayroll.setPhone(phone);
                migrantWorkerPayroll.setUserName(userName);
                migrantWorkerPayroll.setMonth(month);
                migrantWorkerPayroll.setGrossSalary(grossSalary);
                migrantWorkerPayroll.setNetSalary(netSalary);
                migrantWorkerPayroll.setPaymentDate(paymentDate);
                migrantWorkerPayroll.setPayrollNumber(payrollNumber);
                migrantWorkerPayroll.setWorkType(workType);
                migrantWorkerPayroll.setIdCard(idCard);
                migrantWorkerPayroll.setCompany(company);

                LambdaQueryWrapper<SysWorkPeople> sysWorkPeopleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysWorkPeopleLambdaQueryWrapper.eq(migrantWorkerPayroll.getSysWorkPeopleId() != null, SysWorkPeople::getId, migrantWorkerPayroll.getSysWorkPeopleId());
                sysWorkPeopleLambdaQueryWrapper.eq(StringUtils.isNotEmpty(migrantWorkerPayroll.getPhone()), SysWorkPeople::getPhone, migrantWorkerPayroll.getPhone());
                sysWorkPeopleLambdaQueryWrapper.eq(StringUtils.isNotEmpty(migrantWorkerPayroll.getUserName()), SysWorkPeople::getName, migrantWorkerPayroll.getUserName());
                sysWorkPeopleLambdaQueryWrapper.eq(StringUtils.isNotEmpty(migrantWorkerPayroll.getIdCard()), SysWorkPeople::getIdCard, migrantWorkerPayroll.getIdCard());
                SysWorkPeople sysWorkPeople = workPeopleService.getOne(sysWorkPeopleLambdaQueryWrapper);
                if (sysWorkPeople == null) {
                    return AjaxResult.error("人员信息缺失");
                }
                migrantWorkerPayrollArrayList.add(migrantWorkerPayroll);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 根据需要处理异常
        }
        Boolean result = migrantWorkerPayrollService.saveMigrantWorkerPayrollList(migrantWorkerPayrollArrayList);
        if (result) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

}
