package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.service.ProjectCalculateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 三维进度统计数据(ProjectCalculate)表控制层
 */
@RestController
@RequestMapping("projectCalculate")
public class ProjectCalculateController extends BaseController {
    @Resource
    private ProjectCalculateService projectCalculateService;

    /**
     * 分页查询
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(ProjectCalculate projectCalculate) {
        startPage();
        List<ProjectCalculate> projectCalculateList = projectCalculateService.queryByPage(projectCalculate);
        return getDataTable(projectCalculateList);
    }

    /**
     * 通过主键查询单条数据
     * @param id 主键
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.projectCalculateService.queryById(id));
    }

    /**
     * 新增数据
     * @param projectCalculate 实体
     */
    @PostMapping("/add")
    public AjaxResult add(ProjectCalculate projectCalculate) {
        return AjaxResult.success(this.projectCalculateService.insert(projectCalculate));
    }

    /**
     * 编辑数据
     * @param projectCalculate 实体
     */
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody ProjectCalculate projectCalculate) {
        //自动变成已审核
        projectCalculate.setCheckState(3L);
        projectCalculate.setCheckBy(getUsername());
        projectCalculate.setCheckDate(new Date());
        return AjaxResult.success(this.projectCalculateService.update(projectCalculate));
    }

    /**
     * 删除数据
     * @param id 主键
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.projectCalculateService.deleteById(id));
    }
}

