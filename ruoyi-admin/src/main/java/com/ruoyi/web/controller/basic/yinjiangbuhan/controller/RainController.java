package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.RainMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IRainService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 雨量计Controller
 *
 * @author ruoyi
 * @date 2024-08-19
 */
@RestController
@RequestMapping("/system/rain")
@Api(tags = {"雨量计 Controller"})
public class RainController extends BaseController {
    @Autowired
    private IRainService rainService;

    @Autowired(required = false)
    private RainMapper rainMapper;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    /**
     * 查询雨量计列表
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:list')")
    @GetMapping("/list")
    @ApiOperation("查询雨量计列表")
    public TableDataInfo list(Rain rain) {
        startPage();
        List<Rain> list = rainService.selectRainList(rain);
        return getDataTable(list);
    }

    /**
     * 导出雨量计列表
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:export')")
    @Log(title = "雨量计", businessType = BusinessType.EXPORT)
    @ApiOperation("导出雨量计列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Rain rain) {
        List<Rain> list = rainService.selectRainList(rain);
        ExcelUtil<Rain> util = new ExcelUtil<Rain>(Rain.class);
        util.exportExcel(response, list, "雨量计数据");
    }

    /**
     * 获取雨量计详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:query')")
    @ApiOperation("获取雨量计详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "雨量计参数", required = true)
                              @PathVariable("id") Long id) {
        return success(rainService.selectRainById(id));
    }

    /**
     * 新增雨量计
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:add')")
    @Log(title = "雨量计", businessType = BusinessType.INSERT)
    @ApiOperation("新增雨量计")
    @PostMapping
    public AjaxResult add(@RequestBody Rain rain) {
        return toAjax(rainService.insertRain(rain));
    }

    /**
     * 修改雨量计
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:edit')")
    @Log(title = "雨量计", businessType = BusinessType.UPDATE)
    @ApiOperation("修改雨量计")
    @PutMapping
    public AjaxResult edit(@RequestBody Rain rain) {
        return toAjax(rainService.updateRain(rain));
    }

    /**
     * 删除雨量计
     */
//    @PreAuthorize("@ss.hasPermi('system:rain:remove')")
    @Log(title = "雨量计", businessType = BusinessType.DELETE)
    @ApiOperation("删除雨量计")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "雨量计ids参数", required = true)
                             @PathVariable Long[] ids) {
        return toAjax(rainService.deleteRainByIds(ids));
    }


    @GetMapping("/getLatestRain")
    public AjaxResult getLatestRain(){
        QueryWrapper<Rain> rainQueryWrapper = new QueryWrapper<>();
        rainQueryWrapper.orderByDesc("id").last("limit 1");
        Rain latestRain = rainMapper.selectOne(rainQueryWrapper);
        return success(latestRain);
    }

    @GetMapping("/getRain")
    @Scheduled(cron = "0 */1 * * * *")
    public void getRain() throws IOException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginName", "h240627ztsb");
        paramMap.put("password", "h240627ztsb");
        String result3 = HttpUtil.get("http://www.0531yun.com/api/getToken", paramMap);
        JSONObject jsonObject = JSON.parseObject(result3);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
        String token = jsonObject1.getString("token");

        //查询实时数据
        HashMap<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("groupId", "0dc740ca53344982aaa08e29d84f7f2a");
        // 构建请求并添加 "authorization" 请求头
        HttpResponse response1 = HttpRequest.get("http://www.0531yun.com/api/data/getRealTimeData")
                .header("authorization", token) // 添加请求头 "authorization"
                .form(paramMap1)
                .execute(); // 执行请求
        // 获取响应结果
        String result5 = response1.body();

        String objectResult = JSON.parseObject(result5).get("data").toString();
        JSONObject objectResult5 = (JSONObject) JSONArray.parse(objectResult).get(0);

        Rain rain = new Rain();
        rain.setDeviceAddr(objectResult5.get("deviceAddr").toString());
        rain.setDeviceName(objectResult5.get("deviceName").toString());
        rain.setDeviceStatus(objectResult5.get("deviceStatus").toString());
        rain.setLat(objectResult5.get("lat").toString());
        rain.setLng(objectResult5.get("lng").toString());
        rain.setRelayStatus(objectResult5.get("relayStatus").toString());
        rain.setSystemCode(objectResult5.get("systemCode").toString());
        rain.setTimeStamp(objectResult5.get("timeStamp").toString());
        String dataItem = objectResult5.get("dataItem").toString();
        rain.setDataItem(dataItem);
        rain.setRawData(result5);

        JSONArray jsonArray = JSONArray.parse(dataItem);
        //原始数据中当天降雨量
        double rowDataCurRain = 0.0;
        for (Object value : jsonArray) {
            JSONObject object = (JSONObject) value;
            String nodeId = object.get("nodeId").toString();
            //累计雨量
            if (nodeId.equals("20")) {
                String registerItem = object.get("registerItem").toString();
                JSONArray registerItemJsonArray = JSONArray.parse(registerItem);
                JSONObject registerItemJsonObject = (JSONObject) registerItemJsonArray.get(0);
                rain.setTotalRain(registerItemJsonObject.get("data").toString());
            }
            //当天降雨量（存）
            if (nodeId.equals("21")) {
                String registerItem = object.get("registerItem").toString();
                JSONArray registerItemJsonArray = JSONArray.parse(registerItem);
                JSONObject registerItemJsonObject = (JSONObject) registerItemJsonArray.get(1);
                rowDataCurRain = Double.parseDouble(registerItemJsonObject.get("data").toString());
                rain.setOldRain1(String.valueOf(rowDataCurRain));
            }
        }
        //一分钟的降雨量
        QueryWrapper<Rain> rainQueryWrapper = new QueryWrapper<>();
        rainQueryWrapper.orderByDesc("id").last("limit 1");
        Rain latestRain = rainMapper.selectOne(rainQueryWrapper);
        if (latestRain != null) {
            //一分钟的
            double oldRain1 = Double.parseDouble(latestRain.getOldRain1());
            double result = NumberUtil.sub(rowDataCurRain, oldRain1);
            rain.setRain1(String.valueOf(result));
        } else {
            //如果没有的话，默认为0
            rain.setRain1("0.0");
        }
        //当天降雨量
        // 创建 QueryWrapper 实例
        QueryWrapper<Rain> rainListQueryWrapper = new QueryWrapper<>();
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 获取今天的 08:00 时间点
        LocalDateTime todayEightAM = LocalDateTime.of(now.toLocalDate(), LocalTime.of(8, 0));
        // 获取明天的 08:00 时间点
        LocalDateTime tomorrowEightAM = todayEightAM.plusDays(1);
        // 构建查询条件
        rainListQueryWrapper.ge("create_time", todayEightAM) // createTime >= 今天的 08:00
                .le("create_time", tomorrowEightAM); // createTime <= 明天的 08:00
        // 执行查询
        List<Rain> results = rainMapper.selectList(rainListQueryWrapper);
        double curRain = 0.0;
        if (results != null) {
            for (Rain value : results) {
                //当天降雨量
                curRain = NumberUtil.add(curRain, Double.parseDouble(value.getRain1()));
            }
        }
        rain.setCurRain(String.valueOf(curRain));

        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        object.put("deviceType", "2001000008");////设备类型，见1.1章节
        object.put("SN", "40331531");//设备SN号,必填
        object.put("dataType", "80000");//固定值
        object.put("bidCode", "YJBH-SSZGX_BD-SG-205");//标段编码
        object.put("workAreaCode", "YJBH-SSZGX_GQ-08");//工区编码
        object.put("deviceName", "雨量计");   //设备名称
        com.alibaba.fastjson.JSONArray values = new com.alibaba.fastjson.JSONArray();
        com.alibaba.fastjson.JSONObject valuesJSON = new com.alibaba.fastjson.JSONObject();
        valuesJSON.put("reportTs", rain.getTimeStamp());//数据报告时间
        com.alibaba.fastjson.JSONObject valuesJSONEventsMonitorData = new com.alibaba.fastjson.JSONObject();
        valuesJSONEventsMonitorData.put("eventTs", rain.getTimeStamp());
        valuesJSONEventsMonitorData.put("eventType", 1);
        // 将时间戳转换为LocalDateTime
        Long timestamp = Long.parseLong(rain.getTimeStamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化日期时间
        String formattedDateTime = dateTime.format(formatter);
        valuesJSONEventsMonitorData.put("monitorTime", formattedDateTime);
        valuesJSONEventsMonitorData.put("waterLevel", rain.getOldRain1());
        rain.setWaterLevel(rain.getOldRain1());
        valuesJSONEventsMonitorData.put("curRain", rain.getCurRain());
        valuesJSONEventsMonitorData.put("totalRain", rain.getTotalRain());
        valuesJSONEventsMonitorData.put("rain1", rain.getRain1());
        QueryWrapper<Rain> rain60QueryWrapper = new QueryWrapper<>();
        rain60QueryWrapper.orderByDesc("id").last("limit 60");
        List<Rain> latestRainList = rainMapper.selectList(rain60QueryWrapper);
        double count = 0.0;
        // 使用 Math.min 来确保不超过实际数据的大小
        int dataSize = Math.min(latestRainList.size(), 5);
        for (int i = 0; i < dataSize; i++) {
            count = NumberUtil.add(count, Double.parseDouble(latestRainList.get(i).getRain1()));
        }
        valuesJSONEventsMonitorData.put("rain5", count);
        rain.setRain5(String.valueOf(count));
        dataSize = Math.min(latestRainList.size(), 10);
        for (int i = 0; i < dataSize; i++) {
            count = NumberUtil.add(count, Double.parseDouble(latestRainList.get(i).getRain1()));
        }
        valuesJSONEventsMonitorData.put("rain10", count);
        rain.setRain10(String.valueOf(count));
        dataSize = Math.min(latestRainList.size(), 60);
        for (int i = 0; i < dataSize; i++) {
            count = NumberUtil.add(count, Double.parseDouble(latestRainList.get(i).getRain1()));
        }
        valuesJSONEventsMonitorData.put("rain60", count);
        rain.setRain60(String.valueOf(count));

        rainService.insertRain(rain);


        //信号强度   没有
        valuesJSONEventsMonitorData.put("signal", "0");
        //电池电量   没有
        valuesJSONEventsMonitorData.put("battery", "0");

        com.alibaba.fastjson.JSONObject valuesJSONprofile = new com.alibaba.fastjson.JSONObject();
        valuesJSONprofile.put("appType", "waterMonitor");//固定值，不用改
        valuesJSONprofile.put("modelId", "200012");//固定值，不用改
        valuesJSONprofile.put("poiCode", "w1315001");//固定值，不用改
        valuesJSONprofile.put("deviceType", "2001000032");//固定值，不用改
        valuesJSON.put("profile", valuesJSONprofile);


        com.alibaba.fastjson.JSONObject valuesJSONproperties = new com.alibaba.fastjson.JSONObject();
        valuesJSONproperties.put("name", "雨量计");// 设备名称
        valuesJSONproperties.put("status", 1);
        valuesJSONproperties.put("report", 1);
        //设备型号
        valuesJSONproperties.put("model", "RS-RADZJ-E-Y-4G");
        //设备制造商
        valuesJSONproperties.put("manufacture", "建大仁科");
        //设备制造商
        valuesJSONproperties.put("makeDate", "建大仁科");
        //有效年限
        valuesJSONproperties.put("validYear", "一年");

        //设备使用状态
        valuesJSONproperties.put("state", "1");
        //产权单位
        valuesJSONproperties.put("owner", "土建4标");
        //安装日期
        valuesJSONproperties.put("makeDate", "2024-06-28");

        //设备安装位置
        valuesJSONproperties.put("installPosition", "8号洞出渣场");

        //腾讯坐标x，经度，eg：113.073201
        valuesJSONproperties.put("x", "");

        //腾讯坐标y,纬度， eg：22.792789
        valuesJSONproperties.put("y", "");

        //大地坐标Z
        valuesJSONproperties.put("z", "");
        valuesJSON.put("properties", valuesJSONproperties);

        com.alibaba.fastjson.JSONObject valuesJSONservices = new com.alibaba.fastjson.JSONObject();
        valuesJSON.put("services", valuesJSONservices);

        valuesJSON.put("events", valuesJSONEventsMonitorData);
        values.add(valuesJSON);
        object.put("values", values);
        Map<String, Object> map = jsonObjectToMap(object);
       // swzkHttpUtils.pushIOT(map);
    }


    public static Map<String, Object> jsonObjectToMap(com.alibaba.fastjson.JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof com.alibaba.fastjson.JSONObject) {
                map.put(key, jsonObjectToMap((com.alibaba.fastjson.JSONObject) value));
            } else if (value instanceof com.alibaba.fastjson.JSONArray) {
                map.put(key, value); // 简单处理数组类型，如有需要可进一步处理
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
