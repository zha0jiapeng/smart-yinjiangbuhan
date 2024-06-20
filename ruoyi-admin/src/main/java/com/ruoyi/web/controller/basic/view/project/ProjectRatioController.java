package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.ProjectRatio;
import com.ruoyi.system.service.ProjectRatioService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 三维_累计比例(ProjectRatio)表控制层
 *
 * @since 2023-06-07 17:55:22
 */
@RestController
@RequestMapping("/projectRatio")
public class ProjectRatioController extends BaseController {

    @Resource
    private ProjectRatioService projectRatioService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping
    public TableDataInfo queryByPage(ProjectRatio projectRatio) {
        startPage();
        List<ProjectRatio> projectRatioList = this.projectRatioService.queryByPage(projectRatio);
        return getDataTable(projectRatioList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.projectRatioService.queryById(id));
    }

    @GetMapping("/yesterday")
    public AjaxResult yesterday() {
        //设置格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //创建Calendar 的实例
        Calendar calendar = Calendar.getInstance();
        //当前时间减去一天，即一天前的时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        ProjectRatio projectRatio = new ProjectRatio();
        projectRatio.setCumulativeDate(simpleDateFormat.format(calendar.getTime()));
        List<ProjectRatio> projectRatios = projectRatioService.queryByPage(projectRatio);
        if (CollectionUtils.isEmpty(projectRatios)) {
            return AjaxResult.success(new ProjectRatio());
        }

        ProjectRatio ratio = projectRatios.stream().findFirst().orElse(new ProjectRatio());
        return AjaxResult.success(ratio);
    }

    @GetMapping("/details")
    public AjaxResult details() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String moon = simpleDateFormat.format(new Date());
        ProjectRatio projectRatio = new ProjectRatio();
        projectRatio.setCumulativeDate(moon);
        List<ProjectRatio> projectRatios = projectRatioService.queryByPage(projectRatio);
        if (CollectionUtils.isEmpty(projectRatios)) {
            return AjaxResult.success(new ProjectRatio());
        }

        ProjectRatio ratio = projectRatios.stream().findFirst().orElse(new ProjectRatio());
        return AjaxResult.success(ratio);
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProjectRatio projectRatio) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        projectRatio.setCumulativeDate(simpleDateFormat.format(new Date()));
        return AjaxResult.success(this.projectRatioService.insert(projectRatio));
    }

    /**
     * 编辑数据
     *
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ProjectRatio projectRatio) {
        return AjaxResult.success(this.projectRatioService.update(projectRatio));
    }


    /**
     * 编辑数据
     *
     * @return 编辑结果
     */
    @PostMapping("/update/base")
    public AjaxResult update(@RequestBody ProjectRatio projectRatio) {
        return AjaxResult.success(this.projectRatioService.updateBase(projectRatio));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.projectRatioService.deleteById(id));
    }

}

