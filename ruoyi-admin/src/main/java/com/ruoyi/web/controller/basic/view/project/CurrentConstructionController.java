package com.ruoyi.web.controller.basic.view.project;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.entity.CurrentConstruction;
import com.ruoyi.system.service.CurrentConstructionService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/current/construction")
public class CurrentConstructionController extends BaseController {

    @Autowired
    private CurrentConstructionService currentConstructionService;

    @PostMapping("/query/by/id")
    @SuppressWarnings("all")
    public AjaxResult queryById(@RequestBody CurrentConstruction currentConstruction) {
        QueryWrapper<CurrentConstruction> wrapper = new QueryWrapper<>();
        if (null != currentConstruction
                && StringUtils.isNotEmpty(currentConstruction.getRelationId())) {
            wrapper.eq("relation_id", currentConstruction.getRelationId());
        }
        wrapper.eq("yn", YnEnum.Y.getCode());
        List<CurrentConstruction> list = currentConstructionService.list(wrapper);

        CurrentConstruction construction = list.stream().findFirst().orElse(new CurrentConstruction());

        if (StringUtils.isNotEmpty(construction.getSaveArray())) {
            List<Map> maps = JSON.parseArray(construction.getSaveArray(), Map.class);
            construction.setInfo(maps);
        }

        return AjaxResult.success(construction);
    }

    @PostMapping("/details")
    public AjaxResult details() {
        QueryWrapper<CurrentConstruction> wrapper = new QueryWrapper<>();
        wrapper.eq("relation_id", "current");
        wrapper.eq("yn", YnEnum.Y.getCode());
        CurrentConstruction one = currentConstructionService.getOne(wrapper);
        return AjaxResult.success(one);
    }

    @PostMapping("/details/plan")
    public AjaxResult detailsPlan() {
        QueryWrapper<CurrentConstruction> wrapper = new QueryWrapper<>();
        wrapper.eq("relation_id", "plan");
        wrapper.eq("yn", YnEnum.Y.getCode());
        CurrentConstruction one = currentConstructionService.getOne(wrapper);
        return AjaxResult.success(one);
    }

    @PostMapping("/update")
    public AjaxResult edit(@RequestBody CurrentConstruction currentConstruction) {
        if (null == currentConstruction) {
            return AjaxResult.error("参数异常");
        }

        if (!CollectionUtils.isEmpty(currentConstruction.getInfo())) {
            currentConstruction.setSaveArray(JSON.toJSONString(currentConstruction.getInfo()));
        }

        QueryWrapper<CurrentConstruction> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(currentConstruction.getRelationId())) {
            wrapper.eq("relation_id", currentConstruction.getRelationId());
        }
        wrapper.eq("yn", YnEnum.Y.getCode());
        CurrentConstruction one = currentConstructionService.getOne(wrapper);
        if (null == one) {
            currentConstructionService.save(currentConstruction);
        } else {
            currentConstruction.setId(one.getId());
            currentConstruction.setSaveArray(JSON.toJSONString(currentConstruction.getInfo()));
            currentConstructionService.updateCurrentConstruction(currentConstruction);
        }

        return AjaxResult.success();
    }


    @GetMapping("updateSchedule")
    public Result<?> updateSchedule() {
        currentConstructionService.bimNewListSchedule();
        return Result.ok();
    }

}
