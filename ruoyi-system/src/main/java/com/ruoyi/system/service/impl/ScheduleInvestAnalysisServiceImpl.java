package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.ScheduleInvestAnalysis;
import com.ruoyi.system.domain.basic.ProjectInvestCondition;
import com.ruoyi.system.domain.bim.RateBimDomain;
import com.ruoyi.system.mapper.ScheduleInvestAnalysisMapper;
import com.ruoyi.system.service.ProjectInvestConditionService;
import com.ruoyi.system.service.ScheduleInvestAnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleInvestAnalysisServiceImpl
        extends ServiceImpl<ScheduleInvestAnalysisMapper, ScheduleInvestAnalysis>
        implements ScheduleInvestAnalysisService {

    @Resource
    private ProjectInvestConditionService projectInvestConditionService;

    @Override
    public List<RateBimDomain.InvestmentAnalysis> queryScheduleInvestAnalysisByMonthBase(String monthBase) {
        List<RateBimDomain.InvestmentAnalysis> investmentAnalysisList = new ArrayList<>();

//        String monthBaseQuery = null;
//        if (StringUtils.isNotEmpty(monthBase)) {
//            monthBaseQuery = monthBase;
//        } else {
//            monthBaseQuery = new SimpleDateFormat("MM").format(new Date());
//        }
//
//        QueryWrapper<ScheduleInvestAnalysis> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("month_base", monthBaseQuery);
//        queryWrapper.eq("yn", YnEnum.Y.getCode());
//
//        List<ScheduleInvestAnalysis> list = this.list(queryWrapper);
//        if (CollectionUtils.isEmpty(list)) {
//            return new ArrayList<>();
//        }
//
//
//        Map<String, List<ScheduleInvestAnalysis>> listMap = list.stream().collect(Collectors.groupingBy(ScheduleInvestAnalysis::getAnalysisName));
//        listMap.forEach((k, v) -> {
//            RateBimDomain.InvestmentAnalysis investmentAnalysis = new RateBimDomain.InvestmentAnalysis();
//            investmentAnalysis.setAnalysisName(k);
//            List<ScheduleInvestAnalysis> collect = v.stream().sorted(Comparator.comparing(ScheduleInvestAnalysis::getCreatedDate).reversed()).collect(Collectors.toList());
//            ScheduleInvestAnalysis scheduleInvestAnalysis = collect.stream().findFirst().orElse(new ScheduleInvestAnalysis());
//            String colour = scheduleInvestAnalysis.getColour();
//            if (StringUtils.isNotEmpty(colour)) {
//                String[] split = colour.split(",");
//                investmentAnalysis.setR(split[0]);
//                investmentAnalysis.setG(split[1]);
//                investmentAnalysis.setB(split[2]);
//            }
//
//            BigDecimal reduceSchedule = v.stream().map(ScheduleInvestAnalysis::getProportion).reduce(BigDecimal.ZERO, BigDecimal::add);
//            investmentAnalysis.setProportion(reduceSchedule.toString());
//            investmentAnalysisList.add(investmentAnalysis);
//        });

        List<ProjectInvestCondition> projectInvestConditionList = projectInvestConditionService.list();
        if (!CollectionUtils.isEmpty(projectInvestConditionList)) {
            //查询总计金额
            BigDecimal totalAmount = projectInvestConditionList.stream().map(ProjectInvestCondition::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            Map<String, List<ProjectInvestCondition>> groupMap = projectInvestConditionList.stream().collect(Collectors.groupingBy(ProjectInvestCondition::getCountGroup));

            groupMap.forEach((k, v) -> {
                RateBimDomain.InvestmentAnalysis investmentAnalysis = new RateBimDomain.InvestmentAnalysis();
                investmentAnalysis.setAnalysisName(k);

                //获取当前的总计金额
                BigDecimal groupSum = v.stream().map(ProjectInvestCondition::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                //获取当前的比例
                BigDecimal proportion = groupSum.divide(totalAmount, 2, RoundingMode.DOWN)
                        .multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.UP);


                //investmentAnalysis.setTotalAmount(groupSum);
                investmentAnalysis.setProportion(String.valueOf(proportion));

                ProjectInvestCondition projectInvestCondition = v.stream().findFirst().orElse(new ProjectInvestCondition());
                investmentAnalysis.setB(projectInvestCondition.getB());
                investmentAnalysis.setG(projectInvestCondition.getG());
                investmentAnalysis.setR(projectInvestCondition.getR());
                investmentAnalysisList.add(investmentAnalysis);
            });

           /* for(Map.Entry entry : groupMap.entrySet()){
                RateBimDomain.ProjectInvestCountModel model = new RateBimDomain.ProjectInvestCountModel();
                model.setCountGroup((String) entry.getKey());
                List<ProjectInvestCondition> valueList = (List<ProjectInvestCondition>) entry.getValue();
                //获取当前的总计金额
                BigDecimal groupSum = valueList.stream().map(ProjectInvestCondition::getTotalAmount).reduce(BigDecimal.ZERO,
                        BigDecimal::add);
                //获取当前的比例
                BigDecimal proportion = groupSum.divide(totalAmount);
                model.setTotalAmount(groupSum);
                model.setProportion(String.valueOf(proportion));
                ProjectInvestCondition projectInvestCondition = valueList.stream().findFirst().orElse(new ProjectInvestCondition());
                model.setB(projectInvestCondition.getB());
                model.setG(projectInvestCondition.getG());
                model.setR(projectInvestCondition.getR());
                list.add(model);
            }*/
        }

        return investmentAnalysisList;
    }

}
