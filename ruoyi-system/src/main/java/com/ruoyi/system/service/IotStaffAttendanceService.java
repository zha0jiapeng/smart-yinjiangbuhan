package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.IotStaffAttendance;

import java.util.List;


public interface IotStaffAttendanceService extends IService<IotStaffAttendance> {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    IotStaffAttendance queryById(Integer pid);

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    List<IotStaffAttendance> queryByPage(IotStaffAttendance iotStaffAttendance);

    void saveScheduled(String dayBegin, String dayEnd);
}
