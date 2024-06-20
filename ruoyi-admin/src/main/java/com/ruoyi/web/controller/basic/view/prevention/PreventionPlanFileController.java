package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.PreventionPlanFile;
import com.ruoyi.system.service.PreventionPlanFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PreventionPlanFile)表控制层
 *
 * @author makejava
 * @since 2022-12-17 13:22:15
 */
@RestController
@RequestMapping("preventionPlanFile")
public class PreventionPlanFileController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionPlanFileService preventionPlanFileService;

    /**
     * 分页查询
     *
     * @param preventionPlanFile 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionPlanFile preventionPlanFile) {
        startPage();
        List<PreventionPlanFile> preventionPlanFiles = this.preventionPlanFileService.queryByPage(preventionPlanFile);
        return getDataTable(preventionPlanFiles);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionPlanFileService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionPlanFile 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionPlanFile preventionPlanFile) {
        return AjaxResult.success(this.preventionPlanFileService.insert(preventionPlanFile));
    }

    /**
     * 编辑数据
     *
     * @param preventionPlanFile 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionPlanFile preventionPlanFile) {
        return AjaxResult.success(this.preventionPlanFileService.update(preventionPlanFile));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionPlanFileService.deleteById(id));
    }

}

