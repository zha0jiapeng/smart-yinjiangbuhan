package com.ruoyi.web.controller.basic.view;

import com.ruoyi.common.enums.CycleEnum;
import com.ruoyi.system.domain.basic.SysWeekly;
import com.ruoyi.system.service.SysWeeklyService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("weekly")
public class SysWeeklyController {

    @Autowired
    private SysWeeklyService sysWeeklyService;

    @GetMapping(value = "/create")
    public Result<?> create(){
        SysWeekly sysWeekly = sysWeeklyService.sumProject(CycleEnum.DAY);
        return Result.ok(sysWeekly);
    }

}
