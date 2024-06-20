package com.ruoyi.web.controller.basic.view.bim;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.system.domain.basic.ProjectInvestAmount;
import com.ruoyi.system.domain.basic.ProjectInvestCondition;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.domain.bim.RateBimDomain;
import com.ruoyi.system.domain.bim.RateBimDomainParam;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.service.*;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("bim/project")
public class BimProjectController {
    @Autowired
    private ProjectMppService projectMppService;
    @Resource
    private ProjectCalculateService projectCalculateService;
    @Resource
    private ScheduleInvestAnalysisService scheduleInvestAnalysisService;
    @Resource
    private ScheduleTbmSegmentService scheduleTbmSegmentService;

    @Resource
    private ScheduleHumanUploadService scheduleHumanUploadService;

    @Resource
    private ProjectInvestAmountService projectInvestAmountService;
    @Resource
    private ProjectInvestConditionService projectInvestConditionService;

    /**
     * 进度管理
     *
     * @return 结果
     */
    @SuppressWarnings("all")
    @PostMapping("/rate")
    public Result<?> rate(RateBimDomainParam rateBimDomainParam) {
        RateBimDomain rateBimDomain = new RateBimDomain();

        //投资分析
        List<RateBimDomain.InvestmentAnalysis> investmentAnalysisList =
                scheduleInvestAnalysisService.queryScheduleInvestAnalysisByMonthBase(rateBimDomainParam.getMonthBase());
        rateBimDomain.setInvestmentAnalysisList(investmentAnalysisList);

        //管片TBM分析
        RateBimDomain.ScheduleTbmSegment scheduleTbmSegment =
                scheduleTbmSegmentService.queryScheduleTbmSegmentByMonthBase(rateBimDomainParam.getMonthBase());
        rateBimDomain.setScheduleTbmSegment(scheduleTbmSegment);

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
                return Result.ok(rateBimDomain);
            }
        }

        Map<String, List<ProjectCalculate>> listMap = projectCalculates.stream()
                .filter(val -> val.getModelKey() != null)
                .collect(Collectors.groupingBy(ProjectCalculate::getModelKey));

        RateBimDomain.TargetAdvance targetAdvance = new RateBimDomain.TargetAdvance();
        RateBimDomain.ManpowerResource manpowerResource = new RateBimDomain.ManpowerResource();
        List<ScheduleHumanUpload> scheduleHumanUploads = scheduleHumanUploadService.bimNewListScheduleHumanUpload();

        List<String> uploadDateList = new ArrayList<>();
        List<RateBimDomain.ManpowerResourceVo> manpowerResourceVoList = new ArrayList<>();

        scheduleHumanUploads.stream().forEach(var -> {
            String date = DateUtil.format(var.getUploadDate(), "yyyy-MM-dd");
            uploadDateList.add(date);

            RateBimDomain.ManpowerResourceVo manpowerResourceVo = new RateBimDomain.ManpowerResourceVo();
            manpowerResourceVo.setShijian(date);
            manpowerResourceVo.setJihuatouru(var.getWeekPlanHuman());
            manpowerResourceVo.setShijitouru(var.getWeekRealityHuman());
            manpowerResourceVoList.add(manpowerResourceVo);
        });
        targetAdvance.setJihuashijian(uploadDateList);
        targetAdvance.setPlanJindu(scheduleHumanUploads.stream().map(ScheduleHumanUpload::getPlanAccumulateAccumulate).collect(Collectors.toList()));
        targetAdvance.setWanchengjindu(scheduleHumanUploads.stream().map(ScheduleHumanUpload::getRealityAccumulateAccumulate).collect(Collectors.toList()));

        manpowerResource.setRenliziyuantouru(manpowerResourceVoList);

        rateBimDomain.setShigongjinduquxian(targetAdvance);
        rateBimDomain.setRenliziyuan(manpowerResource);

        /*//施工进度曲线
        RateBimDomain.TargetAdvance targetAdvance = new RateBimDomain.TargetAdvance();
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
        rateBimDomain.setShigongjinduquxian(targetAdvance);*/

        // 投资进度
        RateBimDomain.PayScheduleModel payScheduleModel = new RateBimDomain.PayScheduleModel();
      /*  if (listMap.containsKey(ProjectCalculateEnum.INVESTMENT_PROGRESS.getDesc())) {
            List<ProjectCalculate> projectCalculateList = listMap.get(ProjectCalculateEnum.INVESTMENT_PROGRESS.getDesc());
            //根据创建时间倒叙排序
            projectCalculateList = projectCalculateList.stream()
                    .sorted(Comparator.comparing(ProjectCalculate::getCreatedDate).reversed()).collect(Collectors.toList());

            ProjectCalculate calculate = projectCalculateList.stream().findFirst().orElse(new ProjectCalculate());

            String modelValue = calculate.getModelValue();
            Map map = JSON.parseObject(modelValue, Map.class);
            payScheduleModel.setYujifukuan(map.get("yujifukuan").toString());
            payScheduleModel.setYifukuan(map.get("yifukuan").toString());
            payScheduleModel.setZhengchang(map.get("zhengchang").toString());
            payScheduleModel.setWeizhifu(map.get("weizhifu").toString());
            payScheduleModel.setChaoe(map.get("chaoe").toString());
        } else {
            payScheduleModel.setYujifukuan("");
            payScheduleModel.setYifukuan("");
            payScheduleModel.setZhengchang("");
            payScheduleModel.setWeizhifu("");
            payScheduleModel.setChaoe("");
        }*/

        ProjectInvestAmount projectInvestAmount = projectInvestAmountService.queryOneProjectInvestAmount(new ProjectInvestAmount());
        payScheduleModel.setYujifukuan(projectInvestAmount.getEstimateAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        payScheduleModel.setYifukuan(projectInvestAmount.getInvestAmount().setScale(0, BigDecimal.ROUND_HALF_UP).toString());

        List<ProjectInvestCondition> projectInvestConditions = projectInvestConditionService.queryProjectInvestCondition(new ProjectInvestCondition());
        BigDecimal sum = projectInvestConditions.stream().map(ProjectInvestCondition::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        payScheduleModel.setZhengchang(sum.toString());

        //以投资 - 工程量 为正数 超额 未支付为0
        //以投资 - 工程量 为负数 取绝对值 未支付 超额为0
        BigDecimal subtract = projectInvestAmount.getInvestAmount().subtract(sum);
        if (subtract.compareTo(BigDecimal.ZERO) == 1) {
            payScheduleModel.setWeizhifu("0");
            payScheduleModel.setChaoe(subtract.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            payScheduleModel.setWeizhifu(subtract.abs().setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            payScheduleModel.setChaoe("0");
        }

        rateBimDomain.setFukuanjindu(payScheduleModel);

        // 人力资源投入
        /*RateBimDomain.ManpowerResource manpowerResource = new RateBimDomain.ManpowerResource();
        if (listMap.containsKey(ProjectCalculateEnum.PROJECT_COMPLETION_PROGRESS.getDesc())) {
            List<ProjectCalculate> projectCalculateList = listMap.get(ProjectCalculateEnum.PROJECT_COMPLETION_PROGRESS.getDesc());
            //根据创建时间倒叙排序
            projectCalculateList = projectCalculateList.stream()
                    .sorted(Comparator.comparing(ProjectCalculate::getCreatedDate).reversed()).collect(Collectors.toList());

            ProjectCalculate calculate = projectCalculateList.stream().findFirst().orElse(new ProjectCalculate());
            String modelValue = calculate.getModelValue();
            List<Map> maps = JSON.parseArray(modelValue, Map.class);

            List<RateBimDomain.ManpowerResourceVo> manpowerResourceVoList = new ArrayList<>();
            for (Map map : maps) {
                RateBimDomain.ManpowerResourceVo manpowerResourceVo = new RateBimDomain.ManpowerResourceVo();
                manpowerResourceVo.setShijian((String) map.get("shijian"));
                manpowerResourceVo.setJihuatouru(map.get("jihuatouru").toString());
                manpowerResourceVo.setShijitouru(map.get("shijitouru").toString());
                manpowerResourceVoList.add(manpowerResourceVo);
            }
            manpowerResource.setRenliziyuantouru(manpowerResourceVoList);
        } else {
            manpowerResource.setRenliziyuantouru(new ArrayList<>());
        }
        rateBimDomain.setRenliziyuan(manpowerResource);*/

        // 进度展现
        RateBimDomain.ProgressShow progressShow = new RateBimDomain.ProgressShow();
        progressShow.setYanqi(1);
        progressShow.setYiwancheng(2);
        rateBimDomain.setJinduzhanxian(progressShow);

        return Result.OK(rateBimDomain);
    }

    @PostMapping(value = "/list")
    public Result<?> list() {
        List<ProjectMpp> projectMppList = projectMppService.list();
        return Result.ok(projectMppList);
    }

    @PostMapping(value = "/update/model")
    public Result<?> updModel(@RequestBody ProjectMpp projectMpp) {
        projectMppService.updateById(projectMpp);
        return Result.ok();
    }

}
