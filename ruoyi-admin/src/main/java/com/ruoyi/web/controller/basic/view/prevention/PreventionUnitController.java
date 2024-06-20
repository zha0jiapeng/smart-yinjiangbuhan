package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.PreventionUnit;
import com.ruoyi.system.service.PreventionUnitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PreventionUnit)表控制层
 *
 * @author makejava
 * @since 2022-12-02 16:53:56
 */
@RestController
@RequestMapping("preventionUnit")
public class PreventionUnitController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionUnitService preventionUnitService;

    /**
     * 分页查询
     *
     * @param preventionUnit 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionUnit preventionUnit) {
        startPage();
        List<PreventionUnit> preventionUnits = this.preventionUnitService.queryByPage(preventionUnit);
        return getDataTable(preventionUnits);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionUnitService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionUnit 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionUnit preventionUnit) {
        return AjaxResult.success(this.preventionUnitService.insert(preventionUnit));
    }

    /**
     * 编辑数据
     *
     * @param preventionUnit 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionUnit preventionUnit) {
        return AjaxResult.success(this.preventionUnitService.update(preventionUnit));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionUnitService.deleteById(id));
    }

}

