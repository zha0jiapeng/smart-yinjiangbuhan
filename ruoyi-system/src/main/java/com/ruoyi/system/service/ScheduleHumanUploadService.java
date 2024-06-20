package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;

import java.util.List;

public interface ScheduleHumanUploadService extends IService<ScheduleHumanUpload> {

    List<ScheduleHumanUpload> queryListScheduleHumanUpload(ScheduleHumanUpload scheduleHumanUpload);

    void updateBatchScheduleHumanUpload(List<ScheduleHumanUpload> scheduleHumanUploads);

    List<ScheduleHumanUpload> bimNewListScheduleHumanUpload();

}
