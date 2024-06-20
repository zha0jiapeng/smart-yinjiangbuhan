package com.ruoyi.web.controller.basic.view.qualityManager;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.entity.QualityTesting;
import com.ruoyi.system.entity.QualityTestingDTO;
import com.ruoyi.system.service.QualityProblemService;
import com.ruoyi.system.service.QualityTestingService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * (QualityProblem)表控制层
 *
 * @author makejava
 * @since 2022-12-26 16:03:29
 */
@Slf4j
@RestController
@RequestMapping("quality/testing")
public class QualitTestingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QualityTestingService qualityTestingService;
    @Resource
    private QualityProblemService qualityProblemService;

    @GetMapping
    public Result<?> queryPageList(QualityTesting qualityTesting,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {

        QueryWrapper<QualityTesting> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(qualityTesting.getTestingName())) {
            queryWrapper.eq("testing_name", qualityTesting.getTestingName());
        }
        if (StringUtils.isNoneBlank(qualityTesting.getCheckOptions())) {
            queryWrapper.eq("check_options", qualityTesting.getCheckOptions());
        }
        if (StringUtils.isNoneBlank(qualityTesting.getTaskDescribe())) {
            queryWrapper.eq("task_describe", qualityTesting.getTaskDescribe());
        }
        Page<QualityTesting> page = new Page<QualityTesting>(pageNo, pageSize);
        IPage<QualityTesting> pageList = qualityTestingService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody QualityTesting qualityTesting) {
        qualityTesting.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        qualityTesting.setCreateTime(new Date());
        qualityTesting.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        qualityTesting.setUpdateTime(new Date());
        return ResponseEntity.ok(this.qualityTestingService.save(qualityTesting));
    }

    @PutMapping
    public Result<?> edit(@RequestBody QualityTesting qualityTesting) {
        log.info("质检任务 更新:{}", JSON.toJSON(qualityTesting));
        qualityTesting.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        qualityTesting.setUpdateTime(new Date());
        return Result.OK("编辑成功!");
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        qualityTestingService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/testingAdd")
    public AjaxResult add(@RequestBody QualityTestingDTO qualityTestingDTO) {
        log.info("请求参数:{}", JSON.toJSON(qualityTestingDTO));
        StringBuilder key = new StringBuilder();

        QualityTesting queryById = qualityTestingService.getById(qualityTestingDTO.getPid());
        if (null != queryById && !com.ruoyi.common.utils.StringUtils.isEmpty(queryById.getQualityProblemsId())) {
            key.append(queryById.getQualityProblemsId());
        }

        if (!CollectionUtils.isEmpty(qualityTestingDTO.getQualityProblemList())) {
            for (QualityProblem qualityProblem : qualityTestingDTO.getQualityProblemList()) {
                qualityProblem.setCheckStatus(0);
                QualityProblem insert = qualityProblemService.insert(qualityProblem);

                key.append(insert.getId());
                key.append(",");
            }
        }

        if (!com.ruoyi.assessment.core.utils.StringUtils.isBlank(key.toString())) {
            assert queryById != null;
            queryById.setQualityProblemsId(key.toString());
            log.info("insert 结果:{}", JSON.toJSON(queryById));
            qualityTestingService.updateById(queryById);
        }

        return AjaxResult.success();
    }

    @GetMapping("/testingList")
    public AjaxResult qualityList(QualityTestingDTO qualityTestingDTO) {
        QualityTesting qualityTesting = qualityTestingService.getById(qualityTestingDTO.getPid());
        List<QualityProblem> list = new ArrayList<>();
        if (qualityTesting != null) {
            if (!StringUtils.isBlank(qualityTesting.getQualityProblemsId())) {
                String taskProblemId = qualityTesting.getQualityProblemsId();
                taskProblemId = taskProblemId.substring(0, taskProblemId.length() - 1);
                if (taskProblemId.contains(",")) {
                    String[] split = taskProblemId.split(",");
                    QualityProblem qualityProblem = new QualityProblem();
                    qualityProblem.setIds(Arrays.asList(split));
                    list.addAll(qualityProblemService.queryByPage(qualityProblem));
                } else {
                    if (!StringUtils.isBlank(taskProblemId)) {
                        QualityProblem qualityProblem = qualityProblemService.queryById(Integer.valueOf(taskProblemId));
                        list.add(qualityProblem);
                    }
                }
            }
        }

        qualityTestingDTO.setQualityProblemList(list);
        return AjaxResult.success(qualityTestingDTO);
    }

}

