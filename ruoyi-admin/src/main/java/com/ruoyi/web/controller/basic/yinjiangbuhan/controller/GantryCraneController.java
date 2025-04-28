package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.GantryCrane;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IGantryCraneService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 龙门吊Controller
 *
 * @author ruoyi
 * @date 2025-04-21
 */
@RestController
@RequestMapping("/system/crane")
@Api(tags = {"龙门吊 Controller"})
public class GantryCraneController extends BaseController {
    @Autowired
    private IGantryCraneService gantryCraneService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    /**
     * 查询龙门吊列表
     */
//    @PreAuthorize("@ss.hasPermi('system:crane:list')")
    @GetMapping("/list")
    @ApiOperation("查询龙门吊列表")
    public TableDataInfo list(GantryCrane gantryCrane) {
        startPage();
        List<GantryCrane> list = gantryCraneService.selectGantryCraneList(gantryCrane);
        return getDataTable(list);
    }

    /**
     * 查询最新一条龙门吊数据
     *
     * @param sn
     * @return
     */
    @GetMapping("/last")
    @ApiOperation("查询龙门吊列表")
    public AjaxResult last(String sn) {
        QueryWrapper<GantryCrane> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sn", sn);
        queryWrapper.orderByDesc("id");  // 按 id 倒序排列
        queryWrapper.last("LIMIT 1");  // 限制只取一条数据
        GantryCrane gantryCrane = gantryCraneService.getOne(queryWrapper);
        return AjaxResult.success(gantryCrane);
    }

    /**
     * 导出龙门吊列表
     */
    @PreAuthorize("@ss.hasPermi('system:crane:export')")
    @Log(title = "龙门吊", businessType = BusinessType.EXPORT)
    @ApiOperation("导出龙门吊列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, GantryCrane gantryCrane) {
        List<GantryCrane> list = gantryCraneService.selectGantryCraneList(gantryCrane);
        ExcelUtil<GantryCrane> util = new ExcelUtil<GantryCrane>(GantryCrane.class);
        util.exportExcel(response, list, "龙门吊数据");
    }

    /**
     * 获取龙门吊详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:crane:query')")
    @ApiOperation("获取龙门吊详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "龙门吊参数", required = true)
                              @PathVariable("id") Long id) {
        return success(gantryCraneService.selectGantryCraneById(id));
    }

    /**
     * 新增龙门吊
     */
    @PreAuthorize("@ss.hasPermi('system:crane:add')")
    @Log(title = "龙门吊", businessType = BusinessType.INSERT)
    @ApiOperation("新增龙门吊")
    @PostMapping
    public AjaxResult add(@RequestBody GantryCrane gantryCrane) {
        return toAjax(gantryCraneService.insertGantryCrane(gantryCrane));
    }

    /**
     * 修改龙门吊
     */
    @PreAuthorize("@ss.hasPermi('system:crane:edit')")
    @Log(title = "龙门吊", businessType = BusinessType.UPDATE)
    @ApiOperation("修改龙门吊")
    @PutMapping
    public AjaxResult edit(@RequestBody GantryCrane gantryCrane) {
        return toAjax(gantryCraneService.updateGantryCrane(gantryCrane));
    }

    /**
     * 删除龙门吊
     */
    @PreAuthorize("@ss.hasPermi('system:crane:remove')")
    @Log(title = "龙门吊", businessType = BusinessType.DELETE)
    @ApiOperation("删除龙门吊")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "龙门吊ids参数", required = true)
                             @PathVariable Long[] ids) {
        return toAjax(gantryCraneService.deleteGantryCraneByIds(ids));
    }


    private static final Map<String, BiConsumer<GantryCrane, String>> FIELD_MAPPERS = new HashMap<>();

    static {
        FIELD_MAPPERS.put("主钩吊重", GantryCrane::setLoadWghtOfMain);
        FIELD_MAPPERS.put("主钩高度", GantryCrane::setLoadHghtOfMain);
        FIELD_MAPPERS.put("大车行程", GantryCrane::setDistOfCrane);
        FIELD_MAPPERS.put("小车行程", GantryCrane::setDistOfCar);
        FIELD_MAPPERS.put("风速", GantryCrane::setWindSpeed);
        FIELD_MAPPERS.put("超重报警", GantryCrane::setOverWghtAlm);
        FIELD_MAPPERS.put("小车行程前限", GantryCrane::setFtLimAlmOfCar);
        FIELD_MAPPERS.put("小车行程右限", GantryCrane::setBkLimAlmOfCar);
        FIELD_MAPPERS.put("大车行程左限", GantryCrane::setRtLimAlmOfCrane);
        FIELD_MAPPERS.put("大车行程右限", GantryCrane::setLeLimAlmOfCrane);
        FIELD_MAPPERS.put("风速状态", GantryCrane::setOverWindSpdAlm);
    }

    @GetMapping(value = "/realdata")
    @Scheduled(cron = "0 0/5 * * * ?")
    public void getRealdata() {
        realdata("LJMG001", 6499313, "门燕妮", "420605197607102025");
        realdata("LJMG002", 6499314, "孟祥新", "420626199109134519");
    }

    public void realdata(String alias, Integer boxId, String name, String carId) {
        //登录
        String loginUrl = "http://api.v-box.net/box-data/api/we-data/login?alias=" + alias + "&password=21218cca77804d2ba1922c33e0151105";
        String loginHeader = common("", alias);
        HttpResponse response = HttpRequest.get(loginUrl)
                .header("common", loginHeader)
                .execute();
        String loginResponse = response.body();
        JSONObject json = JSONObject.parseObject(loginResponse);
        String sid = JSONObject.parseObject(json.getString("result")).getString("sid");
        //常用接口
        String realdataUrl = "http://api.v-box.net/box-data/api/we-data/realdata";
        String realdataHeader = common(sid, alias);
        Map<String, Object> formMap = new HashMap<>();
        formMap.put("groupId", 1);
        formMap.put("boxId", boxId); //http://api.v-box.net/box-data/api/we-data/boxlist获取
        formMap.put("devType", 1);
        formMap.put("pageSize", 20);
        formMap.put("pageIndex", 1);
        HttpResponse realdataResponse = HttpRequest.post(realdataUrl)
                .header("common", realdataHeader)
                .header("Content-Type", "multipart/form-data")
                .form(formMap)
                .execute();
        //数据推送
        JSONObject realdataJsonObject = JSON.parseObject(realdataResponse.body());
        JSONObject result = realdataJsonObject.getJSONObject("result");
        JSONArray list = result.getJSONArray("list"); // 获取 list 数组
        GantryCrane gantryCrane = new GantryCrane();
        gantryCrane.setSn(alias);
        gantryCrane.setDriverIdCard(carId);
        gantryCrane.setDriverName(name);
        gantryCrane.setSn(alias);
        for (Object o : list) {
            JSONObject jsonObject = (JSONObject) o;
            String monitorName = jsonObject.getString("monitorName");
            String value = jsonObject.getString("value");
            // 动态调用 setter
            if (FIELD_MAPPERS.containsKey(monitorName)) {
                FIELD_MAPPERS.get(monitorName).accept(gantryCrane, value);
            } else {
                System.out.println("未匹配的监控项: " + monitorName);
            }
        }
        if (gantryCrane.getSn().equals("LJMG001")) {
            gantryCrane.setInstallPosition("8#平洞");//地址
        } else {
            gantryCrane.setInstallPosition("2#预制管片厂");//地址
        }
        push(gantryCrane);


        gantryCrane.setRawData(realdataJsonObject.toJSONString());
        //插入数据库记录
        gantryCraneService.insertGantryCrane(gantryCrane);
    }

    private void push(GantryCrane gantryCrane) {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", gantryCrane.getSn());
        swzkParam.put("dataType", "200300007");
        swzkParam.put("deviceType", "2001000018");
        swzkParam.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        swzkParam.put("deviceName", ""); //设备名称
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType", "gantry_crane");
        profile.put("modelId", "2403");
        profile.put("poiCode", "w1009011");
        profile.put("name", "龙门吊1");
        profile.put("model", "龙门吊");
        profile.put("manufacture", "深圳市机械制造有限公司");
        profile.put("owner", "");
        profile.put("makeDate", "2020-05-22");
        profile.put("validYear", "2050-05-22");
        profile.put("status", "01");
        if (gantryCrane.getSn().equals("LJMG001")) {
            profile.put("installPosition", "8#平洞");//地址
        } else {
            profile.put("installPosition", "2#预制管片厂");//地址
        }

        profile.put("ratedWghtOfMain", "0");
        profile.put("ratedHghtOfMain", "0");
        profile.put("ratedSpeedOfMain", "0");
        profile.put("ratedWghtOfAux", "0");
        profile.put("ratedHghtOfAux", "0");
        profile.put("ratedSpeedOfAux", "0");
        profile.put("spanLength", "0");
        profile.put("x", "0");
        profile.put("y", "0");
        profile.put("z", "0");
        map.put("profile", profile);
        Map<String, Object> properties = new HashMap<>();
        properties.put("monitorTime", DateUtil.now());

        properties.put("distOfCrane", gantryCrane.getDistOfCrane());
        properties.put("distOfCar", gantryCrane.getDistOfCar());
        properties.put("loadWghtOfMain", gantryCrane.getLoadWghtOfMain());
        properties.put("loadHghtOfMain", gantryCrane.getLoadHghtOfMain());
        properties.put("loadWghtOfAux", 0);
        properties.put("loadHghtOfAux", 0);
        properties.put("windSpeed", gantryCrane.getWindSpeed());
        properties.put("rtLimAlmOfCrane", gantryCrane.getRtLimAlmOfCrane());
        properties.put("leLimAlmOfCrane", gantryCrane.getLeLimAlmOfCrane());
        properties.put("ftLimAlmOfCar", gantryCrane.getFtLimAlmOfCar());
        properties.put("bkLimAlmOfCar", gantryCrane.getBkLimAlmOfCar());
        properties.put("overWghtAlm", gantryCrane.getOverWghtAlm());
        properties.put("overWindSpdAlm", gantryCrane.getOverWindSpdAlm());

        properties.put("driverIdCard", gantryCrane.getDriverIdCard());
        properties.put("driverName", gantryCrane.getDriverName());


        map.put("properties", properties);
        map.put("events", new Object());
        map.put("services", new Object());
        valus.add(map);
        swzkParam.put("values", valus);
        swzkHttpUtils.pushIOT(swzkParam);
    }


    public static String common(String sid, String alias) {
        long timestampSeconds = Instant.now().getEpochSecond();
        String jsonString = "";
        if (StringUtils.isNotEmpty(sid)) {
            //常用接口
            String sign = "comid=1041&compvtkey=f3ee1fd566764671838dbae83050450e&sid=" + sid + "&ts=" + timestampSeconds + "&key=f1cd9351930d4e589922edbcf3b09a7c";
            String encrypted = DigestUtils.md5Hex(sign);
            JSONObject json = new JSONObject();
            json.put("compvtkey", "f3ee1fd566764671838dbae83050450e");
            json.put("sign", encrypted);
            json.put("comid", "1041");
            json.put("sid", sid);
            json.put("ts", timestampSeconds);
            jsonString = JSON.toJSONString(json);
        } else {
            //登录
            String sign = "alias=" + alias + "&comid=1041&compvtkey=f3ee1fd566764671838dbae83050450e&password=21218cca77804d2ba1922c33e0151105&ts=" + timestampSeconds + "&key=f1cd9351930d4e589922edbcf3b09a7c";
            String encrypted = DigestUtils.md5Hex(sign);
            JSONObject json = new JSONObject();
            json.put("compvtkey", "f3ee1fd566764671838dbae83050450e");
            json.put("sign", encrypted);
            json.put("comid", "1041");
            json.put("ts", timestampSeconds);
            jsonString = JSON.toJSONString(json);
        }
        return jsonString;
    }

}
