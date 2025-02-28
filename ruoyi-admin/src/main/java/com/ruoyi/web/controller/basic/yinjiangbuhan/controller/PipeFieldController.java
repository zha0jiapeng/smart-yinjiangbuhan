package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管片厂Controller
 *
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/PipeField")
public class PipeFieldController extends BaseController {

    /**
     * 管片厂面登录页面
     */
    @GetMapping("/login")
    public AjaxResult getLogin() {
        // 创建 BCryptPasswordEncoder 实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 原始密码
        String rawPassword = "NabDKk0uSe8r14O3OGvulR15KsNkOUJj8YjueMk0waz1";

        // 获取当前日期
        Date currentDate = new Date();

        // 定义日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        // 格式化日期
        String formattedDate = formatter.format(currentDate);

        rawPassword += formattedDate;

        // 加密密码
        String encodedPassword = encoder.encode(rawPassword);

        HttpResponse response1 = HttpRequest.get("https://cr18yjbh.ohoyee.com:8080/rms/api/uaa/sso/login")
                .header("key", encodedPassword) // 添加请求头 "authorization"
                .execute(); // 执行请求
        // 获取响应结果
        String result = response1.body();
        String redirectUrl = JSONObject.parseObject(result)
                .getJSONObject("data") // 获取 "data" 对象
                .getString("redirectUrl"); // 获取 "redirectUrl" 字段
        return success(redirectUrl);
    }
}
