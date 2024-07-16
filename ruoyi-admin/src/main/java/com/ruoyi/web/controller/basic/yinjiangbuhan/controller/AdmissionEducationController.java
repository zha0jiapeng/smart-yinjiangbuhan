package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducation;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入场三级教育Controller
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@RestController
@RequestMapping("/education")
public class AdmissionEducationController extends BaseController
{
    @Autowired
    private IAdmissionEducationService admissionEducationService;

    /**
     * 查询入场三级教育列表
     */
    //@PreAuthorize("@ss.hasPermi('system:education:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdmissionEducation admissionEducation)
    {
        startPage();
        List<AdmissionEducation> list = admissionEducationService.selectAdmissionEducationList(admissionEducation);
        return getDataTable(list);
    }

    /**
     * 导出入场三级教育列表
     */
    //@PreAuthorize("@ss.hasPermi('system:education:export')")
    @Log(title = "入场三级教育", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AdmissionEducation admissionEducation)
    {
        List<AdmissionEducation> list = admissionEducationService.selectAdmissionEducationList(admissionEducation);
        ExcelUtil<AdmissionEducation> util = new ExcelUtil<AdmissionEducation>(AdmissionEducation.class);
        util.exportExcel(response, list, "入场三级教育数据");
    }

    /**
     * 获取入场三级教育详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:education:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(admissionEducationService.selectAdmissionEducationById(id));
    }

    /**
     * 新增入场三级教育
     */
    //@PreAuthorize("@ss.hasPermi('system:education:add')")
    @Log(title = "入场三级教育", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdmissionEducation admissionEducation)
    {
        return toAjax(admissionEducationService.insertAdmissionEducation(admissionEducation));
    }

    /**
     * 修改入场三级教育
     */
  //  @PreAuthorize("@ss.hasPermi('system:education:edit')")
    @Log(title = "入场三级教育", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AdmissionEducation admissionEducation)
    {
        return toAjax(admissionEducationService.updateAdmissionEducation(admissionEducation));
    }

    /**
     * 删除入场三级教育
     */
   // @PreAuthorize("@ss.hasPermi('system:education:remove')")
    @Log(title = "入场三级教育", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(admissionEducationService.deleteAdmissionEducationByIds(ids));
    }
}
