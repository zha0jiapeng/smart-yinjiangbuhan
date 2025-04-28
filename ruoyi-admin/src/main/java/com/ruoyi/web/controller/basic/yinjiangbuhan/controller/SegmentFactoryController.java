package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SpecialOperationTypes;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 管片厂
 */
@RestController
@RequestMapping("receive")
public class SegmentFactoryController {

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @PostMapping("/pushIOT")
    public String pushIOT(@RequestBody String param) {
//        System.out.println("管片厂数据推送：" + param);
        Map<String, Object> parsedParam = JSON.parseObject(param, Map.class);
        return swzkHttpUtils.pushGPCIOT(parsedParam);
    }

}
