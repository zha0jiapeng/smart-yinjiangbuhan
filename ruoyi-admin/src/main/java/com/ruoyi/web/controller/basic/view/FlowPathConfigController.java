package com.ruoyi.web.controller.basic.view;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.FlowPathConfig;
import com.ruoyi.system.service.FlowPathConfigService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("flow/path/config")
public class FlowPathConfigController {

    @Autowired
    private FlowPathConfigService flowPathConfigService;

    @GetMapping
    public Result<?> queryPageList(FlowPathConfig flowPathConfig,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<FlowPathConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.orderByDesc("id");
        Page<FlowPathConfig> page = new Page<FlowPathConfig>(pageNo, pageSize);
        IPage<FlowPathConfig> pageList = flowPathConfigService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping
    public ResponseEntity<FlowPathConfig> add(@RequestBody FlowPathConfig flowPathConfig) {
        flowPathConfig.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        flowPathConfig.setCreatedDate(new Date());
        flowPathConfig.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        flowPathConfig.setModifyDate(new Date());
        flowPathConfig.setYn(YnEnum.Y.getCode());
        return ResponseEntity.ok(this.flowPathConfigService.insert(flowPathConfig));
    }

    @PutMapping
    public Result<?> edit(@RequestBody FlowPathConfig flowPathConfig) {
        log.info("流程信息 更新:{}", JSON.toJSON(flowPathConfig));
        flowPathConfig.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        flowPathConfig.setModifyDate(new Date());
        flowPathConfigService.updateById(flowPathConfig);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        flowPathConfigService.removeById(id);
        return Result.OK("删除成功!");
    }

}
