package com.ruoyi.web.controller.basic.view.project;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.ModelTaskEnum;
import com.ruoyi.system.service.ModelRelationTaskConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ModelRelationTaskConfigController extends BaseController {

    @Resource
    private ModelRelationTaskConfigService modelRelationTaskService;

    @PostMapping("/model/segment/list")
    public AjaxResult segmentList() {
        return AjaxResult.success(modelRelationTaskService.queryModelRelationTaskConfigByType(ModelTaskEnum.SEGMENT.getCode()));
    }

    @PostMapping("/model/pipeline/list")
    public AjaxResult pipelineList() {
        return AjaxResult.success(modelRelationTaskService.queryModelRelationTaskConfigByType(ModelTaskEnum.PIPELINE.getCode()));
    }

    @PostMapping("/model/ground/list")
    public AjaxResult groundList() {
        return AjaxResult.success(modelRelationTaskService.queryModelRelationTaskConfigByType(ModelTaskEnum.GROUND.getCode()));
    }

}
