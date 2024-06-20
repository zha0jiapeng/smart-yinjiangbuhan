package com.ruoyi.web.controller.basic.view.danger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.SysQuestionDescribe;
import com.ruoyi.system.service.SysQuestionDescribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysQuestionDescribe")
public class SysQuestionDescribeController extends BaseController {

    @Autowired
    private SysQuestionDescribeService sysQuestionDescribeService;

    @PostMapping("/add")
    public AjaxResult create(@RequestBody SysQuestionDescribe sysQuestionDescribe) {
        sysQuestionDescribeService.create(sysQuestionDescribe);
        return AjaxResult.success();
    }

    @PostMapping("/top")
    public AjaxResult top() {
        QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",0);
        List<SysQuestionDescribe> list = sysQuestionDescribeService.list(queryWrapper);
        return AjaxResult.success(list);
    }

    @PostMapping("/tree")
    public AjaxResult tree(@RequestBody SysQuestionDescribe sysQuestionDescribe) {
        QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",sysQuestionDescribe.getId());
        List<SysQuestionDescribe> list = sysQuestionDescribeService.list(queryWrapper);
        return AjaxResult.success(list);
    }

}
