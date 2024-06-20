package com.ruoyi.web.controller.basic.view.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.ModelRelationTask;
import com.ruoyi.system.service.ModelRelationTaskService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ModelRelationTaskController extends BaseController {

    @Resource
    private ModelRelationTaskService modelRelationTaskService;

    /**
     * 新增数据
     *
     * @param modelRelationTask 实体
     */
    @PostMapping("/model/cancellation/relationship")
    public AjaxResult cancellationRelationship(@RequestBody ModelRelationTask modelRelationTask) {

        if (null == modelRelationTask) {
            return AjaxResult.error("参数异常");
        }

        if (StringUtils.isEmpty(modelRelationTask.getModelId())) {
            return AjaxResult.error("模型id不可为空");
        }

        QueryWrapper<ModelRelationTask> wrapper = new QueryWrapper<>();
        wrapper.eq("model_id", modelRelationTask.getModelId());

        modelRelationTaskService.remove(wrapper);

        return AjaxResult.success();
    }


    /**
     * 新增数据
     *
     * @param modelRelationTask 实体
     */
    @PostMapping("/model/relationship")
    public AjaxResult relationship(@RequestBody ModelRelationTask modelRelationTask) {
        if (null == modelRelationTask) {
            return AjaxResult.error("参数异常");
        }
        if (StringUtils.isEmpty(modelRelationTask.getTaskId())) {
            return AjaxResult.error("任务ID不可为空");
        }
        if (CollectionUtils.isEmpty(modelRelationTask.getModelIds())) {
            return AjaxResult.error("模型数组不可为空");
        }

        List<ModelRelationTask> savaModelRelationTaskList = new ArrayList<>();
        for (String var : modelRelationTask.getModelIds()) {
            ModelRelationTask saveModelRelationTask = new ModelRelationTask();
            saveModelRelationTask.setTaskId(modelRelationTask.getTaskId());
            saveModelRelationTask.setModelId(var);
            saveModelRelationTask.setYn(YnEnum.Y.getCode());
            saveModelRelationTask.setCreatedDate(new Date());
            savaModelRelationTaskList.add(saveModelRelationTask);
        }

        modelRelationTaskService.saveBatch(savaModelRelationTaskList);
        return AjaxResult.success();
    }
}
