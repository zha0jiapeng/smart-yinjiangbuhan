package com.ruoyi.web.controller.basic.view;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.system.domain.basic.SysContract;
import com.ruoyi.system.service.ISysContractService;
import com.ruoyi.system.service.ProjectMppService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("contract")
public class SysContractController {
    @Autowired
    private ProjectMppService projectMppService;
    @Autowired
    private ISysContractService sysContractService;

    @GetMapping("list")
    public Result<?> queryPageList(SysContract sysContract,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SysContract> queryWrapper = new QueryWrapper<>();
        Page<SysContract> page = new Page<SysContract>(pageNo, pageSize);
        IPage<SysContract> pageList = sysContractService.page(page, queryWrapper);

        if (null != pageList && !CollectionUtils.isEmpty(pageList.getRecords())) {
            for (SysContract sysContractResp : pageList.getRecords()) {
                String respModelIds = sysContractResp.getModelIds();
                if (StringUtils.isNotBlank(respModelIds)) {
                    List<Integer> modelIds = new ArrayList<>();
                    if (respModelIds.contains(",")) {
                        String[] split = respModelIds.split(",");
                        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
                        queryWrapperCase.in("proj_id", Arrays.asList(split));
                        List<ProjectMpp> list = projectMppService.list(queryWrapperCase);
                        sysContractResp.setProjectMppList(list);
                        for (String modelId : split) {
                            modelIds.add(Integer.valueOf(modelId));
                        }
                    } else {
                        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
                        queryWrapperCase.eq("proj_id", respModelIds);
                        List<ProjectMpp> list = projectMppService.list(queryWrapperCase);
                        sysContractResp.setProjectMppList(list);
                        modelIds.add(Integer.valueOf(respModelIds));
                    }
                    sysContractResp.setModelIdsParam(modelIds);
                }
            }
        }

        return Result.OK(pageList);
    }

    @PostMapping("add")
    public ResponseEntity<SysContract> add(@RequestBody SysContract sysContract) {
        if (null != sysContract && !CollectionUtils.isEmpty(sysContract.getSysContractCostDetails())) {
            sysContract.setBatchDetail(JSON.toJSONString(sysContract));
        }
        return ResponseEntity.ok(this.sysContractService.insert(sysContract));
    }

    @PutMapping("edit")
    public Result<?> edit(@RequestBody SysContract sysContract) {
        log.info("合同信息 更新:{}", JSON.toJSON(sysContract));
        sysContractService.updateById(sysContract);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping("delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        sysContractService.removeById(id);
        return Result.OK("删除成功!");
    }

    @PostMapping("relation")
    public Result<?> relation(@RequestBody SysContract sysContract) {
        if (null == sysContract) {
            return Result.error("参数异常");
        }
        if (null == sysContract.getId()) {
            return Result.error("费用id不能为空");
        }
        if (CollectionUtils.isEmpty(sysContract.getModelIdsParam())) {
            return Result.error("模型id不能为空");
        }

        String join = StringUtils.join(sysContract.getModelIdsParam().stream().map(Object::toString).sorted().collect(Collectors.toList()), ",");
        sysContract.setModelIds(join);
        sysContractService.updateById(sysContract);

        return Result.OK();
    }

}
