package com.ruoyi.web.controller.basic.view.danger;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.assessment.core.utils.StringUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.entity.SysHiddenDanger;
import com.ruoyi.system.entity.SysHiddenDangerFiles;
import com.ruoyi.system.entity.SysQuestionDescribe;
import com.ruoyi.system.mapper.SysHiddenDangerLogMapper;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/sysHiddenDanger")
public class SysHiddenDangerController extends BaseController {
    @Autowired
    private SysHiddenDangerService sysHiddenDangerService;
    @Autowired
    private SysQuestionDescribeService sysQuestionDescribeService;
    @Autowired
    private SysHiddenDangerFilesService sysHiddenDangerFilesService;

    @Resource
    private SysUserNoticeService sysUserNoticeService;
    @Autowired
    private FlowPathConfigService flowPathConfigService;

    @Resource
    private SysHiddenDangerLogMapper sysHiddenDangerLogMapper;


    @GetMapping("/list")
    public TableDataInfo queryByPage(SysHiddenDanger sysHiddenDanger) {
        log.info("sysHiddenDanger :{}", JSON.toJSON(sysHiddenDanger));
        if (sysHiddenDanger.getMoon() != null) {
            String str = sysHiddenDanger.getMoon();
            if (str.length() < 2) {
                str = 0 + str;
            } else {
                str = str;
            }
            sysHiddenDanger.setMonth(str);
        }

        if (!StringUtils.isBlank(sysHiddenDanger.getProblemBase())) {
            QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("problem_content", sysHiddenDanger.getProblemBase());
            SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(queryWrapper);
            if (null != describeServiceOne) {
                sysHiddenDanger.setProblemId(describeServiceOne.getId());
            }
        }

        startPage();
        List<SysHiddenDanger> inspectionPlans = this.sysHiddenDangerService.queryByPage(sysHiddenDanger);
        return getDataTable(inspectionPlans);
    }

    @PostMapping("week/count")
    public AjaxResult weekCount() {
        List<WeekCount> weekCounts = new ArrayList<>();

        QueryWrapper<SysHiddenDanger> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<SysHiddenDanger> list = sysHiddenDangerService.list(queryWrapper);

        Map<String, List<SysHiddenDanger>> collect = list.stream().filter(f -> f.getWeek() != null)
                .collect(Collectors.groupingBy(SysHiddenDanger::getWeek, Collectors.toList()));

        collect.forEach((k, v) -> {
            WeekCount weekCount = new WeekCount();
            weekCount.setWeek(k);
            weekCount.setCount(v.size());

            weekCounts.add(weekCount);
        });

        return AjaxResult.success(weekCounts);
    }

    @PostMapping("moon/count")
    public AjaxResult moonCount() {

        List<MoonCount> moonCounts = new ArrayList<>();

        String moonStr = "01,02,03,04,05,06,07,08,09,10,11,12";
        String mm = new SimpleDateFormat("MM").format(new Date());
        String substring = moonStr.substring(0, moonStr.indexOf(mm) + 2);
        List<String> mList = new ArrayList<>();
        if (substring.contains(",")) {
            String[] split = substring.split(",");
            mList = Arrays.asList(split);
        } else {
            mList.add(substring);
        }

        for (String str : mList) {
            MoonCount moonCount = new MoonCount();
            moonCount.setMoon(Integer.valueOf(str));
            QueryWrapper<SysHiddenDanger> filesQueryWrapper = new QueryWrapper<>();
            filesQueryWrapper.eq("month", str);
            int count = sysHiddenDangerService.count(filesQueryWrapper);
            moonCount.setCount(count);
            moonCounts.add(moonCount);
        }

        return AjaxResult.success(moonCounts);
    }

    @PostMapping("/rectified")
    public TableDataInfo rectified(SysHiddenDanger sysHiddenDanger) {
        startPage();
        sysHiddenDanger.setStatus(0);
        log.info("查询条件:{}", JSON.toJSON(sysHiddenDanger));
        List<SysHiddenDanger> inspectionPlans = this.sysHiddenDangerService.queryByPage(sysHiddenDanger);

        if (!CollectionUtils.isEmpty(inspectionPlans)) {
            inspectionPlans.forEach(val -> {
                QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", val.getProblemId());
                SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(queryWrapper);
                if (null != describeServiceOne) {
                    val.setProblemBase(describeServiceOne.getProblemContent());
                }
            });
        }

        return getDataTable(inspectionPlans);
    }

    @PostMapping("/approve")
    public AjaxResult updateStatus(@RequestBody SysHiddenDanger sysHiddenDanger) {
        log.info("审批参数:{}", JSON.toJSON(sysHiddenDanger));
        //审批拒绝回到待整改
        if (sysHiddenDanger.getStatus() == 3) {
            sysHiddenDanger.setStatus(0);
        }
        //加入审核时间
        sysHiddenDanger.setExamineVerifyDate(new Date());
        sysHiddenDangerService.updateStatus(sysHiddenDanger);
        return AjaxResult.success();
    }

    @PostMapping("/reform")
    public AjaxResult updateFour(@RequestBody SysHiddenDanger sysHiddenDanger) {
        sysHiddenDanger.setStatus(1);
        sysHiddenDangerService.updateFour(sysHiddenDanger);

        SysHiddenDangerLog sysHiddenDangerLog = new SysHiddenDangerLog();
        sysHiddenDangerLog.setPid(sysHiddenDanger.getId().intValue());
        sysHiddenDangerLog.setFileUrl(sysHiddenDanger.getFileUrl());
        sysHiddenDangerLog.setChangeDescribe(sysHiddenDanger.getChangeDescribe());
        sysHiddenDangerLog.setCreatedDate(new Date());
        sysHiddenDangerLogMapper.insert(sysHiddenDangerLog);

        return AjaxResult.success();
    }

    @GetMapping("/info")
    public SysHiddenDanger info(Integer id) {
        SysHiddenDanger dangerServiceById = sysHiddenDangerService.getById(id);
        if (null != dangerServiceById) {

            QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", dangerServiceById.getProblemId());
            SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(queryWrapper);
            if (null != describeServiceOne) {
                dangerServiceById.setProblemBase(describeServiceOne.getProblemContent());
            }

            List<SysHiddenDangerFiles> sysHiddenDangerFiles = new ArrayList<>();

            String fileId = dangerServiceById.getFileId();
            if (!StringUtils.isBlank(fileId)) {
                fileId = fileId.substring(0, fileId.length() - 1);
                if (fileId.contains(",")) {
                    String[] split = fileId.split(",");
                    QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                    filesQueryWrapper.in("id", Arrays.asList(split));
                    sysHiddenDangerFiles.addAll(sysHiddenDangerFilesService.list(filesQueryWrapper));
                } else {
                    QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                    filesQueryWrapper.eq("id", fileId);
                    SysHiddenDangerFiles one = sysHiddenDangerFilesService.getOne(filesQueryWrapper);
                    sysHiddenDangerFiles.add(one);
                }
            }
            dangerServiceById.setSysHiddenDangerFiles(sysHiddenDangerFiles);
        }
        return dangerServiceById;
    }

    @PostMapping("/add")
    public AjaxResult create(@RequestBody SysHiddenDanger sysHiddenDanger) {
        log.info("日常巡检 请求参数:{}", JSON.toJSON(sysHiddenDanger));

        StringBuilder key = new StringBuilder();
        if (!CollectionUtils.isEmpty(sysHiddenDanger.getSysHiddenDangerFiles())) {
            for (SysHiddenDangerFiles sysHiddenDangerFile : sysHiddenDanger.getSysHiddenDangerFiles()) {
                sysHiddenDangerFilesService.save(sysHiddenDangerFile);
                key.append(sysHiddenDangerFile.getId());
                key.append(",");
            }
        }

        //根据问题项查询模版是否存在;
        QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_content", sysHiddenDanger.getProblem());
        List<SysQuestionDescribe> list = sysQuestionDescribeService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            SysQuestionDescribe sysQuestionDescribe = new SysQuestionDescribe();
            sysQuestionDescribe.setPid(sysHiddenDanger.getProblemId());
            sysQuestionDescribe.setProblemContent(sysHiddenDanger.getProblem());
            sysQuestionDescribeService.save(sysQuestionDescribe);
        }

        if (!StringUtils.isBlank(key.toString())) {
            sysHiddenDanger.setFileId(key.toString());
        }

        sysHiddenDanger.setDelFlag(0);

        if (sysHiddenDanger.getTaskType() == 1) {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            sysHiddenDanger.setCreateTime(format);
        }
        if (sysHiddenDanger.getTaskType() == 2) {
            Calendar c = Calendar.getInstance();
            // 一年内的第xx周//获取当前星期是第几周
            Integer week = c.get(Calendar.WEEK_OF_YEAR);
            sysHiddenDanger.setWeek(String.valueOf(week));
        }
        if (sysHiddenDanger.getTaskType() == 3) {
            String mm = new SimpleDateFormat("MM").format(new Date());
            sysHiddenDanger.setMonth(mm);
        }

        sysHiddenDanger.setStatus(0);
        sysHiddenDanger.setCreateBy(getUsername());
        sysHiddenDangerService.create(sysHiddenDanger);

        SysUserNotice sysUserNotice = new SysUserNotice();
        sysUserNotice.setNoticeTitle(getUsername());
        sysUserNotice.setNoticeType("通知");
        sysUserNotice.setContent(sysHiddenDanger.getProblem());
        sysUserNotice.setStatus("正常");
        sysUserNotice.setUserId("1");

        sysUserNotice.setUserId(getUserId().toString());
        sysUserNotice.setModelName("安全模块");
        sysUserNotice.setYn(YnEnum.Y.getCode());
        sysUserNoticeService.insert(sysUserNotice);

        return AjaxResult.success();
    }

    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable String ids) {
        QueryWrapper<FlowPathConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.eq("flow_path_code", Constants.SECURE_PROBLEM_DELETE);
        List<FlowPathConfig> list = flowPathConfigService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.error("未配置审批流程!");
        }

        List<Integer> flowReviewedByIds = list.stream().map(FlowPathConfig::getFlowReviewedById).collect(Collectors.toList());

        //获取当前登录用户
        Long userId = SecurityUtils.getLoginUser().getUserId();

        if (!flowReviewedByIds.contains(userId.intValue())) {
            return AjaxResult.error("未获得审批权限,无法完成!");
        }

        sysHiddenDangerService.delete(Integer.valueOf(ids));
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysHiddenDanger sysHiddenDanger) {
        sysHiddenDangerService.update(sysHiddenDanger);
        return AjaxResult.success();
    }

    @GetMapping("/info/log")
    public List<SysHiddenDangerLog> infoLog(Integer id) {
        QueryWrapper<SysHiddenDangerLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        return sysHiddenDangerLogMapper.selectList(queryWrapper);
    }

}
