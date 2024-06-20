package com.ruoyi.web.controller.basic.view.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.ScheduleInvestAnalysis;
import com.ruoyi.system.service.ScheduleInvestAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("scheduleInvestAnalysis")
public class ScheduleInvestAnalysisController extends BaseController {

    @Resource
    private ScheduleInvestAnalysisService scheduleInvestAnalysisService;

    @GetMapping("/list")
    public TableDataInfo list(ScheduleInvestAnalysis scheduleInvestAnalysis) {
        startPage();
        QueryWrapper<ScheduleInvestAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ScheduleInvestAnalysis> listPage = scheduleInvestAnalysisService.list(queryWrapper);
        return getDataTable(listPage);
    }

    @GetMapping("/getCurrentWeek")
    public AjaxResult getCurrentWeek() {
        ScheduleInvestAnalysis scheduleInvestAnalysis = new ScheduleInvestAnalysis();

        //年份
        scheduleInvestAnalysis.setYearBase(new SimpleDateFormat("yyyy").format(new Date()));

        //月份
        scheduleInvestAnalysis.setMonthBase(new SimpleDateFormat("MM").format(new Date()));

        //周
        StringBuilder stringBuilder = new StringBuilder();
        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        Integer week = c.get(Calendar.WEEK_OF_YEAR);
        stringBuilder.append("今年第").append(week).append("周");
        scheduleInvestAnalysis.setWeekBase(stringBuilder.toString());

        return AjaxResult.success(scheduleInvestAnalysis);
    }

    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ScheduleInvestAnalysis scheduleInvestAnalysis) {
        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        int week = c.get(Calendar.WEEK_OF_YEAR);

        //根据年份+月份+周+名称判断是否存在
        QueryWrapper<ScheduleInvestAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year_base", scheduleInvestAnalysis.getYearBase());
        queryWrapper.eq("month_base", scheduleInvestAnalysis.getMonthBase());
        queryWrapper.eq("week_base", String.valueOf(week));
        queryWrapper.eq("analysis_name", scheduleInvestAnalysis.getAnalysisName());
        queryWrapper.eq("yn", YnEnum.Y.getCode());

        ScheduleInvestAnalysis scheduleInvestAnalysisServiceOne = scheduleInvestAnalysisService.getOne(queryWrapper);
        if (null != scheduleInvestAnalysisServiceOne) {
            return AjaxResult.error(scheduleInvestAnalysis.getAnalysisName() + "本周已经录入,无法重复录入。");
        }

        String colour = scheduleInvestAnalysis.getColour();
        if (StringUtils.isNotEmpty(colour)) {
            String substring = colour.substring(4, colour.length() - 1);
            scheduleInvestAnalysis.setColour(substring);
        } else {
            //查询之前周的颜色
            queryWrapper.clear();
            queryWrapper.eq("year_base", scheduleInvestAnalysis.getYearBase());
            queryWrapper.eq("month_base", scheduleInvestAnalysis.getMonthBase());
            queryWrapper.eq("week_base", String.valueOf(week - 1));
            queryWrapper.eq("analysis_name", scheduleInvestAnalysis.getAnalysisName());
            queryWrapper.eq("yn", YnEnum.Y.getCode());
            scheduleInvestAnalysisServiceOne = scheduleInvestAnalysisService.getOne(queryWrapper);
            scheduleInvestAnalysis.setColour(scheduleInvestAnalysisServiceOne.getColour());
        }

        scheduleInvestAnalysis.setWeekBase(Integer.toString(week));
        scheduleInvestAnalysis.setYn(YnEnum.Y.getCode());
        scheduleInvestAnalysis.setCreatedBy(getUsername());
        scheduleInvestAnalysis.setCreatedDate(new Date());
        scheduleInvestAnalysisService.save(scheduleInvestAnalysis);

        return AjaxResult.success();
    }

}
