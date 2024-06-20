package com.ruoyi.web.controller.basic.view.bim;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.domain.GeologicalNumber;
import com.ruoyi.system.domain.GeologicalNumberParticulars;
import com.ruoyi.system.service.GeologicalNumberParticularsService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("geological/number")
public class GeologicalNumberController {
    @Resource
    private GeologicalNumberParticularsService geologicalNumberParticularsService;

    @PostMapping("/list")
    public Result<?> list(@RequestBody GeologicalNumber geologicalNumber) {
        QueryWrapper<GeologicalNumberParticulars> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stake_mark", geologicalNumber.getStakeMark());
        List<GeologicalNumberParticulars> list = geologicalNumberParticularsService.list(queryWrapper);
        return Result.OK(list);
    }

}
