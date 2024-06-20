package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.PreventionCheckTaskConfigVO;
import com.ruoyi.system.entity.PreventionCheckTaskConfig;
import com.ruoyi.system.service.PreventionCheckTaskConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 双重预防-排查任务配置表(PreventionCheckTaskConfig)表控制层
 *
 * @author makejava
 * @since 2022-11-18 13:58:19
 */
@RestController
@RequestMapping("preventionCheckTaskConfig")
public class PreventionCheckTaskConfigController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionCheckTaskConfigService preventionCheckTaskConfigService;

    /**
     * 分页查询
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionCheckTaskConfigVO preventionCheckTaskConfigVO) {
        startPage();
        List<PreventionCheckTaskConfigVO> preventionCheckTaskConfigs = this.preventionCheckTaskConfigService.queryByPage(preventionCheckTaskConfigVO);
        return getDataTable(preventionCheckTaskConfigs);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionCheckTaskConfigService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionCheckTaskConfig 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionCheckTaskConfig preventionCheckTaskConfig) {
        return AjaxResult.success(this.preventionCheckTaskConfigService.insert(preventionCheckTaskConfig));
    }

    /**
     * 编辑数据
     *
     * @param preventionCheckTaskConfig 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionCheckTaskConfig preventionCheckTaskConfig) {
        return AjaxResult.success(this.preventionCheckTaskConfigService.update(preventionCheckTaskConfig));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionCheckTaskConfigService.deleteById(id));
    }

    /**
     * 配置
     *
     * @param preventionCheckTaskConfig 实体
     * @return 编辑结果
     */
    @PostMapping("/updateWhetherConfig")
    public AjaxResult updateWhetherConfig(@RequestBody PreventionCheckTaskConfig preventionCheckTaskConfig) {
        preventionCheckTaskConfig.setWhetherConfig(0);
        return AjaxResult.success(this.preventionCheckTaskConfigService.update(preventionCheckTaskConfig));
    }


    /**
     * 发布
     *
     * @param preventionCheckTaskConfig 实体
     * @return 编辑结果
     */
    @PostMapping("/updateWhetherRelease")
    public AjaxResult updateWhetherRelease(@RequestBody PreventionCheckTaskConfig preventionCheckTaskConfig) {
        preventionCheckTaskConfig.setWhetherRelease(0);
        return AjaxResult.success(this.preventionCheckTaskConfigService.update(preventionCheckTaskConfig));
    }

}

