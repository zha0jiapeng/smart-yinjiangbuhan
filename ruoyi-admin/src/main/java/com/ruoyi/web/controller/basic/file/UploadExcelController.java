package com.ruoyi.web.controller.basic.file;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ProgressBingRelationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Api(description = "excel项目管理")
@RestController
@RequestMapping("upload/excel")
public class UploadExcelController {
    @Resource
    private ProgressBingRelationService progressBingRelationService;
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestParam("file") MultipartFile excel) {
        return AjaxResult.success();
    }

}
