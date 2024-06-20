package com.ruoyi.web.controller.basic.view.qualityManager;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.QualityTemplate;
import com.ruoyi.system.service.QualityTemplateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 质量模板表(QualityTemplate)表控制层
 *
 * @author makejava
 * @since 2022-12-25 14:48:58
 */
@RestController
@RequestMapping("qualityTemplate")
public class QualityTemplateController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private QualityTemplateService qualityTemplateService;

    /**
     * 分页查询
     *
     * @param qualityTemplate 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(QualityTemplate qualityTemplate) {
        startPage();
        List<QualityTemplate> qualityTemplates = this.qualityTemplateService.queryByPage(qualityTemplate);
        return getDataTable(qualityTemplates);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.qualityTemplateService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qualityTemplate 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody QualityTemplate qualityTemplate) {
        return AjaxResult.success(this.qualityTemplateService.insert(qualityTemplate));
    }

    /**
     * 编辑数据
     *
     * @param qualityTemplate 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody QualityTemplate qualityTemplate) {
        return AjaxResult.success(this.qualityTemplateService.update(qualityTemplate));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.qualityTemplateService.deleteById(id));
    }

}

