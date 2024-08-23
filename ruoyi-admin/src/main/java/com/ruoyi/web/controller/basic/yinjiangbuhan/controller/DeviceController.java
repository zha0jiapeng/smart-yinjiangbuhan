package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 设备Controller
 *
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {
    @Autowired
    private IDeviceService deviceService;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public AjaxResult list(Device device) {
        LambdaQueryWrapper<Device> lq = new LambdaQueryWrapper<>();
        lq.eq(Device::getYn, 1);
        if (StringUtils.isNotEmpty(device.getDeviceType())) {
            lq.eq(Device::getDeviceType, device.getDeviceType());
        }
        if (StringUtils.isNotEmpty(device.getDeviceArea())) {
            lq.eq(Device::getDeviceArea, device.getDeviceArea());
        }
        List<Device> list = deviceService.list(lq);
        return AjaxResult.success(list);
    }

    @GetMapping("/info")
    public AjaxResult info(Device device) {
        LambdaQueryWrapper<Device> lq = new LambdaQueryWrapper<>();
        lq.eq(Device::getYn, 1);
        lq.eq(Device::getId, device.getId());
        if (StringUtils.isNotEmpty(device.getDeviceType())) {
            lq.eq(Device::getDeviceType, device.getDeviceType());
        }
        if (StringUtils.isNotEmpty(device.getDeviceArea())) {
            lq.eq(Device::getDeviceArea, device.getDeviceArea());
        }
        Device list = deviceService.getOne(lq, false);
        return AjaxResult.success(list);
    }


    @GetMapping("/getDeviceType")
    public AjaxResult getDeviceType() {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT device_type");
        List<Map<String, Object>> maps = deviceService.listMaps(queryWrapper);

        List<String> deviceType = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            deviceType.add(map.get("device_type").toString());
        }
        return AjaxResult.success(deviceType);
    }

    @GetMapping("/getDeviceArea")
    public AjaxResult getDeviceArea(String deviceType) {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT device_area");
        queryWrapper.eq("device_type", deviceType);
        List<Map<String, Object>> maps = deviceService.listMaps(queryWrapper);

        List<String> deviceArea = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            deviceArea.add(map.get("device_area").toString());
        }
        return AjaxResult.success(deviceArea);
    }

    /**
     * 获取设备详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:device:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(deviceService.getById(id));
    }

    /**
     * 设备离线总数
     */
    @GetMapping("/getTotalDeviceOfflineCount")
    public AjaxResult getTotalDeviceOfflineCount() {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_online", 0);
        queryWrapper.isNotNull("device_ip")
                .notLike("device_ip", "%,%");
        int count = deviceService.count(queryWrapper);
        return success(count);
    }

    /**
     * 设备总数
     */
    @GetMapping("/getTotalDeviceCount")
    public AjaxResult getTotalDeviceCount() {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("device_ip")
                .notLike("device_ip", "%,%");
        int count = deviceService.count(queryWrapper);
        return success(count);
    }
}
