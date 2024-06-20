package com.ruoyi.web.controller.basic.view.hardware;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.GasHistoryData;
import com.ruoyi.system.service.GasHistoryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
public class GasHistoryDataController extends BaseController {

    @Resource
    private GasHistoryDataService gasHistoryDataService;

    @GetMapping("gas/history/data/list")
    public TableDataInfo list(GasHistoryData gasHistoryData) {
        startPage();
        QueryWrapper<GasHistoryData> queryWrapper = new QueryWrapper<>();

        LocalDate now = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 当天开始时间
        LocalDateTime todayStart = now.atStartOfDay();
        // 当天结束时间
        LocalDateTime todayEnd = LocalDateTime.of(now, LocalTime.MAX);
        queryWrapper.ge("时间", pattern.format(todayStart))
                .le("时间", pattern.format(todayEnd));
        queryWrapper.orderByDesc("时间");

        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<GasHistoryData> listPage = gasHistoryDataService.list(queryWrapper);
        return getDataTable(listPage);
    }


}
