package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationUserService;
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

import java.util.List;

/**
 * 入场三级教育用户Controller
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@RestController
@RequestMapping("/educationUser")
public class AdmissionEducationUserController extends BaseController
{
    @Autowired
    private IAdmissionEducationUserService admissionEducationUserService;

    /**
     * 查询入场三级教育用户列表
     */
    //@PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdmissionEducationUser admissionEducationUser)
    {
        startPage();
        List<AdmissionEducationUser> list = admissionEducationUserService.selectAdmissionEducationUserList(admissionEducationUser);
        return getDataTable(list);
    }

    /**
     * 导出入场三级教育用户列表
     */
    //@PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "入场三级教育用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AdmissionEducationUser admissionEducationUser)
    {
        List<AdmissionEducationUser> list = admissionEducationUserService.selectAdmissionEducationUserList(admissionEducationUser);
        ExcelUtil<AdmissionEducationUser> util = new ExcelUtil<AdmissionEducationUser>(AdmissionEducationUser.class);
        util.exportExcel(response, list, "入场三级教育用户数据");
    }

    /**
     * 获取入场三级教育用户详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(admissionEducationUserService.selectAdmissionEducationUserById(id));
    }

    /**
     * 新增入场三级教育用户
     */
   // @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "入场三级教育用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdmissionEducationUser admissionEducationUser)
    {
        return toAjax(admissionEducationUserService.insertAdmissionEducationUser(admissionEducationUser));
    }

    /**
     * 修改入场三级教育用户
     */
   // @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "入场三级教育用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AdmissionEducationUser admissionEducationUser)
    {
        return toAjax(admissionEducationUserService.updateAdmissionEducationUser(admissionEducationUser));
    }

    /**
     * 删除入场三级教育用户
     */
   // @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "入场三级教育用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(admissionEducationUserService.deleteAdmissionEducationUserByIds(ids));
    }
}
