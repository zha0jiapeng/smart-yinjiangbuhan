package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 扬尘检测数据
 * @author hu_p
 * @date 2024/6/21
 */
@RestController
@Slf4j
@Api(description = "扬尘检测数据")
public class DustDetectionController {

    @Autowired
    RedisCache redisCache;

    @PostMapping("dustdetection")
    @Log(title = "扬尘检测数据", businessType = BusinessType.INSERT)
    public AjaxResult uploadDustDetectionData(@RequestBody DustDetectionData dustDetectionData) {
        redisCache.setCacheObject("dustdetection", dustDetectionData);
        return AjaxResult.success();
    }

    @GetMapping("dustdetection")
    public AjaxResult getDustDetectionData() {
        final DustDetectionData data = redisCache.getCacheObject("dustdetection");
        return AjaxResult.success(data);
    }

}
