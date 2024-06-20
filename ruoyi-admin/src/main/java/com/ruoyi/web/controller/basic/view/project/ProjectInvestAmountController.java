package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.basic.ProjectInvestAmount;
import com.ruoyi.system.service.ProjectInvestAmountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("project/invest/amount")
public class ProjectInvestAmountController extends BaseController {

    @Resource
    private ProjectInvestAmountService projectInvestAmountService;

    @GetMapping("/list")
    public TableDataInfo queryByPage(ProjectInvestAmount projectInvestAmount) {
        startPage();
        List<ProjectInvestAmount> projectInvestAmounts = projectInvestAmountService.queryAllProjectInvestAmount(projectInvestAmount);
        return getDataTable(projectInvestAmounts);
    }


    @GetMapping("/one")
    public AjaxResult one() {
        ProjectInvestAmount projectInvestAmount = new ProjectInvestAmount();
        projectInvestAmount = this.projectInvestAmountService.queryOneProjectInvestAmount(projectInvestAmount);
        return AjaxResult.success(projectInvestAmount);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProjectInvestAmount projectInvestAmount) {
        this.projectInvestAmountService.insertProjectInvestAmountService(projectInvestAmount);
        return AjaxResult.success();
    }

}
