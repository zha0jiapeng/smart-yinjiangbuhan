package com.ruoyi.web.controller.basic.view;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.basic.*;
import com.ruoyi.system.service.ISysContractService;
import com.ruoyi.system.service.ProgressBingRelationService;
import com.ruoyi.system.service.ProjectMppService;
import com.ruoyi.system.service.ProjectMppTrackService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Api(description = "project项目管理")
@RestController
@RequestMapping("/project")
public class SysProjectController {

    @Autowired
    private ProjectMppService projectMppService;
    @Autowired
    private ProjectMppTrackService projectMppTrackService;
    @Autowired
    private ProgressBingRelationService progressBingRelationService;

    @Autowired
    private ISysContractService sysContractService;

    @GetMapping(value = "/tree")
    public Result<?> tree(ProjectMpp projectMpp) {
        List<TreeSelect> treeSelects = projectMppService.queryTree(projectMpp);
        return Result.ok(treeSelects);
    }

    @GetMapping(value = "/list")
    public Result<?> list(ProjectMpp projectMpp,
                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                          HttpServletRequest req) {

        log.info("project项目管理:{}", JSON.toJSONString(projectMpp));
        QueryWrapper<ProjectMpp> queryWrapper = new QueryWrapper<>();
        Page<ProjectMpp> page = new Page<ProjectMpp>(pageNo, pageSize);
        IPage<ProjectMpp> pageList = projectMppService.page(page, queryWrapper);
        if (null != pageList && !CollectionUtils.isEmpty(pageList.getRecords())) {
            for (ProjectMpp mpp : pageList.getRecords()) {
                List<ProjectMpp> projectMppLists = new ArrayList<>();
                //前置任务
                QueryWrapper<ProjectMpp> frontWrapper = new QueryWrapper<>();
                frontWrapper.eq("proj_id", mpp.getFrontTask());
                projectMppLists.add(projectMppService.getOne(frontWrapper));
                //查询后置任务
                QueryWrapper<ProjectMpp> afterWrapper = new QueryWrapper<>();
                afterWrapper.eq("proj_id", mpp.getAfterTask());
                projectMppLists.add(projectMppService.getOne(afterWrapper));
                mpp.setFrontAndAfter(projectMppLists);
            }
        }
        return Result.OK(pageList);
    }

    @PostMapping("add")
    public Result<?> add(@RequestBody ProjectMpp projectMpp) {
        return Result.ok(this.projectMppService.save(projectMpp));
    }

    @PutMapping("edit")
    public Result<?> edit(@RequestBody ProjectMpp projectMpp) {
        log.info("project项目管理 更新:{}", JSON.toJSON(projectMpp));
        projectMppService.updateProjectMpp(projectMpp);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping("delete")
    public Result<?> delete(@RequestParam(name = "projId", required = true) String id) {
        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("proj_id", id);
        projectMppService.remove(queryWrapperCase);
        return Result.OK("删除成功!");
    }

    @PostMapping("relation")
    public Result<?> relation(@RequestBody ProjectMpp projectMpp) {
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }
        if (CollectionUtils.isEmpty(projectMpp.getModelIdsParam())) {
            return Result.error("模型id不能为空");
        }

        //根据任务查询是否已经关联模型;
        QueryWrapper<ProgressBingRelation> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("project_id", projectMpp.getProjId());
        ProgressBingRelation one = progressBingRelationService.getOne(queryWrapperCase);

        ProgressBingRelation progressBingRelation = new ProgressBingRelation();
        progressBingRelation.setProjectId(projectMpp.getProjId().toString());
        String join = StringUtils.join(projectMpp.getModelIdsParam().stream().map(Object::toString).sorted().collect(Collectors.toList()), ",");
        progressBingRelation.setModelId(join);

        if (null != one) {
            progressBingRelationService.updateById(progressBingRelation);
        } else {
            progressBingRelationService.save(progressBingRelation);
        }

        return Result.OK();
    }

    @PostMapping("frontAfter")
    public Result<?> frontAfter(@RequestBody ProjectMpp projectMpp) {
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }

        List<ProjectMpp> projectMppLists = new ArrayList<>();

        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("proj_id", projectMpp.getProjId());
        ProjectMpp one = projectMppService.getOne(queryWrapperCase);
        if (null == one) {
            return Result.error("任务为空,不可非法调用。");
        }

        //前置任务
        QueryWrapper<ProjectMpp> frontWrapper = new QueryWrapper<>();
        frontWrapper.eq("proj_id", one.getFrontTask());
        projectMppLists.add(projectMppService.getOne(frontWrapper));

        //查询后置任务
        QueryWrapper<ProjectMpp> afterWrapper = new QueryWrapper<>();
        afterWrapper.eq("proj_id", one.getAfterTask());
        projectMppLists.add(projectMppService.getOne(afterWrapper));

        return Result.OK(projectMppLists);
    }

    @PostMapping("project/mpp/icor")
    public Result<?> projectMppIcor(@RequestBody ProjectMpp projectMpp) {
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }

        ProjectMppIcon projectMppIcon = new ProjectMppIcon();
        //查询任务计划开始时间和计划结束时间;
        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("proj_id", projectMpp.getProjId());
        ProjectMpp one = projectMppService.getOne(queryWrapperCase);
        if (null == one) {
            return Result.error("任务为空,不可非法调用。");
        }

        try {
            List<String> monthBetweenDateStr = new ArrayList<>();
            monthBetweenDateStr.add(one.getStartDate());
            monthBetweenDateStr.addAll(DateUtils.getMonthBetweenDateStr(one.getStartDate(), one.getEndDate()));
            monthBetweenDateStr.add(one.getEndDate());
            projectMppIcon.setDateChart(monthBetweenDateStr);
        } catch (Exception e) {
            log.error("project/mpp/icor 查询数据:{}", JSON.toJSONString(one));
            return Result.error("日期计算异常");
        }

        //计划时间
        projectMppIcon.setPlanProgressStartDate(one.getStartDate());
        projectMppIcon.setPlanProgressEndDate(one.getEndDate());

        //实际时间
        QueryWrapper<ProjectMppTrack> projectMppIconQueryWrapper = new QueryWrapper<>();
        projectMppIconQueryWrapper.eq("pid", projectMpp.getProjId());
        List<ProjectMppTrack> projectMppIcons = projectMppTrackService.list(projectMppIconQueryWrapper);
        if (!CollectionUtils.isEmpty(projectMppIcons)) {
            Date date = projectMppIcons.stream().sorted(Comparator.comparing(ProjectMppTrack::getStartDate)
                    .reversed()).collect(Collectors.toList()).stream().map(ProjectMppTrack::getStartDate).findFirst().orElse(null);
            projectMppIcon.setActualProgressStartDate(date);

            Date actualProgressEndDate = projectMppIcons.stream().sorted(Comparator.comparing(ProjectMppTrack::getEndDate))
                    .collect(Collectors.toList()).stream().map(ProjectMppTrack::getEndDate).findFirst().orElse(null);
            projectMppIcon.setActualProgressEndDate(actualProgressEndDate);
        }
        return Result.OK(projectMppIcon);
    }

    @PostMapping("project/mpp/details")
    public Result<?> projectMppDetails(@RequestBody ProjectMpp projectMpp) throws Exception {
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }
        ProjectMppDetails projectMppDetails = new ProjectMppDetails();

        //查询任务计划开始时间和计划结束时间;
        QueryWrapper<ProjectMpp> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("proj_id", projectMpp.getProjId());
        ProjectMpp one = projectMppService.getOne(queryWrapperCase);
        if (null == one) {
            return Result.error("任务为空,不可非法调用。");
        }

        QueryWrapper<ProjectMppTrack> trackWrapper = new QueryWrapper<>();
        trackWrapper.eq("pid", projectMpp.getProjId());
        List<ProjectMppTrack> trackList = projectMppTrackService.list(trackWrapper);
        if (!CollectionUtils.isEmpty(trackList)) {
            // 任务状态
            projectMppDetails.setTaskStatus("进行中");

            //开始时间排序
            ProjectMppTrack projectMppTrack = trackList.stream().sorted(Comparator.comparing(ProjectMppTrack::getStartDate))
                    .collect(Collectors.toList()).stream().limit(BigDecimal.ROUND_DOWN).findFirst().orElse(null);
            if (ObjectUtils.isNotEmpty(projectMppTrack)) {
                // 取最早日期
                projectMppDetails.setActualStartDate(projectMppTrack.getStartDate());
            }

            //获取比例为100%的结束时间
            Optional<ProjectMppTrack> max = trackList.stream().max(Comparator.comparing(ProjectMppTrack::getCompleteProgress));
            if (max.isPresent()) {
                ProjectMppTrack mppTrack = max.get();
                if ("100".equals(mppTrack.getCompleteProgress())) {
                    projectMppDetails.setActualEndDate(mppTrack.getEndDate());
                    projectMppDetails.setTaskStatus("已完成");
                }
            }

            List<String> completeProgressStr = trackList.stream().map(ProjectMppTrack::getCompleteProgress).collect(Collectors.toList());
            List<BigDecimal> decimals = completeProgressStr.stream().map(BigDecimal::new).collect(Collectors.toList());
            BigDecimal decimal = decimals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal bigDecimal = new BigDecimal("100");

            List<ProjectMppTrack> mppTracks = trackList.stream().sorted(Comparator.comparing(ProjectMppTrack::getEndDate))
                    .collect(Collectors.toList());
            ProjectMppTrack track = mppTracks.stream().findFirst().orElse(null);

            if (decimal.compareTo(bigDecimal) == 0) {
                assert track != null;
                projectMppDetails.setActualEndDate(track.getEndDate());
                projectMppDetails.setTaskStatus("已完成");
            }

            //处理已经延期逻辑
            String endDate = one.getEndDate();
            Date endDateBuilder = DateUtils.parseDate(endDate);
            ProjectMppTrack projectMppTrackOne = trackList.stream().sorted(Comparator.comparing(ProjectMppTrack::getEndDate)).collect(Collectors.toList())
                    .stream().limit(BigDecimal.ROUND_DOWN).findFirst().orElse(null);

            if (null != projectMppTrackOne && null != projectMppTrackOne.getEndDate() && projectMppTrackOne.getEndDate().after(endDateBuilder)) {
                projectMppDetails.setTaskStatus("已延期");
            }
        } else {
            //任务状态未开始
            projectMppDetails.setTaskStatus("未开始");
        }
        //任务名称
        projectMppDetails.setTaskName(one.getTaskName());
        //计划开始时间
        projectMppDetails.setPlanSateDate(DateUtils.parseDate(one.getStartDate()));
        //计划结束时间
        projectMppDetails.setPlanEndDate(DateUtils.parseDate(one.getEndDate()));
        //工期
        projectMppDetails.setDuration(one.getDurationDate());
        return Result.OK(projectMppDetails);
    }


    @PostMapping("project/mpp/track")
    public Result<?> projectMppTrack(@RequestBody ProjectMpp projectMpp) {
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }
        //查询任务计划开始时间和计划结束时间;
        QueryWrapper<ProjectMppTrack> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("pid", projectMpp.getProjId());
        List<ProjectMppTrack> list = projectMppTrackService.list(queryWrapperCase);
        return Result.OK(list);
    }

    @PostMapping("project/mpp/track/add")
    public Result<?> projectMppTrackAdd(@RequestBody ProjectMppTrack projectMppTrack) {
        //上报人取当前登录用户
        projectMppTrack.setEscalationPeople(SecurityUtils.getLoginUser().getUsername());
        //上报时间为当前时间
        projectMppTrack.setEscalationDate(new Date());
        projectMppTrackService.save(projectMppTrack);
        return Result.OK(projectMppTrack);
    }

    @DeleteMapping("project/mpp/track/delete")
    public Result<?> projectMppTrackDelete(@RequestBody ProjectMppTrack projectMppTrack) {
        log.info("进度追踪 删除任务:{}", JSON.toJSONString(projectMppTrack));
        if (null == projectMppTrack) {
            return Result.error("参数异常");
        }
        if (null == projectMppTrack.getId()) {
            return Result.error("任务ID不能为空");
        }
        projectMppTrackService.removeById(projectMppTrack.getId());
        return Result.OK(projectMppTrack);
    }


    @PostMapping("relation/contract")
    public Result<?> relationContract(@RequestBody ProjectMpp projectMpp) {
        List<ProjectMppContract> projectMppContractList = new ArrayList<>();
        //根据进度查询合同
        if (null == projectMpp) {
            return Result.error("参数异常");
        }
        if (null == projectMpp.getProjId()) {
            return Result.error("任务ID不能为空");
        }
        QueryWrapper<SysContract> queryWrapperCase = new QueryWrapper<>();
        queryWrapperCase.eq("model_ids", projectMpp.getProjId());
        List<SysContract> list = sysContractService.list(queryWrapperCase);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(val -> {
                ProjectMppContract projectMppContract = new ProjectMppContract();
                projectMppContract.setContractCode(val.getContractCode());
                projectMppContract.setContractName(val.getContractName());
                projectMppContract.setContractAmount(val.getContractAmount());
                projectMppContract.setSupplier(val.getSupplierName());
                projectMppContract.setSignDate(DateUtils.parseDate(val.getSigningDate()));
                projectMppContractList.add(projectMppContract);
            });
        }

        return Result.OK(projectMppContractList);
    }

    @PostMapping("project/mpp/file/add")
    public Result<?> projectMppFileAdd(@RequestBody ProjectMpp projectMpp) {
        return Result.OK();
    }

    @PostMapping("project/mpp/file")
    public Result<?> projectMppFile(@RequestBody ProjectMpp projectMpp) {
        ProjectMppFile projectMppFile = new ProjectMppFile();
        return Result.OK(projectMppFile);
    }

    @PostMapping("frontAfterV2")
    public Result<?> frontAfterV2(@RequestBody ProjectMpp projectMpp) {
        ProjectMppAround projectMppAround = new ProjectMppAround();
        return Result.OK(projectMppAround);
    }

    @PostMapping("project/mpp/count")
    public Result<?> projectMppCount() {
        ProjectMppCount projectMppCount = new ProjectMppCount();
        return Result.OK(projectMppCount);
    }

    @PostMapping("project/mpp/resources")
    public Result<?> projectMppResources(@RequestBody ProjectMpp projectMpp) {
        ProjectMppResources projectMppResources = new ProjectMppResources();
        return Result.OK(projectMppResources);
    }

    @PostMapping("project/mpp/resources/chart")
    public Result<?> projectMppResourcesChart(@RequestBody ProjectMpp projectMpp) {
        ProjectMppResourcesChart projectMppResourcesChart = new ProjectMppResourcesChart();
        return Result.OK(projectMppResourcesChart);
    }

}
