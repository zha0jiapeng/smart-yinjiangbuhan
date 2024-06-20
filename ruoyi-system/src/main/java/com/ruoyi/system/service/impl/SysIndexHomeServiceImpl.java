package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.ProjectMpp;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.basic.EngIndexHome;
import com.ruoyi.system.domain.basic.IndexHome;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.mapper.QualityProblemMapper;
import com.ruoyi.system.service.ProjectMppService;
import com.ruoyi.system.service.SysIndexHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SysIndexHomeServiceImpl implements SysIndexHomeService {

    @Autowired
    private ProjectMppService projectMppService;
    @Autowired
    private QualityProblemMapper qualityProblemMapper;

    @Override
    public IndexHome home() {
        IndexHome indexHome = new IndexHome();

        List<ProjectMpp> list = projectMppService.list();
        if (CollectionUtils.isEmpty(list)) {
            return indexHome;
        }

        //已完成
        int completeCount = (int) list.stream().filter(val -> DateUtils.parseDate(val.getEndDate()).after(new Date())).count();
        indexHome.setComplete(completeCount);

        //已拖延
        indexHome.setDelay(10);

        //进行中的任务
        int conductCount = (int) list.stream().filter(val -> DateUtils.parseDate(val.getStartDate()).before(new Date())
                && DateUtils.parseDate(val.getEndDate()).after(new Date())).count();
        indexHome.setConduct(conductCount);

        //未开始
        indexHome.setNoStarted(15);

        //计划任务
        indexHome.setPlanTask(10);

        //完成任务
        indexHome.setComplete(20);

        //未完成任务
        indexHome.setNoTask(30);

        //质量问题
        QualityProblem qualityProblem = new QualityProblem();
        List<QualityProblem> qualityProblems = qualityProblemMapper.queryAllByLimit(qualityProblem);
        indexHome.setQualityProblemList(qualityProblems);

        //安全问题

        //文明施工问题

        return indexHome;
    }

    @Override
    public EngIndexHome engIndex() {
        ProjectMpp projectMpp = null;
        EngIndexHome engIndexHome = new EngIndexHome();

        try {
            engIndexHome.setTitleOne("工程任务统计");

            engIndexHome.setTitleTwo("工程进度占比");
            //查询总工期 （总天数）
            QueryWrapper<ProjectMpp> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("duration_date");
            queryWrapper.last("limit 1");
            projectMpp = projectMppService.getOne(queryWrapper);
            if (null != projectMpp) {
                engIndexHome.setCountDuration(Integer.valueOf(projectMpp.getDurationDate()));
            }

            //查询已经完成的工期 （已经施工天数） 当前时间 - 施工开始时间
            queryWrapper.clear();
            queryWrapper.orderByDesc("start_date");
            queryWrapper.last("limit 1");
            projectMpp = projectMppService.getOne(queryWrapper);
            if (null != projectMpp) {
                Date date = new Date();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = simpleDateFormat.parse(projectMpp.getStartDate());

                int differentDays = differentDays(date, startDate);
                engIndexHome.setCountDuration(differentDays);
            }
        } catch (Exception e) {
            log.error("首页 工程展板 系统异常:{}", e.toString());
        }

        return engIndexHome;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 天数
     */
    private int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            // 不同年
            return day2 - day1;
        }
    }

}
