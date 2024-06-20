package com.ruoyi.web.controller.basic.view.project;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.ProjectPlanTaskNameConfig;
import com.ruoyi.system.service.ProjectPlanTaskNameConfigService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 计划-进度任务分类配置表(ProjectPlanTaskNameConfig)表控制层
 */
@RestController
@RequestMapping("/projectPlanTaskNameConfig")
public class ProjectPlanTaskNameConfigController extends BaseController {

    @Resource
    private ProjectPlanTaskNameConfigService projectPlanTaskNameConfigService;

    /**
     * 分页查询
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        startPage();
        List<ProjectPlanTaskNameConfig> projectPlanTaskNameConfigList = projectPlanTaskNameConfigService.queryByPage(projectPlanTaskNameConfig);
        return getDataTable(projectPlanTaskNameConfigList);
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.projectPlanTaskNameConfigService.queryById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        return AjaxResult.success(this.projectPlanTaskNameConfigService.insert(projectPlanTaskNameConfig));
    }

    /**
     * 编辑数据
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ProjectPlanTaskNameConfig projectPlanTaskNameConfig) {
        return AjaxResult.success(this.projectPlanTaskNameConfigService.update(projectPlanTaskNameConfig));
    }

    /**
     * 删除数据
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.projectPlanTaskNameConfigService.deleteById(id));
    }

}

