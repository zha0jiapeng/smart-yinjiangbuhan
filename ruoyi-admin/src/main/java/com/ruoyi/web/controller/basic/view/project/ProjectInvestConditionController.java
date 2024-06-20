package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.basic.ProjectInvestCondition;
import com.ruoyi.system.service.ProjectInvestConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("basic/invest/condition")
public class ProjectInvestConditionController extends BaseController {

    @Resource
    private ProjectInvestConditionService projectInvestConditionService;

    @GetMapping("/list")
    public TableDataInfo queryByPage(ProjectInvestCondition projectInvestCondition) {
        startPage();
        List<ProjectInvestCondition> projectInvestAmounts = projectInvestConditionService.queryProjectInvestCondition(projectInvestCondition);
        return getDataTable(projectInvestAmounts);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProjectInvestCondition projectInvestCondition) {
        this.projectInvestConditionService.saveProjectInvestCondition(projectInvestCondition);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody ProjectInvestCondition projectInvestCondition) {
        this.projectInvestConditionService.updateProjectInvestCondition(projectInvestCondition);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody ProjectInvestCondition projectInvestCondition) {
        this.projectInvestConditionService.deleteProjectInvestCondition(projectInvestCondition);
        return AjaxResult.success();
    }

}
