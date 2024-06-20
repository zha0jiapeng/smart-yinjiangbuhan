package com.ruoyi.web.controller.basic.view;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.VideoConfig;
import com.ruoyi.system.service.VideoConfigService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("video/config")
public class VideoConfigController extends BaseController {

    @Value("${bsl.video.ip}")
    private String bslVideoIp;

    @Resource
    private VideoConfigService videoConfigService;

    @GetMapping("getVideoIp")
    public Result<?> getVideoIp() {
        return Result.ok(bslVideoIp);
    }

    @GetMapping("list")
    public TableDataInfo list() {
        startPage();
        List<VideoConfig> list = videoConfigService.list();
        return getDataTable(list);
    }


}
