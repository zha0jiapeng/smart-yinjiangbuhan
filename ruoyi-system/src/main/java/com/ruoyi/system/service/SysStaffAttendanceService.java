package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.SysStaffAttendance;

public interface SysStaffAttendanceService extends IService<SysStaffAttendance> {

    /**
     * 导出人员考勤信息
     * @param sysStaffAttendance 参数
     */
    public void exportStaffAttendance(SysStaffAttendance sysStaffAttendance);

}
