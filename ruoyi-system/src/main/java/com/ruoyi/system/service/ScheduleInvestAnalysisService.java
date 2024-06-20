package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ScheduleInvestAnalysis;
import com.ruoyi.system.domain.bim.RateBimDomain;

import java.util.List;

public interface ScheduleInvestAnalysisService extends IService<ScheduleInvestAnalysis> {


    List<RateBimDomain.InvestmentAnalysis> queryScheduleInvestAnalysisByMonthBase(String monthBase);

}
