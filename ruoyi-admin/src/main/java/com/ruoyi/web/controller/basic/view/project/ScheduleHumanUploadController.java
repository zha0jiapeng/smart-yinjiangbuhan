package com.ruoyi.web.controller.basic.view.project;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.service.ScheduleHumanUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("scheduleHumanUpload")
public class ScheduleHumanUploadController extends BaseController {

    @Resource
    private ScheduleHumanUploadService scheduleHumanUploadService;

    @GetMapping("/list")
    public TableDataInfo list(ScheduleHumanUpload scheduleHumanUpload) {
        startPage();
        scheduleHumanUpload.setOrderByAscOrDesc(YnEnum.N.getCode());
        List<ScheduleHumanUpload> listPage = scheduleHumanUploadService.queryListScheduleHumanUpload(scheduleHumanUpload);
        return getDataTable(listPage);
    }

    @GetMapping("all")
    public AjaxResult queryAlleList() {
        ScheduleHumanUpload scheduleHumanUpload = new ScheduleHumanUpload();
        scheduleHumanUpload.setOrderByAscOrDesc(YnEnum.Y.getCode());
        List<ScheduleHumanUpload> list = scheduleHumanUploadService.queryListScheduleHumanUpload(scheduleHumanUpload);
        return success(list);
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody List<ScheduleHumanUpload> scheduleHumanUploads) {
        scheduleHumanUploadService.updateBatchScheduleHumanUpload(scheduleHumanUploads);
        return AjaxResult.success();
    }

}
