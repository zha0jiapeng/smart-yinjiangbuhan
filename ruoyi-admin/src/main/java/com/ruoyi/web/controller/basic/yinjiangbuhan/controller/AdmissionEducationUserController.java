package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SysWorkPeopleService workPeopleService;

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

    @GetMapping("/coverage")
    public AjaxResult coverage()
    {
        Map<String,Object> response = new HashMap<>();
        response.put("preshiftEducation",100);
        Integer count3 = admissionEducationUserService.coverage();
        int countTotal = workPeopleService.count(new LambdaQueryWrapper<SysWorkPeople>().eq(SysWorkPeople::getPersonnelConfigType, 4).ne(SysWorkPeople::getWorkType,"管理人员").eq(SysWorkPeople::getYn,1));
        response.put("threeLevelEducation",new BigDecimal(count3).divide(new BigDecimal(countTotal),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(0,RoundingMode.HALF_UP));
        return AjaxResult.success(response);
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
