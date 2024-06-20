package com.ruoyi.web.controller.basic.view.project;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.constant.IntegerConstant;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.entity.CurrentConstructionNew;
import com.ruoyi.system.service.CurrentConstructionNewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/current/construction/new")
public class CurrentConstructionNewController extends BaseController {

    @Autowired
    private CurrentConstructionNewService currentConstructionNewService;

    @GetMapping("/list")
    public TableDataInfo list(CurrentConstructionNew currentConstructionNew) {
        startPage();

        QueryWrapper<CurrentConstructionNew> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_flag", IntegerConstant.ONE);
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<CurrentConstructionNew> listPage = currentConstructionNewService.list(queryWrapper);

        return getDataTable(listPage);
    }

    @PostMapping("/details")
    public AjaxResult details(@RequestBody CurrentConstructionNew currentConstructionNew) {
        log.info("currentConstructionNew:{}", JSON.toJSONString(currentConstructionNew));

        List<Integer> idList = new ArrayList<>();
        if (currentConstructionNew.getIds().contains(",")) {
            String[] split = currentConstructionNew.getIds().split(",");
            for (String s : split) {
                idList.add(Integer.valueOf(s));
            }
        } else {
            idList.add(Integer.valueOf(currentConstructionNew.getIds()));
        }

        QueryWrapper<CurrentConstructionNew> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", idList);
        List<CurrentConstructionNew> listPage = currentConstructionNewService.list(queryWrapper);

        return AjaxResult.success(listPage);
    }

    @PostMapping("/getCompany")
    public AjaxResult getCompany(@RequestBody Map<String, Integer> map) {
        Map<Integer, String> companyMap = new HashMap<>();
        if (Objects.equals(map.get("type"), IntegerConstant.ONE)) {
            companyMap.put(1, "中铁十八局集团有限公司");
        } else if (Objects.equals(map.get("type"), IntegerConstant.TWO)) {
            companyMap.put(1, "上海唯智工程项目管理有限公司");
        } else {
            companyMap.put(1, "深圳市福盈混凝土有限公司惠州大亚湾分公司");
        }
        return AjaxResult.success(companyMap);
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody List<CurrentConstructionNew> currentConstructionNews) {
        currentConstructionNewService.updateCurrentConstructionNew(currentConstructionNews);
        return AjaxResult.success();
    }

    @PostMapping("/query/by/pileNumber")
    @SuppressWarnings("all")
    public AjaxResult queryByPileNumber(@RequestBody CurrentConstructionNew currentConstructionNew) {
        log.info("currentConstructionNew:{}", JSON.toJSONString(currentConstructionNew));

        QueryWrapper<CurrentConstructionNew> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pile_number", currentConstructionNew.getPileNumber());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        CurrentConstructionNew one = currentConstructionNewService.getOne(queryWrapper);

        List<Map> maps = new ArrayList<>();
        if (currentConstructionNew.getDirection().equals(IntegerConstant.ONE.toString())) {
            if (StringUtils.isNotEmpty(one.getBasicValueInfoTop())) {
                maps = JSON.parseArray(one.getBasicValueInfoTop(), Map.class);
            }
        } else if (currentConstructionNew.getDirection().equals(IntegerConstant.TWO.toString())) {
            if (StringUtils.isNotEmpty(one.getBasicValueInfoBottom())) {
                maps = JSON.parseArray(one.getBasicValueInfoBottom(), Map.class);
            }
        } else if (currentConstructionNew.getDirection().equals(IntegerConstant.THREE.toString())) {
            if (StringUtils.isNotEmpty(one.getBasicValueInfoLeft())) {
                maps = JSON.parseArray(one.getBasicValueInfoLeft(), Map.class);
            }
        } else {
            if (StringUtils.isNotEmpty(one.getBasicValueInfoRight())) {
                maps = JSON.parseArray(one.getBasicValueInfoRight(), Map.class);
            }
        }

        maps.stream().forEach(var -> {
            Set<String> keys = var.keySet();
            for (String s : keys) {
                Object object = var.get(s);
                var.put(s, object.toString());
            }
        });

        one.setInfo(maps);

        return AjaxResult.success(one);
    }


}
