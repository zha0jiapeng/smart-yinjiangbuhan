package com.ruoyi.web.controller.basic.view;

import com.ruoyi.system.domain.basic.EngIndexHome;
import com.ruoyi.system.domain.basic.IndexHome;
import com.ruoyi.system.service.SysIndexHomeService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(description = "首页")
@RestController
@RequestMapping("/index")
public class SysIndexHomeController {
    @Autowired
    private SysIndexHomeService sysIndexHomeService;

    @GetMapping(value = "/home")
    public Result<?> home() {
        IndexHome indexHome = sysIndexHomeService.home();
        return Result.ok(indexHome);
    }

    @GetMapping(value = "/eng/home")
    public Result<?> engHome(){
        EngIndexHome engIndexHome = sysIndexHomeService.engIndex();
        return Result.ok(engIndexHome);
    }

}
