package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.PersonnelTypeEnum;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.PersonnelTypeCount;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonnelTypeCountServiceImpl {

    @Resource
    private SysWorkPeopleService sysWorkPeopleService;
    @Resource
    private PresenceDetailsServiceImpl presenceDetailsService;

    public PersonnelTypeCount personnelTypeCount() {
        PersonnelTypeCount personnelTypeCount = new PersonnelTypeCount();

        //顺序调一下：业主代建、监理人员、管理人员、班组人员、临时访客
        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<SysWorkPeople> sysWorkPeopleList = sysWorkPeopleService.list(queryWrapper);
        if (CollectionUtils.isEmpty(sysWorkPeopleList)) {
            return personnelTypeCount;
        }

        Map<Integer, List<SysWorkPeople>> listMap = sysWorkPeopleList.stream().collect(Collectors.groupingBy(SysWorkPeople::getPersonnelConfigType));
        if (MapUtils.isEmpty(listMap)) {
            return personnelTypeCount;
        }

        int sum = 0;

        //业主
        List<SysWorkPeople> ownerCount = listMap.get(PersonnelTypeEnum.OWNER.getCode());
        sum = sum + ownerCount.size();
        personnelTypeCount.setOwner(Integer.toString(ownerCount.size()));

        //监理
        List<SysWorkPeople> supervisorCount = listMap.get(PersonnelTypeEnum.SUPERVISOR.getCode());
        sum = sum + supervisorCount.size();
        personnelTypeCount.setSupervisor(Integer.toString(supervisorCount.size()));

        //管理
        List<SysWorkPeople> managementCount = listMap.get(PersonnelTypeEnum.MANAGE.getCode());
        sum = sum + managementCount.size();
        personnelTypeCount.setManagement(Integer.toString(managementCount.size()));

        //班组
        List<SysWorkPeople> constructionCount = listMap.get(PersonnelTypeEnum.TEAMS.getCode());
        sum = sum + constructionCount.size();
        personnelTypeCount.setConstructionPersonnel(Integer.toString(constructionCount.size()));

        //访客旧版本处理逻辑
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayBegin = dateformat.format(DateToUtils.getDayBegin());
        String dayEnd = dateformat.format(DateToUtils.getDayEnd());

        PresenceDetails presenceDetails = presenceDetailsService.countWorkerDetailsBase(dayBegin, dayEnd, false);
        if (null != presenceDetails && !CollectionUtils.isEmpty(presenceDetails.getPresenceFromList())) {

            List<PresenceDetails.PresenceFrom> collect = presenceDetails.getPresenceFromList().stream().filter(
                    var -> var.getType().equals(YnEnum.Y.getCode())).collect(Collectors.toList());

            collect = collect.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                            new TreeSet<PresenceDetails.PresenceFrom>(Comparator.comparing(PresenceDetails.PresenceFrom::getPhone))), ArrayList::new));

            //访客
            personnelTypeCount.setVisitor(Integer.toString(collect.size()));

            //施工
            personnelTypeCount.setConstructors(Integer.toString(presenceDetails.getCount()));
        }

        //总数
        personnelTypeCount.setPresentPeople(Integer.toString(sum));

        return personnelTypeCount;
    }

}
