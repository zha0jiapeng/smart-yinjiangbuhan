package com.ruoyi.web.controller.basic.model;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.system.domain.basic.ProgressBingRelation;
import com.ruoyi.system.domain.basic.ReceiveModel;
import com.ruoyi.system.service.ProgressBingRelationService;
import com.ruoyi.system.service.ProjectMppService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(description = "ssn奥术大师发货单森林防火金卡戴")
@RestController
@RequestMapping("/three/dimensional/model")
public class ThreeDimensionalModelController {
    @Autowired
    private ProjectMppService projectMppService;
    @Autowired
    private ProgressBingRelationService progressBingRelationService;

    @PostMapping("receive/model")
    public AjaxResult receiveModel(@RequestBody ReceiveModel receiveModel) {
        log.info("接收三维模型参数:{}", JSON.toJSONString(receiveModel));

        BaseVerifyUtil.verify(CollectionUtils.isEmpty(receiveModel.getParam()))
                .throwMessage(VerifyEnum.THREE_DIMENSIONAL_MODEL_IDS_NULL.getCode(), VerifyEnum.THREE_DIMENSIONAL_MODEL_IDS_NULL.getDesc());

        List<ProgressBingRelation> progressBingRelations = new ArrayList<>();
        for (String modelId : receiveModel.getParam()) {
            ProgressBingRelation progressBingRelation = new ProgressBingRelation();
            progressBingRelation.setModelId(modelId);
            progressBingRelation.setCreatedBy("sys");
            progressBingRelation.setCreatedDate(new Date());
            progressBingRelations.add(progressBingRelation);
        }

        //数据量比较大,分页多线程处理 线程公式 cpu = cpu/4
        List<List<ProgressBingRelation>> partition = Lists.partition(progressBingRelations, 1000);
        partition.parallelStream().forEach(value -> {
            progressBingRelationService.saveBatch(value);
        });

        return AjaxResult.success();
    }

    @PostMapping("uploda/model")
    public AjaxResult uploadProject(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        List<String> modelIds = new ArrayList<>();

        BufferedReader bufReader = null;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufReader = new BufferedReader(inputStreamReader);
            //读取每行内容
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                modelIds.add(line);
            }
        } catch (Exception e) {
            log.info("读取三维模型id失败:{}", e.toString());
        } finally {
            if (bufReader != null) {
                bufReader.close();
            }
        }

        for (String modelId : modelIds) {
        }

        return AjaxResult.success();
    }

    @PostMapping("/list")
    public Result<?> list(@RequestBody ReceiveModel receiveModel) {
        return Result.ok();
    }

    @PostMapping("/progressName")
    public Result<?> progressName(@RequestBody ReceiveModel receiveModel) {

        QueryWrapper<ProgressBingRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_id", receiveModel.getModelId());
        ProgressBingRelation progressBingRelation = progressBingRelationService.getOne(queryWrapper);
        if (null == progressBingRelation) {
            return Result.error("未查询到数据");
        }

        QueryWrapper<ProjectMpp> wrapper = new QueryWrapper<>();
        queryWrapper.eq("id", progressBingRelation.getProjectId());
        ProjectMpp projectMppServiceOne = projectMppService.getOne(wrapper);
        if (null == projectMppServiceOne) {
            return Result.error("未查询到数据");
        }

        return Result.ok(projectMppServiceOne);
    }

}
