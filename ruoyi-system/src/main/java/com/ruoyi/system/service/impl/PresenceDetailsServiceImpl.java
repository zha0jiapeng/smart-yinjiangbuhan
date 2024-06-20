package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.HiddenDangerStatus;
import com.ruoyi.common.enums.PresenceDetailsTypeEnum;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.entity.SysPersonnel;
import com.ruoyi.system.mapper.IotStaffAttendanceMapper;
import com.ruoyi.system.mapper.SysPersonnelMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PresenceDetailsServiceImpl {
    @Resource
    private SysPersonnelMapper sysPersonnelMapper;
    @Resource
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;

    public PresenceDetails countPresenceDetails(String dayBegin, String dayEnd) {
        PresenceDetails presenceDetails = new PresenceDetails();
        List<PresenceDetails.PresenceFrom> presenceFromList = new ArrayList<>();
        List<SysPersonnel> sysPersonnelList = sysPersonnelMapper.queryAllPersonnel(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(sysPersonnelList)) {
            Map<String, List<SysPersonnel>> listMap = sysPersonnelList.stream().collect(Collectors.groupingBy(SysPersonnel::getUserName));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<SysPersonnel> collect = v.stream().sorted(Comparator.comparing(SysPersonnel::getApproachTime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect) && collect.size() % 2 != 0) {
                        SysPersonnel sysPersonnel = collect.stream().findFirst().orElse(new SysPersonnel());
                        PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                        presenceFrom.setType(PresenceDetailsTypeEnum.TEMPORARY.getCode());
                        presenceFrom.setName(sysPersonnel.getUserName());
                        presenceFrom.setSex(sysPersonnel.getSex());
                        presenceFrom.setPhone(sysPersonnel.getPhone());
                        presenceFrom.setLastInDate(sysPersonnel.getApproachTime());
                        presenceFrom.setMatter(sysPersonnel.getMatter());
                        presenceFrom.setCorporate(sysPersonnel.getCorporate());
                        presenceFromList.add(presenceFrom);
                    }
                });
            }
        }

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<IotStaffAttendance>> listMap = list.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<IotStaffAttendance> collect = v.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect)) {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        if (first.isPresent()) {
                            IotStaffAttendance iotStaffAttendance = first.get();
                            if (iotStaffAttendance.getWay().equals(HiddenDangerStatus.AUDIT.getCode().toString())) {
                                PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                                presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                                presenceFrom.setName(iotStaffAttendance.getName());
                                presenceFrom.setPhone(iotStaffAttendance.getPhone());
                                presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                                presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                                presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                                presenceFromList.add(presenceFrom);
                            }
                        }
                    }
                });
            }
        }

        presenceDetails.setCount(presenceFromList.size());
        presenceDetails.setPresenceFromList(presenceFromList.stream().sorted(Comparator.comparing(PresenceDetails.PresenceFrom::getLastInDate).reversed()).collect(Collectors.toList()));
        return presenceDetails;
    }

    public PresenceDetails countPresenceDetailsNo(String dayBegin, String dayEnd) {
        PresenceDetails presenceDetails = new PresenceDetails();
        List<PresenceDetails.PresenceFrom> presenceFromList = new ArrayList<>();
        List<SysPersonnel> sysPersonnelList = sysPersonnelMapper.queryAllPersonnel(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(sysPersonnelList)) {
            Map<String, List<SysPersonnel>> listMap = sysPersonnelList.stream().collect(Collectors.groupingBy(SysPersonnel::getUserName));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<SysPersonnel> collect = v.stream().sorted(Comparator.comparing(SysPersonnel::getApproachTime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect)) {
                        SysPersonnel sysPersonnel = collect.stream().findFirst().orElse(new SysPersonnel());
                        PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                        presenceFrom.setType(PresenceDetailsTypeEnum.TEMPORARY.getCode());
                        presenceFrom.setName(sysPersonnel.getUserName());
                        presenceFrom.setSex(sysPersonnel.getSex());
                        presenceFrom.setPhone(sysPersonnel.getPhone());
                        presenceFrom.setLastInDate(sysPersonnel.getApproachTime());
                        presenceFrom.setMatter(sysPersonnel.getMatter());
                        presenceFrom.setCorporate(sysPersonnel.getCorporate());
                        presenceFromList.add(presenceFrom);
                    }
                });
            }
        }

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<IotStaffAttendance>> listMap = list.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<IotStaffAttendance> collect = v.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect)) {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        IotStaffAttendance iotStaffAttendance = first.get();
                        PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                        presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                        presenceFrom.setName(iotStaffAttendance.getName());
                        presenceFrom.setPhone(iotStaffAttendance.getPhone());
                        presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                        presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                        presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                        presenceFrom.setCorporate(iotStaffAttendance.getCompany());
                        presenceFrom.setYearCase(iotStaffAttendance.getYearCase());
                        presenceFrom.setMonthCase(iotStaffAttendance.getMonthCase());
                        presenceFrom.setDayCase(iotStaffAttendance.getDayCase());
                        presenceFrom.setWeekCase(iotStaffAttendance.getWeekCase());
                        presenceFrom.setHourCase(iotStaffAttendance.getHourCase());
                        presenceFrom.setWorkConfigType(iotStaffAttendance.getPersonnelConfigType());
                        presenceFromList.add(presenceFrom);

                    }
                });
            }
        }

        presenceDetails.setCount(presenceFromList.size());
        presenceDetails.setPresenceFromList(presenceFromList.stream().sorted(Comparator.comparing(PresenceDetails.PresenceFrom::getLastInDate).reversed()).collect(Collectors.toList()));
        return presenceDetails;
    }

    public PresenceDetails countWorkerDetails(String dayBegin, String dayEnd, Boolean flag) {
        PresenceDetails presenceDetails = new PresenceDetails();
        List<PresenceDetails.PresenceFrom> presenceFromList = new ArrayList<>();

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<IotStaffAttendance>> listMap = list.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<IotStaffAttendance> collect = v.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect) && flag) {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        if (first.isPresent()) {
                            IotStaffAttendance iotStaffAttendance = first.get();
                            if (iotStaffAttendance.getWay().equals(HiddenDangerStatus.AUDIT.getCode().toString())) {
                                PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                                presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                                presenceFrom.setName(iotStaffAttendance.getName());
                                presenceFrom.setPhone(iotStaffAttendance.getPhone());
                                presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                                presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                                presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                                presenceFrom.setCorporate(iotStaffAttendance.getCompany());

                                presenceFrom.setYearCase(iotStaffAttendance.getYearCase());
                                presenceFrom.setMonthCase(iotStaffAttendance.getMonthCase());
                                presenceFrom.setDayCase(iotStaffAttendance.getDayCase());
                                presenceFrom.setWeekCase(iotStaffAttendance.getWeekCase());
                                presenceFrom.setHourCase(iotStaffAttendance.getHourCase());
                                presenceFromList.add(presenceFrom);
                            }
                        }
                    } else {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        if (first.isPresent()) {
                            IotStaffAttendance iotStaffAttendance = first.get();
                            if (null != iotStaffAttendance.getWay() && iotStaffAttendance.getWay().equals(HiddenDangerStatus.AUDIT.getCode().toString())) {
                                PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                                presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                                presenceFrom.setName(iotStaffAttendance.getName());
                                presenceFrom.setPhone(iotStaffAttendance.getPhone());
                                presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                                presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                                presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                                presenceFrom.setCorporate(iotStaffAttendance.getCompany());

                                presenceFrom.setYearCase(iotStaffAttendance.getYearCase());
                                presenceFrom.setMonthCase(iotStaffAttendance.getMonthCase());
                                presenceFrom.setDayCase(iotStaffAttendance.getDayCase());
                                presenceFrom.setWeekCase(iotStaffAttendance.getWeekCase());
                                presenceFrom.setHourCase(iotStaffAttendance.getHourCase());
                                presenceFrom.setWorkConfigType(iotStaffAttendance.getPersonnelConfigType());
                                presenceFromList.add(presenceFrom);
                            }
                        }
                    }
                });
            }
        }

        presenceDetails.setCount(presenceFromList.size());
        presenceDetails.setPresenceFromList(presenceFromList);
        return presenceDetails;
    }

    public PresenceDetails countWorkerDetailsBase(String dayBegin, String dayEnd, Boolean flag) {
        PresenceDetails presenceDetails = new PresenceDetails();
        List<PresenceDetails.PresenceFrom> presenceFromList = new ArrayList<>();

        List<SysPersonnel> sysPersonnelList = sysPersonnelMapper.queryAllPersonnel(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(sysPersonnelList)) {
            Map<String, List<SysPersonnel>> listMap = sysPersonnelList.stream().collect(Collectors.groupingBy(SysPersonnel::getUserName));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<SysPersonnel> collect = v.stream().sorted(Comparator.comparing(SysPersonnel::getApproachTime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect) && collect.size() % 2 != 0 && flag) {
                        SysPersonnel sysPersonnel = collect.stream().findFirst().orElse(new SysPersonnel());
                        PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                        presenceFrom.setType(PresenceDetailsTypeEnum.TEMPORARY.getCode());
                        presenceFrom.setName(sysPersonnel.getUserName());
                        presenceFrom.setSex(sysPersonnel.getSex());
                        presenceFrom.setPhone(sysPersonnel.getPhone());
                        presenceFrom.setLastInDate(sysPersonnel.getApproachTime());
                        presenceFrom.setMatter(sysPersonnel.getMatter());
                        presenceFrom.setCorporate(sysPersonnel.getCorporate());
                        presenceFromList.add(presenceFrom);
                    } else {
                        SysPersonnel sysPersonnel = collect.stream().findFirst().orElse(new SysPersonnel());
                        PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                        presenceFrom.setType(PresenceDetailsTypeEnum.TEMPORARY.getCode());
                        presenceFrom.setName(sysPersonnel.getUserName());
                        presenceFrom.setSex(sysPersonnel.getSex());
                        presenceFrom.setPhone(sysPersonnel.getPhone());
                        presenceFrom.setLastInDate(sysPersonnel.getApproachTime());
                        presenceFrom.setMatter(sysPersonnel.getMatter());
                        presenceFrom.setCorporate(sysPersonnel.getCorporate());
                        presenceFromList.add(presenceFrom);
                    }
                });
            }
        }

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<IotStaffAttendance>> listMap = list.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<IotStaffAttendance> collect = v.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect) && flag) {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        if (first.isPresent()) {
                            IotStaffAttendance iotStaffAttendance = first.get();
                            if (iotStaffAttendance.getWay().equals(HiddenDangerStatus.AUDIT.getCode().toString())) {
                                PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                                presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                                presenceFrom.setName(iotStaffAttendance.getName());
                                presenceFrom.setPhone(iotStaffAttendance.getPhone());
                                presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                                presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                                presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                                presenceFrom.setCorporate(iotStaffAttendance.getCompany());

                                presenceFrom.setYearCase(iotStaffAttendance.getYearCase());
                                presenceFrom.setMonthCase(iotStaffAttendance.getMonthCase());
                                presenceFrom.setDayCase(iotStaffAttendance.getDayCase());
                                presenceFrom.setWeekCase(iotStaffAttendance.getWeekCase());
                                presenceFrom.setHourCase(iotStaffAttendance.getHourCase());
                                presenceFrom.setWorkConfigType(iotStaffAttendance.getPersonnelConfigType());
                                presenceFromList.add(presenceFrom);
                            }
                        }
                    } else {
                        Optional<IotStaffAttendance> first = collect.stream().findFirst();
                        if (first.isPresent()) {
                            IotStaffAttendance iotStaffAttendance = first.get();
                            if (iotStaffAttendance.getWay().equals(HiddenDangerStatus.AUDIT.getCode().toString())) {
                                PresenceDetails.PresenceFrom presenceFrom = new PresenceDetails.PresenceFrom();
                                presenceFrom.setType(PresenceDetailsTypeEnum.SCENE.getCode());
                                presenceFrom.setName(iotStaffAttendance.getName());
                                presenceFrom.setPhone(iotStaffAttendance.getPhone());
                                presenceFrom.setLastInDate(iotStaffAttendance.getDatetime());
                                presenceFrom.setGroupName(iotStaffAttendance.getGroupName());
                                presenceFrom.setWorkType(iotStaffAttendance.getWorkType());
                                presenceFrom.setCorporate(iotStaffAttendance.getCompany());

                                presenceFrom.setYearCase(iotStaffAttendance.getYearCase());
                                presenceFrom.setMonthCase(iotStaffAttendance.getMonthCase());
                                presenceFrom.setDayCase(iotStaffAttendance.getDayCase());
                                presenceFrom.setWeekCase(iotStaffAttendance.getWeekCase());
                                presenceFrom.setHourCase(iotStaffAttendance.getHourCase());
                                presenceFrom.setWorkConfigType(iotStaffAttendance.getPersonnelConfigType());
                                presenceFromList.add(presenceFrom);
                            }
                        }
                    }
                });
            }
        }

        presenceDetails.setCount(presenceFromList.size());
        presenceDetails.setPresenceFromList(presenceFromList);
        return presenceDetails;
    }

}
