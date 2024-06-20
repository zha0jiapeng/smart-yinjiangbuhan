package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.ProjectAttendance;
import com.ruoyi.system.domain.WorkDateStorage;

import java.util.List;

public interface WorkDateStorageService extends IService<WorkDateStorage> {

    String importExcel(List<WorkDateStorage> workDateStorages, Boolean isUpdateSupport, String operName);

    ProjectAttendance attendance(ProjectAttendance projectAttendance);

}
