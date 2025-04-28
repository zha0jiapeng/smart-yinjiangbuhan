package com.ruoyi.web.controller.basic.view.bl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.service.impl.PresenceDetailsServiceImpl;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
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


    public static void main(String[] args) {
        long timestampSeconds = Instant.now().getEpochSecond();
        //常用接口
        String sid = "48c44c67aa9b4ee18cc0959509a6c27e";
        String sign = "comid=1041&compvtkey=f3ee1fd566764671838dbae83050450e&sid=" + sid + "&ts=" + timestampSeconds + "&key=f1cd9351930d4e589922edbcf3b09a7c";
        //登录接口
//        String sign = "alias=LJMG001&comid=1041&compvtkey=f3ee1fd566764671838dbae83050450e&password=21218cca77804d2ba1922c33e0151105&ts=" + timestampSeconds + "&key=f1cd9351930d4e589922edbcf3b09a7c";
        String encrypted = DigestUtils.md5Hex(sign);

        JSONObject json = new JSONObject();
        json.put("compvtkey", "f3ee1fd566764671838dbae83050450e");
        json.put("sign", encrypted);
        json.put("comid", "1041");
        json.put("sid", sid);
        json.put("ts", timestampSeconds);

        String jsonString = JSON.toJSONString(json);
        System.out.println(jsonString);
    }
}
