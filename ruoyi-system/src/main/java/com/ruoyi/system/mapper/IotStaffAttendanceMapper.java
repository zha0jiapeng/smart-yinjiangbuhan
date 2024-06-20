package com.ruoyi.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IotStaffAttendanceMapper extends BaseMapper<IotStaffAttendance> {

    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    IotStaffAttendance queryById(Integer pid);

    /**
     * 查询指定行数据
     * @return 对象列表
     */
    List<IotStaffAttendance> queryAllByLimit(IotStaffAttendance iotStaffAttendance);

    List<IotStaffAttendance> queryAll(@Param("dayBegin") String dayBegin, @Param("dayEnd")  String dayEnd);

}
