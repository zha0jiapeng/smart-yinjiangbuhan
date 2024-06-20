package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ProjectAttendance;
import com.ruoyi.system.domain.WorkDateStorage;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.mapper.IotStaffAttendanceMapper;
import com.ruoyi.system.mapper.WorkDateStorageMapper;
import com.ruoyi.system.service.WorkDateStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkDateStorageServiceImpl extends ServiceImpl<WorkDateStorageMapper, WorkDateStorage> implements WorkDateStorageService {

    @Resource
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;

    @Override
    public String importExcel(List<WorkDateStorage> workDateStorages, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(workDateStorages) || workDateStorages.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        this.saveBatch(workDateStorages);
        return "成功";
    }

    @Override
    public ProjectAttendance attendance(ProjectAttendance projectAttendance) {
        ProjectAttendance projectAttendanceResp = new ProjectAttendance();

        String endDate = projectAttendance.getEndDate();
        String[] split = endDate.split("-");
        String titleBar = split[0] + "年" + split[1] + "月保障班考勤登记表";
        projectAttendanceResp.setTitleBar(titleBar);

        projectAttendanceResp.setCompanyName("支付单位：中铁十八局集团有限公司深圳市白石岭区域天然气管线调整工程项目部");

        //统计周期
        List<Integer> oughtAttendanceDayList = new ArrayList<>();

        String[] split1 = projectAttendance.getStartDate().split("-");
        oughtAttendanceDayList.add(Integer.valueOf(split1[2]));

        try {
            List<String> monthBetweenDateStr = DateUtils.getMonthBetweenDateStr(projectAttendance.getStartDate(), projectAttendance.getEndDate());
            if (!CollectionUtils.isEmpty(monthBetweenDateStr)) {
                for (String var : monthBetweenDateStr) {
                    String[] split2 = var.split("-");
                    oughtAttendanceDayList.add(Integer.valueOf(split2[2]));
                }
            }
        } catch (Exception e) {
            log.error("进度资源 计算人员考勤 时间周期异常:{}", e);
        }

        if (!projectAttendance.getStartDate().equals(projectAttendance.getEndDate())) {
            oughtAttendanceDayList.add(Integer.valueOf(split[2]));
        }

        projectAttendanceResp.setOughtAttendanceDayList(oughtAttendanceDayList);

        //统计人员
        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(
                projectAttendance.getStartDate() + " 00:00:00",
                projectAttendance.getEndDate() + " 23:59:59");

        if (!CollectionUtils.isEmpty(list)) {
            List<ProjectAttendance.ProjectAttendanceStaff> staffList = new ArrayList<>();

            Map<String, List<IotStaffAttendance>> listMap = list.stream()
                    .filter(var -> var.getName() != null)
                    .collect(Collectors.groupingBy(IotStaffAttendance::getName));
            listMap.forEach((k, v) -> {
                ProjectAttendance.ProjectAttendanceStaff projectAttendanceStaff = new ProjectAttendance.ProjectAttendanceStaff();
                projectAttendanceStaff.setName(k);
                List<ProjectAttendance.ProjectAttendancedDay> attendanceDayList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(v)) {
                    IotStaffAttendance iotStaffAttendance = v.stream().findFirst().orElse(new IotStaffAttendance());
                    ProjectAttendance.ProjectAttendancedDay projectAttendancedDay = new ProjectAttendance.ProjectAttendancedDay();
                    projectAttendancedDay.setFlag(true);
                    String[] split2 = iotStaffAttendance.getDatetime().split("-");
                    projectAttendancedDay.setDay(split2[2].substring(0, 2));
                    attendanceDayList.add(projectAttendancedDay);
                }
                projectAttendanceStaff.setAttendanceDayList(attendanceDayList);
                staffList.add(projectAttendanceStaff);
            });

            projectAttendanceResp.setStaffList(staffList);
        }

        return projectAttendanceResp;
    }

}
