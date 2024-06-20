package com.ruoyi.web.controller.basic.view.inspection;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.InspectionProject;
import com.ruoyi.system.service.InspectionProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionProject)表控制层
 *
 * @author makejava
 * @since 2022-11-25 17:10:49
 */
@RestController
@RequestMapping("inspectionProject")
public class InspectionProjectController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private InspectionProjectService inspectionProjectService;

    /**
     * 分页查询
     *
     * @param inspectionProject 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(InspectionProject inspectionProject) {
        startPage();
        List<InspectionProject> inspectionProjects = this.inspectionProjectService.queryByPage(inspectionProject);
        return getDataTable(inspectionProjects);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.inspectionProjectService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param inspectionProject 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody InspectionProject inspectionProject) {
        return AjaxResult.success(this.inspectionProjectService.insert(inspectionProject));
    }

    /**
     * 编辑数据
     *
     * @param inspectionProject 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody InspectionProject inspectionProject) {
        return AjaxResult.success(this.inspectionProjectService.update(inspectionProject));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.inspectionProjectService.deleteById(id));
    }

}

