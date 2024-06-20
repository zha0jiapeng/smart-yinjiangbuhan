package com.ruoyi.web.controller.basic.view.bl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.enums.BasicDataValueConfigEnum;
import com.ruoyi.common.enums.ProjectCalculateEnum;
import com.ruoyi.system.domain.DateCommonParam;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.domain.bim.RateBimDomain;
import com.ruoyi.system.entity.BasicDataValueConfig;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.entity.ProjectConstruction;
import com.ruoyi.system.service.BasicDataValueConfigService;
import com.ruoyi.system.service.ProjectCalculateService;
import com.ruoyi.system.service.ProjectConstructionService;
import com.ruoyi.system.service.ScheduleHumanUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("project/bi")
public class ProjectBIController {
    @Resource
    private ProjectCalculateService projectCalculateService;
    @Resource
    private ProjectConstructionService projectConstructionService;
    @Autowired
    private BasicDataValueConfigService basicDataValueConfigService;

    @Resource
    private ScheduleHumanUploadService scheduleHumanUploadService;

    @SuppressWarnings("all")
    @PostMapping("/top")
    public ProjectBI top(@RequestBody DateCommonParam dateCommonParam) throws ParseException {
        ProjectBI projectBI = new ProjectBI();

        List<BasicDataValueConfig> basicDataValueConfigs = basicDataValueConfigService.list();

        Map<String, BasicDataValueConfig> dataValueConfigMap = basicDataValueConfigs.stream()
                .collect(Collectors.toMap(BasicDataValueConfig::getBasicKey, el -> el, (e1, e2) -> e1));

        BasicDataValueConfig PROJECT_OVERVIEW_CONFIG = dataValueConfigMap.get(BasicDataValueConfigEnum.project_overview_config.getCode());
        ProjectBI.Overview overview = JSON.parseObject(PROJECT_OVERVIEW_CONFIG.getBasicValue(), ProjectBI.Overview.class);
        projectBI.setOverview(overview);

        //查询今天审核通过的数据
        ProjectCalculate projectCalculate = new ProjectCalculate();
        projectCalculate.setCheckState(3L);
        List<ProjectCalculate> projectCalculates = projectCalculateService.queryByPage(projectCalculate);

        if (CollectionUtils.isEmpty(projectCalculates)) {
            //查询昨天数据
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //创建Calendar 的实例
            Calendar calendar = Calendar.getInstance();
            //当前时间减去一天，即一天前的时间
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            //昨天查不到,不参与计算,直接返回。
            projectCalculate.setCheckDateStart(simpleDateFormat.format(calendar.getTime()) + " 00:00:00");
            projectCalculate.setCheckDateEnd(simpleDateFormat.format(calendar.getTime()) + " 23:59:59");
            projectCalculates = projectCalculateService.queryByPage(projectCalculate);
            if (CollectionUtils.isEmpty(projectCalculates)) {
                log.info("昨天查不到,不参与计算,直接返回");
            }
        }

        Map<String, List<ProjectCalculate>> listMap = projectCalculates.stream()
                .filter(val -> val.getModelKey() != null)
                .collect(Collectors.groupingBy(ProjectCalculate::getModelKey));

        ProjectBI.TargetAdvance targetAdvance = new ProjectBI.TargetAdvance();
        ProjectBI.ManpowerResource manpowerResource = new ProjectBI.ManpowerResource();
        List<ScheduleHumanUpload> scheduleHumanUploads = scheduleHumanUploadService.bimNewListScheduleHumanUpload();

        List<String> uploadDateList = new ArrayList<>();
        List<ProjectBI.ManpowerResourceVo> manpowerResourceVoList = new ArrayList<>();

        scheduleHumanUploads.stream().forEach(var -> {
            String date = DateUtil.format(var.getUploadDate(), "yyyy-MM-dd");
            uploadDateList.add(date);

            ProjectBI.ManpowerResourceVo manpowerResourceVo = new ProjectBI.ManpowerResourceVo();
            manpowerResourceVo.setShijian(date);
            manpowerResourceVo.setJihuatouru(var.getWeekPlanHuman());
            manpowerResourceVo.setShijitouru(var.getWeekRealityHuman());
            manpowerResourceVoList.add(manpowerResourceVo);
        });
        targetAdvance.setJihuashijian(uploadDateList);
        targetAdvance.setPlanJindu(scheduleHumanUploads.stream().map(ScheduleHumanUpload::getPlanAccumulateAccumulate).collect(Collectors.toList()));
        targetAdvance.setWanchengjindu(scheduleHumanUploads.stream().map(ScheduleHumanUpload::getRealityAccumulateAccumulate).collect(Collectors.toList()));

        manpowerResource.setRenliziyuantouru(manpowerResourceVoList);

        projectBI.setTargetAdvance(targetAdvance);
        projectBI.setManpowerResource(manpowerResource);



        //施工进度曲线
       /* ProjectBI.TargetAdvance targetAdvance = new ProjectBI.TargetAdvance();
        if (listMap.containsKey(ProjectCalculateEnum.CONSTRUCTION_PROGRESS_CURVE.getDesc())) {
            List<ProjectCalculate> projectCalculateList = listMap.get(ProjectCalculateEnum.CONSTRUCTION_PROGRESS_CURVE.getDesc());
            //根据创建时间倒叙排序
            projectCalculateList = projectCalculateList.stream()
                    .sorted(Comparator.comparing(ProjectCalculate::getCreatedDate).reversed()).collect(Collectors.toList());

            ProjectCalculate calculate = projectCalculateList.stream().findFirst().orElse(new ProjectCalculate());
            String modelValue = calculate.getModelValue();
            Map map = JSON.parseObject(modelValue, Map.class);
            targetAdvance.setJihuashijian((List<String>) map.get("jihuashijian"));
            targetAdvance.setWanchengjindu((List<String>) map.get("wanchengjindu"));
            targetAdvance.setPlanJindu((List<String>) map.get("planJindu"));
        } else {
            targetAdvance.setJihuashijian(new ArrayList<>());
            targetAdvance.setWanchengjindu(new ArrayList<>());
            targetAdvance.setPlanJindu(new ArrayList<>());
        }
        projectBI.setTargetAdvance(targetAdvance);*/

        // 人力资源投入
       /* ProjectBI.ManpowerResource manpowerResource = new ProjectBI.ManpowerResource();
        if (listMap.containsKey(ProjectCalculateEnum.PROJECT_COMPLETION_PROGRESS.getDesc())) {
            List<ProjectCalculate> projectCalculateList = listMap.get(ProjectCalculateEnum.PROJECT_COMPLETION_PROGRESS.getDesc());
            //根据创建时间倒叙排序
            projectCalculateList = projectCalculateList.stream()
                    .sorted(Comparator.comparing(ProjectCalculate::getCreatedDate).reversed()).collect(Collectors.toList());

            ProjectCalculate calculate = projectCalculateList.stream().findFirst().orElse(new ProjectCalculate());
            String modelValue = calculate.getModelValue();
            List<Map> maps = JSON.parseArray(modelValue, Map.class);

            List<ProjectBI.ManpowerResourceVo> manpowerResourceVoList = new ArrayList<>();
            for (Map map : maps) {
                ProjectBI.ManpowerResourceVo manpowerResourceVo = new ProjectBI.ManpowerResourceVo();
                manpowerResourceVo.setShijian((String) map.get("shijian"));
                manpowerResourceVo.setJihuatouru(map.get("jihuatouru").toString());
                manpowerResourceVo.setShijitouru(map.get("shijitouru").toString());
                manpowerResourceVoList.add(manpowerResourceVo);
            }
            manpowerResource.setRenliziyuantouru(manpowerResourceVoList);
        } else {
            manpowerResource.setRenliziyuantouru(new ArrayList<>());
        }
        projectBI.setManpowerResource(manpowerResource);*/

        //任务详情
        List<ProjectBI.TaskDetails> taskDetailsList = new ArrayList<>();
        ProjectConstruction projectConstruction = new ProjectConstruction();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        projectConstruction.setStartDate(dateCommonParam.getMoon());
        projectConstruction.setEndDate(dateCommonParam.getMoon() + "-31 23:59:59");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ProjectConstruction> projectConstructionList = projectConstructionService.reportChart(projectConstruction);
        if (CollectionUtils.isEmpty(projectCalculates)) {
            projectBI.setTaskDetails(taskDetailsList);
            return projectBI;
        }

        for (ProjectConstruction var : projectConstructionList) {
            ProjectBI.TaskDetails taskDetails = new ProjectBI.TaskDetails();
            taskDetails.setTaskName(var.getEngineeringDescription());
            taskDetails.setProportion(var.getTotalFinish());
            if (!StringUtils.isEmpty(var.getPlanBegin()) && !StringUtils.isEmpty(var.getPlanFinish())) {
                taskDetails.setPlanStartDate(var.getPlanBegin());
                taskDetails.setPlanEndDate(var.getPlanFinish());
                taskDetails.setPlanDate(getDay(var.getPlanBegin(), var.getPlanFinish(), dateCommonParam.getMoon()));
            }

            //实际开始时间结束时间,算延期
            if (!StringUtils.isEmpty(var.getPlanBegin()) && !StringUtils.isEmpty(var.getActualBegin())) {
                Date dateOne = simpleDateFormat.parse(var.getActualBegin());
                Date dateTo = simpleDateFormat.parse(var.getPlanBegin());
                if (dateOne.after(dateTo)) {
                    taskDetails.setExtension(getDay(var.getActualBegin(), var.getPlanBegin(), dateCommonParam.getMoon()));
                }
            }

            if (!StringUtils.isEmpty(var.getActualBegin()) && !StringUtils.isEmpty(var.getActualFinish())) {
                taskDetails.setRealityStartDate(var.getActualBegin());
                taskDetails.setRealityEndDate(var.getActualFinish());
                taskDetails.setRealityDate(getDay(var.getActualBegin(), var.getActualFinish(), dateCommonParam.getMoon()));
            } else {
                taskDetails.setExtension(getDay(var.getPlanBegin(), var.getPlanFinish(), dateCommonParam.getMoon()));
            }
            taskDetailsList.add(taskDetails);
        }

        projectBI.setTaskDetails(taskDetailsList);
        return projectBI;
    }

    public static List<String> getDay(String startTime, String endTime, String endDate) {
        return getDays(startTime, endTime);
    }

    public static List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            // 日期加1(包含结束)
            tempEnd.add(Calendar.DATE, +1);
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            log.error("获取时间段出现异常");
        }

        return days;
    }


}
