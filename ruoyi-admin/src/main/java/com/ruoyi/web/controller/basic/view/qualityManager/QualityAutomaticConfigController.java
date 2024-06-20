package com.ruoyi.web.controller.basic.view.qualityManager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.QualityAutomaticConfig;
import com.ruoyi.system.service.QualityAutomaticConfigService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("quality/automatic/config")
public class QualityAutomaticConfigController {

    @Autowired
    private QualityAutomaticConfigService qualityAutomaticConfigService;

    @GetMapping
    public Result<?> queryPageList(QualityAutomaticConfig qualityAutomaticConfig,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<QualityAutomaticConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        Page<QualityAutomaticConfig> page = new Page<QualityAutomaticConfig>(pageNo, pageSize);
        IPage<QualityAutomaticConfig> pageList = qualityAutomaticConfigService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    @PostMapping("/list")
    public Result<?> list(@RequestBody QualityAutomaticConfig qualityAutomaticConfig) {
        QueryWrapper<QualityAutomaticConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", qualityAutomaticConfig.getPid());
        queryWrapper.eq("quality_config_type", qualityAutomaticConfig.getQualityConfigType());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<QualityAutomaticConfig> list = qualityAutomaticConfigService.list(queryWrapper);
        return Result.OK(list);
    }

    @PostMapping
    public ResponseEntity<QualityAutomaticConfig> add(@RequestBody QualityAutomaticConfig qualityAutomaticConfig) {
        qualityAutomaticConfig.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        qualityAutomaticConfig.setCreatedDate(new Date());
        qualityAutomaticConfig.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        qualityAutomaticConfig.setModifyDate(new Date());
        qualityAutomaticConfig.setYn(YnEnum.Y.getCode());
        this.qualityAutomaticConfigService.save(qualityAutomaticConfig);
        return ResponseEntity.ok(qualityAutomaticConfig);
    }

    @PutMapping
    public Result<?> edit(@RequestBody QualityAutomaticConfig qualityAutomaticConfig) {
        qualityAutomaticConfig.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        qualityAutomaticConfig.setModifyDate(new Date());
        qualityAutomaticConfigService.updateById(qualityAutomaticConfig);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        qualityAutomaticConfigService.removeById(id);
        return Result.OK("删除成功!");
    }

}
