package com.ruoyi.web.controller.basic.view.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.constant.IntegerConstant;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.service.CurrentConstructionNewService;
import com.ruoyi.system.service.ScheduleTbmSegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("schedule/Tbm/Segment")
public class ScheduleTbmSegmentController extends BaseController {
    @Resource
    private ScheduleTbmSegmentService scheduleTbmSegmentService;
    @Autowired
    private CurrentConstructionNewService currentConstructionNewService;

    @GetMapping("/list/month")
    public TableDataInfo listMonth(ScheduleTbmSegment scheduleTbmSegment) {
        startPage();

        QueryWrapper<ScheduleTbmSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_type", YnEnum.Y.getCode());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ScheduleTbmSegment> listPage = scheduleTbmSegmentService.list(queryWrapper);

        return getDataTable(listPage);
    }

    @PostMapping("/save/month")
    public AjaxResult saveMonth(@RequestBody ScheduleTbmSegment scheduleTbmSegment) {

        //校验是否有重复数据
        QueryWrapper<ScheduleTbmSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_type", YnEnum.Y.getCode());
        queryWrapper.eq("year_base", scheduleTbmSegment.getYearBase());
        queryWrapper.eq("month_base", scheduleTbmSegment.getMonthBase());
        queryWrapper.eq("yn", YnEnum.Y.getCode());

        int count = scheduleTbmSegmentService.count(queryWrapper);
        if (count > IntegerConstant.ZERO) {
            return AjaxResult.error("当月份数据已经上报,不能重复上报");
        }
        scheduleTbmSegment.setCreateType(IntegerConstant.ONE);
        scheduleTbmSegment.setCreatedBy(getUsername());
        scheduleTbmSegment.setCreatedDate(new Date());
        scheduleTbmSegment.setYn(YnEnum.Y.getCode());

        scheduleTbmSegmentService.save(scheduleTbmSegment);

        return AjaxResult.success();
    }

    @PostMapping("/update/month")
    public AjaxResult updateMonth(@RequestBody ScheduleTbmSegment scheduleTbmSegment) {

        ScheduleTbmSegment scheduleTbmSegmentUpdate = new ScheduleTbmSegment();
        scheduleTbmSegmentUpdate.setId(scheduleTbmSegment.getId());
        scheduleTbmSegmentUpdate.setTbmPlanPrice(scheduleTbmSegment.getTbmPlanPrice());
        scheduleTbmSegmentUpdate.setSchedulePlanPrice(scheduleTbmSegment.getSchedulePlanPrice());
        scheduleTbmSegmentUpdate.setModifyBy(getUsername());
        scheduleTbmSegmentUpdate.setModifyDate(new Date());

        scheduleTbmSegmentService.updateById(scheduleTbmSegmentUpdate);

        return AjaxResult.success();
    }

    @GetMapping("/list/day")
    public TableDataInfo listDay(ScheduleTbmSegment scheduleTbmSegment) {
        startPage();

        QueryWrapper<ScheduleTbmSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_type", IntegerConstant.TWO);
        queryWrapper
                .ge("day_base", scheduleTbmSegment.getYearBase() + "-" + scheduleTbmSegment.getMonthBase() + "-01")
                .le("day_base", scheduleTbmSegment.getYearBase() + "-" + scheduleTbmSegment.getMonthBase() + "-31");
        queryWrapper.eq("yn", YnEnum.Y.getCode());

        List<ScheduleTbmSegment> listPage = scheduleTbmSegmentService.list(queryWrapper);
        return getDataTable(listPage);
    }

    @PostMapping("/save/day")
    public AjaxResult saveDay(@RequestBody ScheduleTbmSegment scheduleTbmSegment) {

        //校验是否有重复数据
        QueryWrapper<ScheduleTbmSegment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_type", IntegerConstant.TWO);
        queryWrapper.eq("day_base", scheduleTbmSegment.getDayBase());
        queryWrapper.eq("yn", YnEnum.Y.getCode());

        int count = scheduleTbmSegmentService.count(queryWrapper);
        if (count > 0) {
            return AjaxResult.error("当日份数据已经上报,不能重复上报");
        }

        //TODO 校验前一天有没有录入

        scheduleTbmSegment.setYearBase(scheduleTbmSegment.getYearBase());
        scheduleTbmSegment.setCreateType(IntegerConstant.TWO);
        scheduleTbmSegment.setCreatedBy(getUsername());
        scheduleTbmSegment.setCreatedDate(new Date());
        scheduleTbmSegment.setYn(YnEnum.Y.getCode());

        scheduleTbmSegmentService.save(scheduleTbmSegment);

        currentConstructionNewService.calculate(scheduleTbmSegment);

        return AjaxResult.success();
    }

    @PostMapping("/update/day")
    public AjaxResult updateDay(@RequestBody ScheduleTbmSegment scheduleTbmSegment) {

        ScheduleTbmSegment scheduleTbmSegmentUpdate = new ScheduleTbmSegment();
        scheduleTbmSegmentUpdate.setId(scheduleTbmSegment.getId());
        scheduleTbmSegmentUpdate.setTbmRealityPrice(scheduleTbmSegment.getTbmRealityPrice());
        scheduleTbmSegmentUpdate.setScheduleRealityPrice(scheduleTbmSegment.getScheduleRealityPrice());
        scheduleTbmSegmentUpdate.setFileName(scheduleTbmSegment.getFileName());
        scheduleTbmSegmentUpdate.setFileUrl(scheduleTbmSegment.getFileUrl());
        scheduleTbmSegmentUpdate.setModifyBy(getUsername());
        scheduleTbmSegmentUpdate.setModifyDate(new Date());

        scheduleTbmSegmentService.updateById(scheduleTbmSegmentUpdate);

        return AjaxResult.success();
    }
}
