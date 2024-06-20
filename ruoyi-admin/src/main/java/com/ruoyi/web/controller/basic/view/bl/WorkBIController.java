package com.ruoyi.web.controller.basic.view.bl;

import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.service.impl.PresenceDetailsServiceImpl;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("work/bi")
public class WorkBIController {


    @Resource
    private PresenceDetailsServiceImpl presenceDetailsService;

    @PostMapping("/top")
    public Result<?> top() {
        WorkBI workBI = new WorkBI();
        WorkBI.DayBase dayBase = new WorkBI.DayBase();
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();


        DateFormat dateformat = new SimpleDateFormat("MM");
        String format = dateformat.format(new Date());


        DateFormat dateformatBase = new SimpleDateFormat("yyyy-MM-dd");
        String dayBeginCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-1));
        String dayEndCase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-7));

        PresenceDetails presenceDetails = presenceDetailsService.countWorkerDetails(dayEndCase, dayBeginCase, false);
        if (presenceDetails == null || CollectionUtils.isEmpty(presenceDetails.getPresenceFromList())) {
            dayBase.setKey(new ArrayList<>());
            dayBase.setValue(new ArrayList<>());
        } else {
            Map<String, List<PresenceDetails.PresenceFrom>> collect = presenceDetails.getPresenceFromList().stream()
                    .filter(val -> StringUtils.isNotEmpty(val.getDayCase()))
                    .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getDayCase));

            Map<String, List<PresenceDetails.PresenceFrom>> sortMapDesc = new HashMap<>();
            collect.entrySet().stream()
                    .sorted(Map.Entry.<String, List<PresenceDetails.PresenceFrom>>comparingByKey())
                    .forEachOrdered(e -> sortMapDesc.put(e.getKey(), e.getValue()));

            sortMapDesc.forEach((k, v) -> {
                v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<PresenceDetails.PresenceFrom>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));

                keyList.add(format + "-" + k);
                valueList.add(String.valueOf(v.size()));
            });
        }

        dayBase.setKey(keyList);
        dayBase.setValue(valueList);
        workBI.setDayBase(dayBase);

        WorkBI.WeekBase weekBase = new WorkBI.WeekBase();
        List<String> key = new ArrayList<>();
        key.add("第一周");
        key.add("第二周");
        key.add("第三周");
        key.add("第四周");
        List<String> plan = new ArrayList<>();
        List<String> reality = new ArrayList<>();
        plan.add("0");
        plan.add("0");
        plan.add("0");
        plan.add("0");

        List<String> value = new ArrayList<>();
        String dayEndBase = dateformatBase.format(DateToUtils.getBeginDayOfYesterdayCase(-28));
        presenceDetails = presenceDetailsService.countWorkerDetails(dayEndBase, dayBeginCase, false);
        if (presenceDetails == null || CollectionUtils.isEmpty(presenceDetails.getPresenceFromList())) {
            weekBase.setValue(reality);
        } else {
            Map<String, List<PresenceDetails.PresenceFrom>> collect = presenceDetails.getPresenceFromList().stream()
                    .filter(val -> StringUtils.isNotEmpty(val.getWeekCase()))
                    .collect(Collectors.groupingBy(PresenceDetails.PresenceFrom::getWeekCase));
            Map<String, List<PresenceDetails.PresenceFrom>> sortMapDesc = new HashMap<>();

            collect.entrySet().stream()
                    .sorted(Map.Entry.<String, List<PresenceDetails.PresenceFrom>>comparingByKey())
                    .forEachOrdered(e -> sortMapDesc.put(e.getKey(), e.getValue()));

            sortMapDesc.forEach((k, v) -> {
                v = v.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<PresenceDetails.PresenceFrom>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));

                if (k.contains("01") || k.contains("02") || k.contains("03") || k.contains("04")) {
                    value.add(String.valueOf(v.size()));
                } else {
                    value.add("0");
                }

            });
        }


        weekBase.setKey(key);
        weekBase.setPlan(plan);
        weekBase.setValue(value);
        workBI.setWeekBase(weekBase);
        return Result.OK(workBI);
    }
}
