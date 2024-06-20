package com.ruoyi.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.basic.IotTspCopy;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IotTspMapper extends BaseMapper<IotTsp> {

    List<IotTsp> queryAllByLimit(IotTsp iotTsp);

    List<IotTspCopy> queryAllList(@Param("starTime") String starTime, @Param("endTime")  String endTime);

}
