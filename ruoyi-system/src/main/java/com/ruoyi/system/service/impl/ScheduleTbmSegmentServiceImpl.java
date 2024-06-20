package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.domain.bim.RateBimDomain;
import com.ruoyi.system.entity.TbmWorkProgressLog;
import com.ruoyi.system.mapper.ScheduleTbmSegmentMapper;
import com.ruoyi.system.mapper.TbmWorkProgressLogMapper;
import com.ruoyi.system.service.ScheduleTbmSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleTbmSegmentServiceImpl extends ServiceImpl<ScheduleTbmSegmentMapper, ScheduleTbmSegment> implements ScheduleTbmSegmentService {

    @Autowired
    private TbmWorkProgressLogMapper tbmWorkProgressLogMapper;

    @Override
    public RateBimDomain.ScheduleTbmSegment queryScheduleTbmSegmentByMonthBase(String monthBase) {
        RateBimDomain.ScheduleTbmSegment scheduleTbmSegment = new RateBimDomain.ScheduleTbmSegment();

        //查询今天的数据
        QueryWrapper<ScheduleTbmSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("day_base", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ScheduleTbmSegment> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            //今天数据没查询到,查询昨天的数据。
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //创建Calendar 的实例
            Calendar calendar = Calendar.getInstance();
            //当前时间减去一天，即一天前的时间
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            queryWrapper.clear();

            queryWrapper.eq("day_base", simpleDateFormat.format(calendar.getTime()));
            list = this.list(queryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                return scheduleTbmSegment;
            }
        }

        List<ScheduleTbmSegment> tbmSegments = list.stream().sorted(Comparator.comparing(ScheduleTbmSegment::getDayBase).reversed()).collect(Collectors.toList());
        ScheduleTbmSegment tbmSegment = tbmSegments.stream().findFirst().orElse(new ScheduleTbmSegment());
        scheduleTbmSegment.setSegmentProduce(tbmSegment.getScheduleRealityPrice());
        scheduleTbmSegment.setTbmProduce(tbmSegment.getTbmRealityPrice());

        //查询当月计划信息
        String monthBaseQuery = null;
        if (StringUtils.isNotEmpty(monthBase)) {
            monthBaseQuery = monthBase;
        } else {
            monthBaseQuery = new SimpleDateFormat("MM").format(new Date());
        }
        queryWrapper.clear();
        queryWrapper.eq("create_type", YnEnum.Y.getCode());
        queryWrapper.eq("month_base", monthBaseQuery);
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        ScheduleTbmSegment one = this.getOne(queryWrapper);
        if (null != one) {
            scheduleTbmSegment.setPlanTbmProduce(one.getTbmPlanPrice().toString());
            scheduleTbmSegment.setPlanSegmentProduce(one.getSchedulePlanPrice().toString());
        }

        //查询当月实际信息
        queryWrapper.clear();
        queryWrapper.eq("create_type", 2);
        queryWrapper.ge("day_base", new SimpleDateFormat("yyyy-MM").format(new Date()) + "-01")
                .le("day_base", new SimpleDateFormat("yyyy-MM").format(new Date()) + "-31");
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ScheduleTbmSegment> scheduleTbmSegments = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(scheduleTbmSegments)) {
            BigDecimal reduceTbm = scheduleTbmSegments.stream().map(ScheduleTbmSegment::getTbmRealityPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            scheduleTbmSegment.setRealityTbmProduce(reduceTbm.toString());
            BigDecimal reduceSchedule = scheduleTbmSegments.stream().map(ScheduleTbmSegment::getScheduleRealityPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            scheduleTbmSegment.setRealitySegmentProduce(reduceSchedule.toString());
        }

        return scheduleTbmSegment;
    }

    @Scheduled(cron = "0 2 19 * * ?")
    public void getDayTBMDataSaveCurrentConstruction() {

        ScheduleTbmSegment scheduleTbmSegment = new ScheduleTbmSegment();
        scheduleTbmSegment.setCreateType(2);

        //查询时间 昨天19点-今日19点数据
        Date endDate = new Date();
        //昨天7点
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterdayStartDate = calendar.getTime();
        //前天7点
        calendar.add(Calendar.DATE, -2);
        Date beYesterdayStartDate = calendar.getTime();

        QueryWrapper<TbmWorkProgressLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("pull_time", yesterdayStartDate, endDate);
        queryWrapper.orderByDesc("pull_time");
        queryWrapper.last("limit 1");
        TbmWorkProgressLog tbmWorkProgressLog = tbmWorkProgressLogMapper.selectOne(queryWrapper);

        QueryWrapper<TbmWorkProgressLog> yesterdayQueryWrapper = new QueryWrapper<>();
        yesterdayQueryWrapper.between("pull_time", beYesterdayStartDate, yesterdayStartDate);
        yesterdayQueryWrapper.orderByDesc("pull_time");
        yesterdayQueryWrapper.last("limit 1");
        TbmWorkProgressLog yesterdayLogs = tbmWorkProgressLogMapper.selectOne(yesterdayQueryWrapper);

        //数据校验
        if (yesterdayLogs == null || tbmWorkProgressLog == null) {
            scheduleTbmSegment.setTbmRealityPrice(BigDecimal.ZERO);
            scheduleTbmSegment.setScheduleRealityPrice(BigDecimal.ZERO);
        } else {
            //计算差值
            BigDecimal diffDtlc = tbmWorkProgressLog.getDtlc().subtract(yesterdayLogs.getDtlc());
            Integer diffHpjsq = tbmWorkProgressLog.getHpjsq() - yesterdayLogs.getHpjsq();
            scheduleTbmSegment.setTbmRealityPrice(diffDtlc);
            scheduleTbmSegment.setScheduleRealityPrice(BigDecimal.valueOf(diffHpjsq));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        scheduleTbmSegment.setDayBase(simpleDateFormat.format(new Date()));

        scheduleTbmSegment.setCreatedBy("admin");
        scheduleTbmSegment.setCreatedDate(new Date());
        scheduleTbmSegment.setYn(YnEnum.Y.getCode());

        //写入日报
        this.save(scheduleTbmSegment);

    }

}
