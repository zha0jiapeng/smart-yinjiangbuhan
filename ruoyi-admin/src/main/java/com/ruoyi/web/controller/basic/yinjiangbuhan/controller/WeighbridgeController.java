package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Weighbridge;
import com.ruoyi.web.controller.basic.yinjiangbuhan.enums.MaterialType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IWeighbridgeService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.io.ModbusSerialTransaction;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.net.SerialConnection;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 地磅Controller
 *
 * @author ruoyi
 * @date 2025-01-23
 */
@RestController
@RequestMapping("/system/weighbridge")
@Api(tags = {"地磅 Controller"})
public class WeighbridgeController extends BaseController {
    @Autowired
    private IWeighbridgeService weighbridgeService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;



    /**
     * 查询地磅列表
     */
    @GetMapping("/test8")
    @ApiOperation("查询地磅列表")
    public AjaxResult test8() {
        // 串口参数设置
        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setPortName("1"); // 设置串口名称
        serialParameters.setBaudRate(9600); // 设置波特率
        serialParameters.setDatabits(8); // 设置数据位
        serialParameters.setParity("E"); // 设置校验位 (E for Even)
        serialParameters.setStopbits(1); // 设置停止位

        System.out.println("风机参数设置完毕");
        // 创建串口连接
        SerialConnection connection = new SerialConnection(serialParameters);
        System.out.println("准备打开风机链接");
        try {
            // 打开连接
            connection.open();
            System.out.println("风机链接打开成功");

            // 创建 Modbus 事务
            ModbusSerialTransaction transaction = new ModbusSerialTransaction(connection);
            transaction.setRetries(3); // 设置重试次数
            System.out.println("准备创建输入寄存器的请求");
            // 创建读取输入寄存器的请求
            ReadInputRegistersRequest request = new ReadInputRegistersRequest();
            request.setUnitID(1); // 设置从站地址
            request.setReference(16139); // 设置寄存器地址
            request.setWordCount(1); // 设置要读取的寄存器数量
            System.out.println("输入完毕寄存器的请求");
            // 设置事务请求
            transaction.setRequest(request);

            // 执行事务
            transaction.execute();

            // 获取响应
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) transaction.getResponse();
            if (response != null) {
                System.out.println("风机的参数获取值Register value: " + response.getRegisterValue(0));
            } else {
                System.out.println("No response received.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            connection.close();
        }
        return success();
    }



    /**
     * 查询地磅列表
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:list')")
    @GetMapping("/list")
    @ApiOperation("查询地磅列表")
    public TableDataInfo list(Weighbridge weighbridge) {
        startPage();
        List<Weighbridge> list = weighbridgeService.selectWeighbridgeList(weighbridge);
        return getDataTable(list);
    }

    /**
     * 导出地磅列表
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:export')")
    @Log(title = "地磅", businessType = BusinessType.EXPORT)
    @ApiOperation("导出地磅列表Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Weighbridge weighbridge) {
        List<Weighbridge> list = weighbridgeService.selectWeighbridgeList(weighbridge);
        ExcelUtil<Weighbridge> util = new ExcelUtil<Weighbridge>(Weighbridge.class);
        util.exportExcel(response, list, "地磅数据");
    }

    /**
     * 获取地磅详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:query')")
    @ApiOperation("获取地磅详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@ApiParam(name = "id", value = "地磅参数", required = true) @PathVariable("id") Long id) {
        return success(weighbridgeService.selectWeighbridgeById(id));
    }

    /**
     * 新增地磅
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:add')")
    @Log(title = "地磅", businessType = BusinessType.INSERT)
    @ApiOperation("新增地磅")
    @PostMapping
    public AjaxResult add(@RequestBody Weighbridge weighbridge) {
        return toAjax(weighbridgeService.insertWeighbridge(weighbridge));
    }

    /**
     * 修改地磅
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:edit')")
    @Log(title = "地磅", businessType = BusinessType.UPDATE)
    @ApiOperation("修改地磅")
    @PutMapping
    public AjaxResult edit(@RequestBody Weighbridge weighbridge) {
        return toAjax(weighbridgeService.updateWeighbridge(weighbridge));
    }

    /**
     * 删除地磅
     */
    @PreAuthorize("@ss.hasPermi('system:weighbridge:remove')")
    @Log(title = "地磅", businessType = BusinessType.DELETE)
    @ApiOperation("删除地磅")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(name = "ids", value = "地磅ids参数", required = true) @PathVariable Long[] ids) {
        return toAjax(weighbridgeService.deleteWeighbridgeByIds(ids));
    }

    public static final String secret = "8dd808a66a402aed7444b1b9a9b2433c";


    @RequestMapping("/test")
    public AjaxResult test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 假设结束时间是当前时间
        LocalDateTime endDate = LocalDateTime.now();
        String endDateString = endDate.format(formatter);
        // 开始时间是结束时间减去10分钟
        LocalDateTime startDate = endDate.minusMinutes(10);
        String startDateString = startDate.format(formatter);
        LocalDate today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        formattedDate = secret + formattedDate;

        String result = HttpRequest.get("https://cr18yjbh.ohoyee.com:8080/rms/api/nproduce/collectData/query/dibang")
                .header("secret", sha256(formattedDate))
                .form("size", 100)
                .form("current", 1)
                .form("startDate", "2025-01-17 10:00:00")
//                .form("startDate", startDateString)
//                .form("endDate", endDateString)
                .form("endDate", "2025-01-17 11:00:00")
                .execute()
                .body();

        //落库
        // 转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(result);
        // 获取 data 数组
        JSONArray dataArray = jsonObject.getJSONArray("data");
        // 遍历 data 数组
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject dataItem = dataArray.getJSONObject(i);
            Map<String, Object> item = new HashMap<>();
            long rawDataId = dataItem.getLongValue("id");
            QueryWrapper<Weighbridge> weighbridgeQueryWrapper = new QueryWrapper<>();
            weighbridgeQueryWrapper.eq("raw_data_id", rawDataId);
            Weighbridge weighbridge = weighbridgeService.getOne(weighbridgeQueryWrapper);
            //查出来不为null，那就是有这条数据，为null那就是没有
            if (weighbridge == null || weighbridge.getTareWeightingTime() == null) {
                item.put("eventType", 1);
                //车牌号
                item.put("plateNumber", dataItem.getString("carNumber"));

                //物料名称
                String materialName = dataItem.getString("type");
                item.put("materialName", materialName);
                MaterialType materialType = getMaterialTypeByName(materialName);
                String typeName = "";
                if (materialType != null) {
                    typeName = materialType.getTypeName();
                } else {
                    typeName = "其他";
                }
                //物料类型
                item.put("materialType", typeName);
                //毛重
                item.put("totalWeight", dataItem.getDoubleValue("gross"));
                //皮重
                item.put("carWeight", dataItem.getDoubleValue("tare"));

                //出入场状态
                String tareWeightingTime = dataItem.getString("tareWeightingTime");
                if (tareWeightingTime == null) {
                    item.put("passDirection", dataItem.getString("02"));
                    //通过时间
                    item.put("passTime", dataItem.getString("grossWeightingTime"));
                } else {
                    item.put("passDirection", dataItem.getString("01"));
                    //通过时间
                    item.put("passTime", tareWeightingTime);
                }

                item.put("describe", "");
                item.put("pictures", new JSONArray());

                //插入数据库
                if (weighbridge == null) {
                    Weighbridge saveWeighbridge = new Weighbridge();
                    saveWeighbridge.setGross(String.valueOf(item.get("totalWeight")));
                    saveWeighbridge.setTare(String.valueOf(item.get("carWeight")));
                    saveWeighbridge.setTareWeightingTime(tareWeightingTime);
                    saveWeighbridge.setCarNumber(String.valueOf(item.get("plateNumber")));
                    saveWeighbridge.setNetWeight(String.valueOf(item.get("carWeight")));
                    saveWeighbridge.setRawData(dataItem.toString());
                    saveWeighbridge.setMaterialName(materialName);
                    saveWeighbridge.setGrossWeightingTime(dataItem.getString("grossWeightingTime"));
                    saveWeighbridge.setRawDataId(rawDataId);
                    weighbridgeService.insertWeighbridge(saveWeighbridge);
                } else if (weighbridge != null && weighbridge.getTareWeightingTime() == null){
                    weighbridge.setTareWeightingTime(tareWeightingTime);
                    weighbridgeService.updateWeighbridge(weighbridge);
                }
                //执行推送
                push(item);
            }
        }
        return AjaxResult.success();
    }

//    @Scheduled(cron = "0 */7 * * * ?")
    public AjaxResult getWeighbridgeData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 假设结束时间是当前时间
        LocalDateTime endDate = LocalDateTime.now();
        String endDateString = endDate.format(formatter);
        // 开始时间是结束时间减去10分钟
        LocalDateTime startDate = endDate.minusMinutes(10);
        String startDateString = startDate.format(formatter);
        LocalDate today = LocalDate.now();
        formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        formattedDate = secret + formattedDate;

        String result = HttpRequest.get("https://cr18yjbh.ohoyee.com:8080/rms/api/nproduce/collectData/query/dibang")
                .header("secret", sha256(formattedDate))
                .form("size", 100)
                .form("current", 1)
                .form("startDate", startDateString)
                .form("endDate", endDateString)
                .execute()
                .body();

        //落库
        // 转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(result);
        // 获取 data 数组
        JSONArray dataArray = jsonObject.getJSONArray("data");
        // 遍历 data 数组
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject dataItem = dataArray.getJSONObject(i);
            Map<String, Object> item = new HashMap<>();
            long rawDataId = dataItem.getLongValue("id");
            QueryWrapper<Weighbridge> weighbridgeQueryWrapper = new QueryWrapper<>();
            weighbridgeQueryWrapper.eq("raw_data_id", rawDataId);
            Weighbridge weighbridge = weighbridgeService.getOne(weighbridgeQueryWrapper);
            //查出来不为null，那就是有这条数据，为null那就是没有
            if (weighbridge == null || weighbridge.getGrossWeightingTime() == null) {
                item.put("eventType", 1);
                //车牌号
                item.put("plateNumber", dataItem.getString("carNumber"));

                //物料名称
                String materialName = dataItem.getString("type");
                item.put("materialName", materialName);
                MaterialType materialType = getMaterialTypeByName(materialName);
                String typeName = "";
                if (materialType != null) {
                    typeName = materialType.getTypeName();
                } else {
                    typeName = "其他";
                }
                //物料类型
                item.put("materialType", typeName);
                //毛重
                item.put("totalWeight", dataItem.getDoubleValue("gross"));
                //皮重
                item.put("carWeight", dataItem.getDoubleValue("tare"));

                //出入场状态
                String tareWeightingTime = dataItem.getString("tareWeightingTime");
                if (tareWeightingTime == null) {
                    item.put("passDirection", dataItem.getString("02"));
                    //通过时间
                    item.put("passTime", dataItem.getString("grossWeightingTime"));
                } else {
                    item.put("passDirection", dataItem.getString("01"));
                    //通过时间
                    item.put("passTime", tareWeightingTime);
                }

                item.put("describe", "");
                item.put("pictures", new JSONArray());


                if (weighbridge.getGrossWeightingTime() == null) {
                    Weighbridge saveWeighbridge = new Weighbridge();
                    saveWeighbridge.setGross(String.valueOf(item.get("totalWeight")));
                    saveWeighbridge.setTare(String.valueOf(item.get("carWeight")));
                    saveWeighbridge.setTareWeightingTime(tareWeightingTime);
                    saveWeighbridge.setCarNumber(String.valueOf(item.get("plateNumber")));
                    saveWeighbridge.setNetWeight(String.valueOf(item.get("carWeight")));
                    saveWeighbridge.setRawData(weighbridge.toString());
                    saveWeighbridge.setMaterialName(materialName);
                    saveWeighbridge.setGrossWeightingTime(dataItem.getString("grossWeightingTime"));
                    saveWeighbridge.setRawDataId(rawDataId);
                    weighbridgeService.insertWeighbridge(saveWeighbridge);
                } else {
                    weighbridge.setTareWeightingTime(tareWeightingTime);
                    weighbridgeService.updateWeighbridge(weighbridge);
                }
                //执行推送
                push(item);
            }
        }
        return AjaxResult.success();
    }


    private void push(Map<String, Object> item) {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", "weighbridge-YJBH-SSZGX_GQ-08");//待确认
        swzkParam.put("dataType", "200100028"); //地磅
        swzkParam.put("deviceType", "2001000075"); //智能地磅
        swzkParam.put("workAreaCode", "YJBH-SSZGX_GQ-08"); //工区编码待确认
        swzkParam.put("deviceName", "砂石料厂出口地磅"); //设备名称
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType", "car-load");
        profile.put("modelId", "3053");
        profile.put("poiCode", "w20240624");
        profile.put("name", "地磅");
        profile.put("model", "地磅型号Y23670");
        profile.put("manufacture", "深圳市机械制造有限公司");
        profile.put("owner", "");
        profile.put("makeDate", "2020-05-22");
        profile.put("validYear", "2050-05-22");
        profile.put("status", "01");
        profile.put("installPosition", "安乐河生活营地门口");
        map.put("profile", profile);
        Map<String, Object> events = new HashMap<>();
        events.put("pass", item);
        map.put("events", events);
        valus.add(map);
        swzkParam.put("values", valus);
        swzkHttpUtils.pushIOT(swzkParam);


    }


    public static MaterialType getMaterialTypeByName(String name) {
        for (MaterialType type : MaterialType.values()) {
            if (type.getNames().contains(name)) {
                return type; // 返回匹配的 MaterialType
            }
        }
        return null; // 如果没有匹配到，返回 null
    }

    public String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Convert byte array to hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
