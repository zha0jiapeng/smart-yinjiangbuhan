package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.mapper.CarAccessMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.DeviceMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 设备Service业务层处理
 * 
 * @author mashir0
 * @date 2024-06-23
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService
{
    @Resource
    DeviceMapper deviceMapper;
}
