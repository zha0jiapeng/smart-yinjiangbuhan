package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.entity.ProjectConstruction;
import com.ruoyi.system.service.ProjectConstructionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目施工报表(ProjectConstruction)表控制层
 *
 * @since 2023-06-01 14:05:20
 */
@RestController
@RequestMapping("projectConstruction")
public class ProjectConstructionController extends BaseController {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ProjectConstructionService projectConstructionService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(ProjectConstruction projectConstruction) {
        startPage();
        List<ProjectConstruction> projectConstructionList = projectConstructionService.queryByPage(projectConstruction);
        return getDataTable(projectConstructionList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.projectConstructionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param projectConstruction 实体
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ProjectConstruction projectConstruction) {
        return AjaxResult.success(this.projectConstructionService.insert(projectConstruction));
    }

    /**
     * 编辑数据
     *
     * @param projectConstruction 实体
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ProjectConstruction projectConstruction) {
        return AjaxResult.success(this.projectConstructionService.update(projectConstruction));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.projectConstructionService.deleteById(id));
    }

    @Log(title = "进度日报", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ProjectConstruction> util = new ExcelUtil<>(ProjectConstruction.class);
        List<ProjectConstruction> projectConstructions = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = projectConstructionService.importExcel(projectConstructions, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<ProjectConstruction> util = new ExcelUtil<>(ProjectConstruction.class);
        util.importTemplateExcel(response, "进度日报");
    }

    @GetMapping("/initPlanInputHuman")
    public void initPlanInputHuman() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String format = simpleDateFormat.format(new Date());

        Map<String, Integer> initMap = new HashMap<>();

        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        int week = c.get(Calendar.WEEK_OF_YEAR);

        initMap.put(String.valueOf(week), 913);

        redisCache.setCacheMap(format, initMap);
    }


    @GetMapping("/countConstructionScheduleList")
    public void countConstructionScheduleList() {
        projectConstructionService.countConstructionScheduleList();
    }
}
