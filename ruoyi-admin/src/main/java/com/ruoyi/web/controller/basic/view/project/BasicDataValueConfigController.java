package com.ruoyi.web.controller.basic.view.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.entity.BasicDataValueConfig;
import com.ruoyi.system.service.BasicDataValueConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("basic/data/value/config")
public class BasicDataValueConfigController extends BaseController {

    @Autowired
    private BasicDataValueConfigService basicDataValueConfigService;

    @PostMapping("/query/by/id")
    public AjaxResult queryById(@RequestBody BasicDataValueConfig basicDataValueConfig) {
        QueryWrapper<BasicDataValueConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("id", basicDataValueConfig.getId());
        wrapper.eq("yn", YnEnum.Y.getCode());
        BasicDataValueConfig one = basicDataValueConfigService.getOne(wrapper);

        return AjaxResult.success(one);
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody BasicDataValueConfig basicDataValueConfig) {

        BasicDataValueConfig basicDataValueConfigUpdate = new BasicDataValueConfig();
        basicDataValueConfigUpdate.setId(basicDataValueConfig.getId());
        basicDataValueConfigUpdate.setBasicValue(basicDataValueConfig.getBasicValue());
        basicDataValueConfigUpdate.setModifyBy(getUsername());
        basicDataValueConfigUpdate.setModifyDate(new Date());

        basicDataValueConfigService.updateById(basicDataValueConfigUpdate);

        return AjaxResult.success();
    }

}
