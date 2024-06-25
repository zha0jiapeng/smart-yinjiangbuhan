package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author hu_p
 * @date 2024/6/25
 */
@RestController
@RequestMapping("energyManagement")
@AllArgsConstructor
public class EnergyManagementController {

    @Autowired
    RedisCache redisCache;

    @GetMapping("ammeterDataSummary")
    public AjaxResult ammeterDataSummary() {
        HttpRequest request = HttpUtil.createPost("http://nhapi.yunjichaobiao.com/api/Device/AmmeterData_Summary")
                .bearerAuth(getToken()).body("{\n" +
                        "    \"areaID\": \"51733\",\n" +
                        "    \"ammeterID\": \"98261\",\n" +
                        "    \"PrivAddr\": \"%2FEquipment%2Faqgz.html\"\n" +
                        "}");
        try (HttpResponse resp = request.execute()) {
            final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
            if (Objects.isNull(data)) {
                refreshToken();
                return AjaxResult.error("token is expired");
            }
            return AjaxResult.success(JSON.parseArray(data));
        }
    }

    /**
     * 获取指标曲线总数
     * @param dataType 15m 15分钟; D 日; M 月
     */
    @GetMapping("getIndexCurveTotal")
    public AjaxResult getIndexCurveTotal(@RequestParam String dataType) {
        return requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetIndexCurveTotal?dateType=" + dataType + "&PrivAddr=%252Findex.html");
    }

    @GetMapping("getUseEnergy")
    public AjaxResult getUseEnergy() {
        return requestUrl("http://nhapi.yunjichaobiao.com/api/Main/GetUseEnergy?PrivAddr=%252Findex.html");
    }

    private AjaxResult requestUrl(String url) {
        HttpRequest request = HttpUtil.createGet(url)
                .bearerAuth(getToken());
        try (HttpResponse resp = request.execute()) {
            final String data = JSON.parseObject(JSON.parse(resp.body()).toString()).getString("Data");
            if (Objects.isNull(data)) {
                refreshToken();
                return AjaxResult.error("token is expired");
            }
            return AjaxResult.success(JSON.parseObject(data));
        }
    }

    void refreshToken() {
        // todo
    }

    String getToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyMDMwNCIsIlBJRCI6IjExNjQ5IiwiTGFuZyI6ImNuIiwiUlR5cGUiOiJzdHJpbmciLCJDbGllbnQiOiIwIiwiZXhwIjoxNzE5NDE0NzM4fQ.Qm2YQwm4F4GKT_oQoTrFaLFOGeLEhw1VYJFs4akZV5Q";
    }

}
