package com.ruoyi.web.controller.basic.view;

import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.system.domain.PersonnelTypeCount;
import com.ruoyi.system.domain.PresenceDetails;
import com.ruoyi.system.service.impl.PersonnelTypeCountServiceImpl;
import com.ruoyi.system.service.impl.PresenceDetailsServiceImpl;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@RestController
@RequestMapping("/presenceDetails")
public class PresenceDetailsController {
    @Resource
    private PresenceDetailsServiceImpl presenceDetailsService;
    @Resource
    private PersonnelTypeCountServiceImpl personnelTypeCountService;

    @GetMapping("/details")
    public Result<?> details() {
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayBegin = dateformat.format(DateToUtils.getDayBegin());
        String dayEnd = dateformat.format(DateToUtils.getDayEnd());
        PresenceDetails countPresenceDetails = presenceDetailsService.countPresenceDetails(dayBegin, dayEnd);

        //查询业主相关信息
        PersonnelTypeCount personnelTypeCount = personnelTypeCountService.personnelTypeCount();
        BeanUtils.copyProperties(personnelTypeCount, countPresenceDetails);

        return Result.OK(countPresenceDetails);
    }

}
