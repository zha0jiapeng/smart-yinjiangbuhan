package com.ruoyi.web.controller.basic.view.danger;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysDanger;
import com.ruoyi.system.service.ISysDangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/danger")
public class SysDangerController extends BaseController {

    @Autowired
    private ISysDangerService sysDangerService;

    @PostMapping("/create")
    public AjaxResult create(@RequestBody SysDanger sysDanger) {
        sysDangerService.create(sysDanger);
        return AjaxResult.success();
    }

    @GetMapping("/list")
    public TableDataInfo list(SysDanger sysDanger) {
        startPage();
        List<SysDanger> sysDangerList = sysDangerService.list(sysDanger);
        return getDataTable(sysDangerList);
    }

    @GetMapping("/delete")
    public AjaxResult delete(Integer id) {
        sysDangerService.delete(id);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysDanger sysDanger) {
        sysDangerService.update(sysDanger);
        return AjaxResult.success();
    }

    @GetMapping("/listByLevel")
    public AjaxResult list() {
        List<SysDanger> sysDangerList = sysDangerService.listByLevel();
        return AjaxResult.success(sysDangerList);
    }

    @GetMapping("/getGeoData")
    public AjaxResult getGeoData() {
        List<Map<String,Object>> geoDataList = sysDangerService.getGeoData();
        return AjaxResult.success(geoDataList);
    }
}
