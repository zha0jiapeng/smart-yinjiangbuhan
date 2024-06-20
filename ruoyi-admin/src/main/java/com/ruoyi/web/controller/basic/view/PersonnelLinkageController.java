package com.ruoyi.web.controller.basic.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.PersonnelLinkage;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.service.IotStaffAttendanceService;
import com.ruoyi.system.service.PersonnelLinkageService;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("presence")
public class PersonnelLinkageController {
    @Resource
    private SysWorkPeopleService sysWorkPeopleService;
    @Resource
    private PersonnelLinkageService personnelLinkageService;
    @Autowired
    private IotStaffAttendanceService iotStaffAttendanceService;

    @PostMapping("linkage")
    public Result<?> linkage(@RequestBody PersonnelLinkage personnelLinkage) {
        //基础参数校验
        BaseVerifyUtil.verify(null == personnelLinkage.getPhone() && null == personnelLinkage.getId())
                .throwMessage(VerifyEnum.PHONE_NULL_OR_ID.getCode(), VerifyEnum.PHONE_NULL_OR_ID.getDesc());

        List<PersonnelLinkage> personnelLinkageList = new ArrayList<>();

        PersonnelLinkage personnelLinkageResp = new PersonnelLinkage();

        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(personnelLinkage.getPhone())) {
            queryWrapper.eq("phone", personnelLinkage.getPhone());
            queryWrapper.ge("yn", YnEnum.Y.getCode());
        }
        if (null != personnelLinkage.getId()) {
            queryWrapper.eq("id", personnelLinkage.getId());
        }
        SysWorkPeople one = sysWorkPeopleService.getOne(queryWrapper);
        if (null == one) {
            return Result.error("未查询到工人信息");
        }

        personnelLinkage.setPhone(one.getPhone());
        //工人名称
        personnelLinkageResp.setName(one.getName());
        //性别
        personnelLinkageResp.setSex(one.getSex() == 1 ? "男" : "女");
        //手机号
        personnelLinkageResp.setPhone(one.getPhone());
        //工种
        personnelLinkageResp.setTypeWork(one.getWorkType());
        //班组
        personnelLinkageResp.setGroups(one.getGroupsName());
        //公司
        personnelLinkageResp.setCompany(one.getCompany());

        LocalDate now = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 当天开始时间
        LocalDateTime todayStart = now.atStartOfDay();
        // 当天结束时间
        LocalDateTime todayEnd = LocalDateTime.of(now, LocalTime.MAX);

        QueryWrapper<IotStaffAttendance> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(personnelLinkage.getPhone())) {
            wrapper.eq("phone", personnelLinkage.getPhone());
        }
        wrapper.ge("datetime", pattern.format(todayStart))
                .le("datetime", pattern.format(todayEnd));
        wrapper.orderByDesc("datetime");

        List<IotStaffAttendance> list = iotStaffAttendanceService.list(wrapper);
        IotStaffAttendance iotStaffAttendance =
                list.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed())
                        .collect(Collectors.toList()).stream().findFirst().orElse(new IotStaffAttendance());

        //进场时间
        personnelLinkageResp.setInDate(iotStaffAttendance.getDatetime());

        //进场图片
        personnelLinkageResp.setImages(iotStaffAttendance.getImage());

        //考试信息
        List<PersonnelLinkage.Examination> examinations = personnelLinkageService.queryUserExams(personnelLinkage.getPhone());
        personnelLinkageResp.setExaminationList(examinations);

        int leg = 9;
        List<PersonnelLinkage.KaoQin> kaoQinList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list = list.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime).reversed())
                    .collect(Collectors.toList());

            for (IotStaffAttendance var : list) {
                PersonnelLinkage.KaoQin kaoQin = new PersonnelLinkage.KaoQin();
                kaoQin.setPhone(var.getPhone());
                kaoQin.setWay(var.getWay());
                kaoQin.setDateTime(var.getDatetime());
                kaoQinList.add(kaoQin);
                if (kaoQinList.size() == leg) {
                    break;
                }
            }
        }

        personnelLinkageResp.setKaoQinList(kaoQinList);

        personnelLinkageList.add(personnelLinkageResp);

        return Result.OK(personnelLinkageList);
    }

}
