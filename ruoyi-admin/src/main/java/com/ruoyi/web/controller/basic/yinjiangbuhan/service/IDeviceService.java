package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;

import java.util.List;

/**
 * 设备Service接口
 * 
 * @author mashir0
 * @date 2024-06-23
 */
public interface IDeviceService extends IService<Device>
{
    /**
     * 修改设备在线状态
     *
     * @param
     * @return 结果
     */
    public int updateDevice(Device device);
}
