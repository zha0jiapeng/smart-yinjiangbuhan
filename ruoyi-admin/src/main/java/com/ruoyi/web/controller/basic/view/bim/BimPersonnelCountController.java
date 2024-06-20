package com.ruoyi.web.controller.basic.view.bim;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.enums.BasicDataValueConfigEnum;
import com.ruoyi.common.enums.PersonnelTypeEnum;
import com.ruoyi.common.enums.PresenceDetailsTypeEnum;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.PersonnelTypeCount;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.domain.bim.BimPersonnelCountDomain;
import com.ruoyi.system.entity.BasicDataValueConfig;
import com.ruoyi.system.mapper.IotStaffAttendanceMapper;
import com.ruoyi.system.service.BasicDataValueConfigService;
import com.ruoyi.system.service.impl.PersonnelTypeCountServiceImpl;
import com.ruoyi.system.service.impl.PresenceDetailsServiceImpl;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("bim/personnel")
public class BimPersonnelCountController {

    @Value("#{${bsl.v2.company.name}}")
    private Map<String, String> companyName;

    @Autowired
    private BasicDataValueConfigService basicDataValueConfigService;

    @Resource
    private PresenceDetailsServiceImpl presenceDetailsService;

    @Resource
    private PersonnelTypeCountServiceImpl personnelTypeCountService;

    @Autowired
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;

    @PostMapping("/count")
    public Result<?> count(@RequestBody SysWorkPeople sysWorkPeople) {
        BimPersonnelCountDomain bimPersonnelCountDomain = new BimPersonnelCountDomain();

        boolean flag = false;
        if (null != sysWorkPeople && StringUtils.isNotEmpty(sysWorkPeople.getGroupsName())) {
            flag = true;
        }

        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayBegin = dateformat.format(DateToUtils.getDayBegin());
        String dayEnd = dateformat.format(DateToUtils.getDayEnd());

        //人员概览
        BimPersonnelCountDomain.PersonnelStatistics personnelStatistics = new BimPersonnelCountDomain.PersonnelStatistics();
        PersonnelTypeCount personnelTypeCount = personnelTypeCountService.personnelTypeCount();
        BeanUtils.copyProperties(personnelTypeCount, personnelStatistics);
        bimPersonnelCountDomain.setPersonnelStatistics(personnelStatistics);

        //施工人力统计
        List<BasicDataValueConfig> basicDataValueConfigs = basicDataValueConfigService.list();

        Map<String, BasicDataValueConfig> dataValueConfigMap = basicDataValueConfigs.stream()
                .collect(Collectors.toMap(BasicDataValueConfig::getBasicKey, el -> el, (e1, e2) -> e1));

        BimPersonnelCountDomain.ConstructionHuman constructionHuman = new BimPersonnelCountDomain.ConstructionHuman();

        List<String> subscript = new ArrayList<>();
        List<String> plan = null;
        List<String> reality = new ArrayList<>();
        BasicDataValueConfig INIT_PLAN_CONSTRUCTION_HUMAN = dataValueConfigMap.get(BasicDataValueConfigEnum.init_plan_construction_human.getCode());
        plan = JSON.parseArray(INIT_PLAN_CONSTRUCTION_HUMAN.getBasicValue(), String.class);

        BasicDataValueConfig INIT_PLAN_CONSTRUCTION_HUMAN_WEEK = dataValueConfigMap.get(BasicDataValueConfigEnum.init_plan_construction_human_week.getCode());
        subscript = JSON.parseArray(INIT_PLAN_CONSTRUCTION_HUMAN_WEEK.getBasicValue(), String.class);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        //设置为第一天
        gcLast.set(Calendar.DAY_OF_MONTH, 1);

        //获取Calendar
        Calendar calendarOne = Calendar.getInstance();
        //设置日期为本月最大日期
        calendarOne.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(sf.format(gcLast.getTime()) + " 00:00:00",
                sf.format(calendar.getTime()) + " 23:59:59");

        if (CollectionUtils.isEmpty(list)) {
            constructionHuman.setReality(reality);
        } else {
            Map<String, List<IotStaffAttendance>> collect = list.stream().collect(Collectors.groupingBy(x -> x.getMonthCase() + x.getWeekCase()));
            Map<String, List<IotStaffAttendance>> sortMapDesc = new HashMap<>();
            collect.entrySet().stream()
                    .sorted(Map.Entry.<String, List<IotStaffAttendance>>comparingByKey())
                    .forEachOrdered(e -> sortMapDesc.put(e.getKey(), e.getValue()));

            sortMapDesc.forEach((k, v) -> {
                v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(IotStaffAttendance::getPhone))), ArrayList::new));
                reality.add(String.valueOf(v.size()));
            });
        }

        constructionHuman.setSubscript(subscript);
        constructionHuman.setPlan(plan);
        constructionHuman.setReality(reality);
        bimPersonnelCountDomain.setConstructionHuman(constructionHuman);

        //今日作业人员趋势
        BimPersonnelCountDomain.TrendHour trendHour = new BimPersonnelCountDomain.TrendHour();
        PresenceDetails presenceDetailsBase = presenceDetailsService.countWorkerDetailsBase(dayBegin, dayEnd, false);
        if (presenceDetailsBase == null || CollectionUtils.isEmpty(presenceDetailsBase.getPresenceFromList())) {
            trendHour.setManage(new ArrayList<>());
            trendHour.setSupervisor(new ArrayList<>());
            trendHour.setConstruction(new ArrayList<>());
            trendHour.setVisitor(new ArrayList<>());
            bimPersonnelCountDomain.setTrendHour(trendHour);
        } else {
            List<String> manage = new ArrayList<>();
            List<String> supervisor = new ArrayList<>();
            List<String> construction = new ArrayList<>();
            List<String> visitor = new ArrayList<>();

            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 0, 0, 5);
            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 5, 4, 9);
            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 9, 8, 13);
            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 13, 12, 17);
            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 17, 16, 21);
            builderMateTimePersonnelMessage(presenceDetailsBase, manage, supervisor, construction, visitor, 21, 20, 24);

            trendHour.setManage(manage);
            trendHour.setSupervisor(supervisor);
            trendHour.setConstruction(construction);
            trendHour.setVisitor(visitor);
        }

        bimPersonnelCountDomain.setTrendHour(trendHour);

        //按单位分析
        BimPersonnelCountDomain.SubpackageUnit subpackageUnit = new BimPersonnelCountDomain.SubpackageUnit();

        //按工种统计
        BimPersonnelCountDomain.WorkType workType = new BimPersonnelCountDomain.WorkType();

        //按单位 || 工种 统计（实时数据）
        PresenceDetails presenceDetails = null;
        presenceDetails = presenceDetailsService.countWorkerDetails(dayBegin, dayEnd, true);

        if (presenceDetails == null || CollectionUtils.isEmpty(presenceDetails.getPresenceFromList())) {
            subpackageUnit.setUnit(new ArrayList<>());
            subpackageUnit.setNumber(new ArrayList<>());
            workType.setType(new ArrayList<>());
            workType.setNumber(new ArrayList<>());
        } else {
            Map<String, List<PresenceDetails.PresenceFrom>> collect = presenceDetails.getPresenceFromList().stream()
                    .filter(val -> val.getCorporate() != null)
                    .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getCorporate));

            Map<String, List<PresenceDetails.PresenceFrom>> workTypeMap = null;
            if (flag) {
                workTypeMap = presenceDetails.getPresenceFromList().stream()
                        .filter(val -> val.getGroupName() != null)
                        .filter(var -> var.getGroupName().equals(sysWorkPeople.getGroupsName()))
                        .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getGroupName));
            } else {
                workTypeMap = presenceDetails.getPresenceFromList().stream()
                        .filter(val -> val.getGroupName() != null)
                        .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getGroupName));
            }

            List<String> workTypeList = new ArrayList<>();
            List<String> workNumberList = new ArrayList<>();

            List<String> subpackageUnitTypeList = new ArrayList<>();
            List<String> subpackageUnitNumberList = new ArrayList<>();

            collect.forEach((k, v) -> {
                subpackageUnitTypeList.add(companyName.get(k));
                subpackageUnitNumberList.add(String.valueOf(v.size()));
            });

            workTypeMap.forEach((k, v) -> {
                workTypeList.add(k);
                workNumberList.add(String.valueOf(v.size()));
            });

            workType.setType(workTypeList);
            workType.setNumber(workNumberList);

            subpackageUnit.setUnit(subpackageUnitTypeList);
            subpackageUnit.setNumber(subpackageUnitNumberList);
        }

        //按单位统计 || 工种（今日数据）
        presenceDetails = presenceDetailsService.countPresenceDetailsNo(dayBegin, dayEnd);
        if (presenceDetails == null || CollectionUtils.isEmpty(presenceDetails.getPresenceFromList())) {
            subpackageUnit.setUnitCase(new ArrayList<>());
            subpackageUnit.setNumberCase(new ArrayList<>());
        } else {
            Map<String, List<PresenceDetails.PresenceFrom>> collect = presenceDetails.getPresenceFromList().stream()
                    .filter(val -> val.getCorporate() != null)
                    .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getCorporate));

            Map<String, List<PresenceDetails.PresenceFrom>> workTypeMap = null;
            if (flag) {
                workTypeMap = presenceDetails.getPresenceFromList().stream()
                        .filter(val -> val.getGroupName() != null)
                        .filter(var -> var.getGroupName().equals(sysWorkPeople.getGroupsName()))
                        .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getGroupName));
            } else {
                workTypeMap = presenceDetails.getPresenceFromList().stream()
                        .filter(val -> val.getGroupName() != null)
                        .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getGroupName));
            }

            List<String> workTypeList = new ArrayList<>();
            List<String> workNumberList = new ArrayList<>();

            List<String> subpackageUnitTypeList = new ArrayList<>();
            List<String> subpackageUnitNumberList = new ArrayList<>();

            collect.forEach((k, v) -> {
                v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<PresenceDetails.PresenceFrom>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));
                subpackageUnitTypeList.add(companyName.get(k));
                subpackageUnitNumberList.add(String.valueOf(v.size()));
            });

            workTypeMap.forEach((k, v) -> {
                v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<PresenceDetails.PresenceFrom>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));
                workTypeList.add(k);
                workNumberList.add(String.valueOf(v.size()));
            });

            workType.setTypeCase(workTypeList);
            workType.setNumberCase(workNumberList);

            subpackageUnit.setUnitCase(subpackageUnitTypeList);
            subpackageUnit.setNumberCase(subpackageUnitNumberList);
        }

        bimPersonnelCountDomain.setSubpackageUnit(subpackageUnit);
        bimPersonnelCountDomain.setWorkType(workType);

        /*
            近七天作业人员作业人员分析
         */
        BimPersonnelCountDomain.SevenAnalysis sevenAnalysis = new BimPersonnelCountDomain.SevenAnalysis();
        List<String> sevenAnalysisList = new ArrayList<>();

        DateFormat dateformatBase = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间 2023/10/07
        String dayBeginCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(1));
        //当前时间倒推7天
        String dayEndCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-6));

        List<IotStaffAttendance> iotStaffAttendances = iotStaffAttendanceMapper.queryAll(dayEndCase, dayBeginCase);
        if (CollectionUtils.isEmpty(iotStaffAttendances)) {
            sevenAnalysis.setNumber(new ArrayList<>());
        } else {
            List<String> stringList = mateTimeSlot();

            List<String> mateDateList = new ArrayList<>();
            for (IotStaffAttendance var : iotStaffAttendances) {
                mateDateList.add(var.getYearCase() + var.getMonthCase() + var.getDayCase());
            }

            for (String mateDate : stringList) {
                if (mateDateList.contains(mateDate)) {

                    Map<String, List<IotStaffAttendance>> collect = iotStaffAttendances.stream()
                            .filter(val -> StringUtils.isNotEmpty(val.getDayCase()))
                            .collect(Collectors.groupingBy(IotStaffAttendance::getDayCase));

                    Map<String, List<IotStaffAttendance>> sortMapDesc = new HashMap<>();
                    collect.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .forEachOrdered(e -> sortMapDesc.put(e.getKey(), e.getValue()));

                    sortMapDesc.forEach((k, v) -> {
                        v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(IotStaffAttendance::getPhone))), ArrayList::new));

                        sevenAnalysisList.add(String.valueOf(v.size()));
                    });
                } else {
                    sevenAnalysisList.add("0");
                }
            }
        }

        sevenAnalysis.setNumber(sevenAnalysisList);
        sevenAnalysis.setSubscript(countTimeSlot());
        bimPersonnelCountDomain.setSevenAnalysis(sevenAnalysis);
        return Result.OK(bimPersonnelCountDomain);
    }

    private List<String> countTimeSlot() {
        List<String> daysResponse = new ArrayList<>();

        DateFormat dateformatBase = new SimpleDateFormat("yyyy/MM/dd");

        String dayBeginCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(0));

        String dayEndCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-6));

        List<String> days = DateUtils.getDays(dayEndCase, dayBeginCase, dateformatBase);

        for (String var : days) {
            daysResponse.add(var.substring(2));
        }

        daysResponse.set(daysResponse.size() - 1, "今日");

        return daysResponse;
    }

    private List<String> mateTimeSlot() {

        DateFormat dateformatBase = new SimpleDateFormat("yyyyMMdd");

        //当前时间 2023/10/07
        String dayBeginCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(0));

        //当前时间倒推7天
        String dayEndCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-6));

        return DateUtils.getDays(dayEndCase, dayBeginCase, dateformatBase);

    }

    private void builderMateTimePersonnelMessage(PresenceDetails presenceDetailsBase,
                                                 List<String> manage,
                                                 List<String> supervisor,
                                                 List<String> construction,
                                                 List<String> visitor,
                                                 Integer var1,
                                                 Integer var2,
                                                 Integer var3) {
        List<PresenceDetails.PresenceFrom> collect = null;

        collect = presenceDetailsBase.getPresenceFromList().stream().filter(
                var -> var.getHourCase() != null && (Integer.parseInt(var.getHourCase()) > var1 || Integer.parseInt(var.getHourCase()) == var2)
                        && Integer.parseInt(var.getHourCase()) < var3 && var.getType().equals(PresenceDetailsTypeEnum.SCENE.getCode())
        ).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));

        manage.add(String.valueOf((int) collect.stream().filter(val -> val.getWorkConfigType() != null
                && val.getWorkConfigType().equals(PersonnelTypeEnum.MANAGE.getCode())).count()));

        supervisor.add(String.valueOf((int) collect.stream().filter(val -> val.getWorkConfigType() != null
                && val.getWorkConfigType().equals(PersonnelTypeEnum.SUPERVISOR.getCode())).count()));

        construction.add(String.valueOf((int) collect.stream().filter(val -> val.getWorkConfigType() != null
                && val.getWorkConfigType().equals(PersonnelTypeEnum.TEAMS.getCode())).count()));

        collect = presenceDetailsBase.getPresenceFromList().stream().filter(
                var -> var.getHourCase() != null && (Integer.parseInt(var.getHourCase()) > var1 || Integer.parseInt(var.getHourCase()) == var2) &&
                        Integer.parseInt(var.getHourCase()) < var3 && var.getType().equals(PresenceDetailsTypeEnum.TEMPORARY.getCode())
        ).collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));

        visitor.add(String.valueOf(collect.size()));
    }

}
