package com.ruoyi.system.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.basic.IotTspCopy;
import java.util.List;
import java.util.Map;

public interface IotTspService extends IService<IotTsp> {

    List<IotTsp> queryByPage(IotTsp iotTsp);

    IotTspCopy queryByPageCopy(IotTspCopy iotTspCopy);

    List<Map<String, Object>> getCurve(Integer type, Integer text);
}
