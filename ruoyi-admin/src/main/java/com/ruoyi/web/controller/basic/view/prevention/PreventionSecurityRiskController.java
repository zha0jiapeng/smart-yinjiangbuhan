package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.PreventionSecurityRiskVO;
import com.ruoyi.system.entity.PreventionSecurityRisk;
import com.ruoyi.system.service.PreventionSecurityRiskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 双重预防-风险分析清单明细(PreventionSecurityRisk)表控制层
 *
 * @author makejava
 * @since 2022-11-17 11:18:53
 */
@RestController
@RequestMapping("/preventionSecurityRisk")
public class PreventionSecurityRiskController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionSecurityRiskService preventionSecurityRiskService;

    /**
     * 分页查询
     *
     * @param preventionSecurityRisk 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionSecurityRisk preventionSecurityRisk) {
        startPage();
        List<PreventionSecurityRisk> preventionSecurityRisks = preventionSecurityRiskService.queryByPage(preventionSecurityRisk);
        return getDataTable(preventionSecurityRisks);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionSecurityRiskService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionSecurityRisk 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionSecurityRisk preventionSecurityRisk) {
        return AjaxResult.success(this.preventionSecurityRiskService.insert(preventionSecurityRisk));
    }

    /**
     * 编辑数据
     *
     * @param preventionSecurityRisk 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionSecurityRisk preventionSecurityRisk) {
        return AjaxResult.success(this.preventionSecurityRiskService.update(preventionSecurityRisk));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionSecurityRiskService.deleteById(id));
    }

    @Log(title = "风险分析清单(", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception{
        ExcelUtil<PreventionSecurityRiskVO> util = new ExcelUtil<>(PreventionSecurityRiskVO.class);
        List<PreventionSecurityRiskVO> preventionSecurityRisks= util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = preventionSecurityRiskService.importExcel(preventionSecurityRisks, updateSupport, operName);
        return success(message);
    }


    @Log(title = "风险分析清单(", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PreventionSecurityRiskVO preventionSecurityRisk)
    {
        List<PreventionSecurityRiskVO> list = preventionSecurityRiskService.queryAllVO(preventionSecurityRisk);
        ExcelUtil<PreventionSecurityRiskVO> util = new ExcelUtil<>(PreventionSecurityRiskVO.class);
        util.exportExcel(response, list, "风险分析清单(");
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<PreventionSecurityRiskVO> util = new ExcelUtil<>(PreventionSecurityRiskVO.class);
        util.importTemplateExcel(response,"风险分析清单(");
    }


}

