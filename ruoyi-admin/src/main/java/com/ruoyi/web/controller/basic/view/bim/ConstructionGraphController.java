package com.ruoyi.web.controller.basic.view.bim;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.ConstructionGraph;
import com.ruoyi.system.service.ConstructionGraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("construction/graph")
public class ConstructionGraphController extends BaseController {

    @Autowired
    private ConstructionGraphService constructionGraphService;

    @GetMapping
    public AjaxResult queryPageList() {
        QueryWrapper<ConstructionGraph> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ConstructionGraph> list = constructionGraphService.list();
        return success(list);
    }


}
