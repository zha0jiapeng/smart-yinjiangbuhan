package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import com.ruoyi.system.domain.WorkDateStorage;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.mapper.SysWorkPeopleInoutLogMapper;
import com.ruoyi.system.service.IotStaffAttendanceService;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.system.service.WorkDateStorageService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;

/**
 * @author hu_p
 * @date 2024/6/23
 */
@RestController
@RequestMapping("inoutlog")
@AllArgsConstructor
public class SysWorkPeopleInoutLogController extends BaseController {

    private final SysWorkPeopleInoutLogMapper sysWorkPeopleInoutLogMapper;

    private final SysWorkPeopleService sysWorkPeopleService;

    private final WorkDateStorageService workDateStorageService;

    private final IotStaffAttendanceService iotStaffAttendanceService;

    @PostMapping("sync")
    public AjaxResult sync(@RequestBody List<SysWorkPeopleInoutLog> logs) {
        if (CollectionUtils.isEmpty(logs)) {
            return AjaxResult.error("数据为空");
        }
        AtomicInteger count = new AtomicInteger(0);
        // 插入日志底表
        logs.stream()
                .filter(log -> ObjectUtils.allNotNull(log.getInoutId(), log.getSn(), log.getIdCard(), log.getMode(), log.getLogTime()))
                .filter(log -> {
                    final QueryWrapper<SysWorkPeopleInoutLog> query = new QueryWrapper<>();
                    query.eq("inout_id", log.getInoutId());
                    // 过滤已存在的记录
                    return sysWorkPeopleInoutLogMapper.selectCount(query) == 0;
                }).peek(log -> {
                    // 填充人员信息
                    final QueryWrapper<SysWorkPeople> query = new QueryWrapper<>();
                    query.eq("id_card", log.getIdCard());
                    Optional.ofNullable(
                                    sysWorkPeopleService.getOne(query, false)).map(SysWorkPeople::getId)
                            .ifPresent(log::setSysWorkPeopleId);
                }).filter(log ->
                        // 过滤系统不存在的用户
                        Objects.nonNull(log.getSysWorkPeopleId()))
                .peek(log -> {
                    log.setCreatedBy("system");
                    log.setCreatedDate(new Date());
                    log.setYn(YnEnum.Y.getCode());
                })
                .peek(sysWorkPeopleInoutLogMapper::insert).map(log -> {
                    IotStaffAttendance iotStaffAttendance = new IotStaffAttendance();
                    iotStaffAttendance.setName(log.getName());
                    iotStaffAttendance.setId(log.getSn());
                    iotStaffAttendance.setPhone(log.getPhone());
                    iotStaffAttendance.setEmployeeId(String.valueOf(log.getSysWorkPeopleId()));
                    iotStaffAttendance.setWayBase(log.getMode() == 1 ? 1 : 2);
                    iotStaffAttendance.setDatetime(log.getLogTime());
                    count.getAndIncrement();
                    return iotStaffAttendance;
                }).forEach(iotStaffAttendanceService::save);
        return AjaxResult.success(count.get());
    }

    /**
     * 同步考勤表
     * 暂时不用
     */
    private void syncWorkDateStorage(SysWorkPeopleInoutLog log) {
        final WorkDateStorage workDateStorage = new WorkDateStorage();
        workDateStorage.setCreatedDate(currentDate());
        workDateStorage.setPhone(log.getPhone());
        workDateStorage.setName(log.getName());
        workDateStorage.setCreatedBy("system");
        final WorkDateStorage storage = workDateStorageService.getOne(new QueryWrapper<>(workDateStorage), false);
        // 为空 说明当天没有进入记录 只处理 进洞日志
        if (null == storage) {
            if (1 == log.getMode()) {
                workDateStorage.setStartDate(DateUtils.parseDate(log.getLogTime()));
                workDateStorage.setYn(YnEnum.Y.getCode());
                workDateStorageService.save(workDateStorage);
            }
        } else if (0 == log.getMode()) {
            // 已有记录 只处理出洞日志 todo 跨天的话 可能会有问题
            storage.setEndDate(DateUtils.parseDate(log.getLogTime()));
            workDateStorageService.updateById(storage);
        }
    }

    Date currentDate() {
        LocalDateTime localDateTime = LocalDateTime.now().withMinute(59).withHour(23).withSecond(0).withNano(0);
        return DateUtils.toDate(localDateTime);
    }

    @GetMapping("list")
    public TableDataInfo list(SysWorkPeopleInoutLog log) {
        startPage();
        final QueryWrapper<SysWorkPeopleInoutLog> query = new QueryWrapper<>(log);
        return getDataTable(sysWorkPeopleInoutLogMapper.selectList(query));
    }


}
