package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.EmployeeCertificates;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IEmployeeCertificatesService;
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
 * 人员证照Controller
 * 
 * @author ruoyi
 * @date 2025-03-10
 */
@RestController
@RequestMapping("/employee/certificates")
@Api(tags={"人员证照 Controller"})
public class EmployeeCertificatesController extends BaseController
{
    @Autowired
    private IEmployeeCertificatesService employeeCertificatesService;

    /**
     * 查询人员证照列表
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:list')")
    @GetMapping("/list")
    @ApiOperation("查询人员证照列表")
    public TableDataInfo list(EmployeeCertificates employeeCertificates)
    {
        startPage();
        List<EmployeeCertificates> list = employeeCertificatesService.selectEmployeeCertificatesList(employeeCertificates);
        return getDataTable(list);
    }

    /**
     * 导出人员证照列表
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:export')")
    @Log(title = "人员证照", businessType = BusinessType.EXPORT)
    @ApiOperation("导出人员证照列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmployeeCertificates employeeCertificates)
    {
        List<EmployeeCertificates> list = employeeCertificatesService.selectEmployeeCertificatesList(employeeCertificates);
        ExcelUtil<EmployeeCertificates> util = new ExcelUtil<EmployeeCertificates>(EmployeeCertificates.class);
        util.exportExcel(response, list, "人员证照数据");
    }

    /**
     * 获取人员证照详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:query')")
    @ApiOperation("获取人员证照详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "人员证照参数", required = true)
            @PathVariable("id") Long id)
    {
        return success(employeeCertificatesService.selectEmployeeCertificatesById(id));
    }

    /**
     * 新增人员证照
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:add')")
    @Log(title = "人员证照", businessType = BusinessType.INSERT)
    @ApiOperation("新增人员证照")
    @PostMapping
    public AjaxResult add(@RequestBody EmployeeCertificates employeeCertificates)
    {
        Long sysWorkPeopleId = employeeCertificates.getSysWorkPeopleId();
        if (sysWorkPeopleId == null || sysWorkPeopleId == 0L) {
            return AjaxResult.error("无人员信息");
        }
        return toAjax(employeeCertificatesService.insertEmployeeCertificates(employeeCertificates));
    }

    /**
     * 修改人员证照
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:edit')")
    @Log(title = "人员证照", businessType = BusinessType.UPDATE)
    @ApiOperation("修改人员证照")
    @PutMapping
    public AjaxResult edit(@RequestBody EmployeeCertificates employeeCertificates)
    {
        return toAjax(employeeCertificatesService.updateEmployeeCertificates(employeeCertificates));
    }

    /**
     * 删除人员证照
     */
    @PreAuthorize("@ss.hasPermi('system:certificates:remove')")
    @Log(title = "人员证照", businessType = BusinessType.DELETE)
    @ApiOperation("删除人员证照")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "人员证照ids参数", required = true)
            @PathVariable Long[] ids)
    {
        return toAjax(employeeCertificatesService.deleteEmployeeCertificatesByIds(ids));
    }


    /**
     * 计算即将过期和已过期的证书数量
     */
    @ApiOperation("计算即将过期和已过期的证书数量")
    @GetMapping(value = "/calculateDueAndOverdueCount")
    public AjaxResult calculateDueAndOverdueCount()
    {
        return success(employeeCertificatesService.calculateDueAndOverdueCount());
    }
}
