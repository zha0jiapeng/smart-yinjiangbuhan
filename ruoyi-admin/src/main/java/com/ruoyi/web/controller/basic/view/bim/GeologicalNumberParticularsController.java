package com.ruoyi.web.controller.basic.view.bim;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.GeologicalNumberParticulars;
import com.ruoyi.system.service.GeologicalNumberParticularsService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("geological/number/particulars")
public class GeologicalNumberParticularsController extends BaseController {

    @Resource
    private GeologicalNumberParticularsService geologicalNumberParticularsService;

    @PostMapping("/bim/list")
    public Result<?> bimList(@RequestBody GeologicalNumberParticulars geologicalNumberParticulars) {
        QueryWrapper<GeologicalNumberParticulars> queryWrapper = new QueryWrapper<>();
        //这里需要写匹配逻辑
        queryWrapper.eq("stake_mark", geologicalNumberParticulars.getStakeMark());
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 1");
        GeologicalNumberParticulars one = geologicalNumberParticularsService.getOne(queryWrapper);

        if (null == one) {
            queryWrapper.clear();
            queryWrapper.orderByAsc("id");
            queryWrapper.last("limit 1");
            one = geologicalNumberParticularsService.getOne(queryWrapper);
        }

        return Result.ok(one);
    }

    @GetMapping
    public TableDataInfo list(GeologicalNumberParticulars geologicalNumberParticulars) {
        startPage();
        QueryWrapper<GeologicalNumberParticulars> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(geologicalNumberParticulars.getStakeMark())) {
            queryWrapper.eq("stake_mark", geologicalNumberParticulars.getStakeMark());
        }
        if (StringUtils.isNotEmpty(geologicalNumberParticulars.getProgress())) {
            queryWrapper.eq("progress", geologicalNumberParticulars.getProgress());
        }
        if (StringUtils.isNotEmpty(geologicalNumberParticulars.getExplorationGeology())) {
            queryWrapper.eq("exploration_geology", geologicalNumberParticulars.getExplorationGeology());
        }
        List<GeologicalNumberParticulars> list = geologicalNumberParticularsService.list(queryWrapper);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult create(@RequestBody GeologicalNumberParticulars geologicalNumberParticulars) {
        geologicalNumberParticularsService.save(geologicalNumberParticulars);
        return AjaxResult.success();
    }

}
