package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.IotTbm;
import com.ruoyi.system.service.IotTbmService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysVentilatorMonitor;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysVentilatorMonitorService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@RestController()
@RequestMapping("/tbm")
@Slf4j
public class TBMController {


    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Resource
    IotTbmService tbmService;

    @Autowired
    private ISysVentilatorMonitorService sysVentilatorMonitorService;

    @RequestMapping("/getTbm6")
    public AjaxResult getTbm6(){
        IotTbm tbm = tbmService.getOne(new LambdaQueryWrapper<IotTbm>().eq(IotTbm::getDeviceCode, "CREC1463").orderByDesc(IotTbm::getCreatedDate), false);
        return AjaxResult.success(tbm);
    }

    @RequestMapping("/getTbm5")
    public AjaxResult getTbm5(){
        IotTbm tbm = tbmService.getOne(new LambdaQueryWrapper<IotTbm>().eq(IotTbm::getDeviceCode, "CREC1463").orderByDesc(IotTbm::getCreatedDate), false);
        return AjaxResult.success(tbm);
    }

    @RequestMapping("/getBigScreen")
    public AjaxResult get() {
        Map<String,Object> map = new HashMap<>();
        String token = getToken();
        String url = "https://tmain.tbmcloud.com.cn/#/screen";
        map.put("url",url);
        map.put("vue_tbmcloud_template_token",token);
        return AjaxResult.success(map);
    }



    private static final String url = "https://trans.tbmcloud.com.cn/trans/transGetRealData/ex/getMappingData";

    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        Map<String,Object> request = new HashMap<>();
        request.put("secretKey","z7kZ9eEsFXfIEDsCXfNCrI2coYPYkIawOWIScmi0gC1ywtVm0uKCmQgye+xuRnnWhhAjAs3AUe6F547z1FgkbQ==");
        request.put("tbmCode","CREC1463");
        request.put("name","shibajukjb");
        HttpResponse execute = HttpRequest.post(url)
                .body(JSONObject.toJSONString(request))
                .execute();
        System.out.println(execute.body());
        JSONObject result = JSONObject.parseObject(execute.body());
        JSONObject data = result.getJSONObject("data");

        zuzhuang(data);
    }



    public void zuzhuang(JSONObject jsonObject){

        String timeStr = jsonObject.getString("time_str");
        String tbmCode = jsonObject.getString("tbmCode");
        Long timestamp = jsonObject.getLong("timestamp");
        JSONArray array = jsonObject.getJSONArray("values");
        Map<String, Map<String, Object>> resultMap = array.stream()
                .map(obj -> (JSONObject) obj) // 将每个元素转换为 JSONObject
                .collect(Collectors.toMap(
                        jsonObj -> jsonObj.getString("key"), // 以 "key" 作为 Map 的键
                        JSONObject::getInnerMap             // 将 JSONObject 转为 Map 作为值
                ));


        IotTbm tbm = new IotTbm();
        tbm.setDeviceCode(tbmCode);
        tbm.setCutterTorque(resultMap.get("cutterTorque").get("val").toString());
        tbm.setCutterRev(resultMap.get("cutterRpm").get("val").toString());
        tbm.setStatus(Integer.parseInt(resultMap.get("shieldState").get("val").toString()));
        tbm.setTotalThrustOne(resultMap.get("thrust").get("val").toString());
        tbm.setTotalThrustTwo(resultMap.get("advnPrs").get("val").toString());
        tbm.setCreatedDate(new Date());
        tbmService.save(tbm);


        //气体检测
        pushGasMonitor(resultMap);

        //气体检测
        pushGasMonitor2(resultMap);

        //气体检测
        pushGasMonitor3(resultMap);

        //风机
        saveVentilator(resultMap);
    }

    private void pushGasMonitor(Map<String, Map<String, Object>> resultMap) {
        Map<String, Object> item = new HashMap<>();
       // item.put("dust", dust.doubleValue());
        item.put("o2", resultMap.get("mainBeltO2").get("val"));
        item.put("ch4", resultMap.get("mainBeltCH4").get("val"));
        item.put("co", resultMap.get("mainBeltCO").get("val"));
        item.put("h2s", resultMap.get("mainBeltH2S").get("val"));
        item.put("co2", resultMap.get("mainBeltCO2").get("val"));
        item.put("so2", resultMap.get("mainBeltSO2").get("val"));
        item.put("no2", resultMap.get("mainBeltNO2").get("val"));
        item.put("no", resultMap.get("mainBeltNO").get("val"));
        item.put("cl2", resultMap.get("mainBeltCL2").get("val"));
        push(item,"主皮带区","tbm6_mainBelt");
    }

    private void pushGasMonitor2(Map<String, Map<String, Object>> resultMap) {
        Map<String, Object> item = new HashMap<>();
        item.put("ch4", resultMap.get("mainCH4").get("val"));
        item.put("co", resultMap.get("mainCO").get("val"));
        item.put("h2s", resultMap.get("mainH2S").get("val"));
        push(item,"主机","tbm6_main");
    }

    private void pushGasMonitor3(Map<String, Map<String, Object>> resultMap) {
        Map<String, Object> item = new HashMap<>();
        item.put("ch4", resultMap.get("trailerCH4").get("val"));
        item.put("co", resultMap.get("trailerCO").get("val"));
        item.put("h2s", resultMap.get("trailerH2S").get("val"));
        push(item,"拖车尾部","tbm6_trailer");
    }
    private void push(Map<String, Object> item,String name,String sn) {
        List<Object> valus = new ArrayList<>();
        Map<String, Object> swzkParam = new HashMap<String, Object>();
        swzkParam.put("SN", sn);
        swzkParam.put("dataType","200300025"); //有毒有害气体
        swzkParam.put("deviceType","2001000060"); //有毒有害气体
        swzkParam.put("workAreaCode","YJBH-SSZGX_GQ-08"); //鸡冠河
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> profile = new HashMap<>();
        map.put("reportTs", DateUtil.currentSeconds());
        profile.put("appType","environment");
        profile.put("modelId","2055");
        profile.put("poiCode","w0907001");
        profile.put("name", name);
        profile.put("model","");
        profile.put("manufacture","");
        profile.put("owner","");
        profile.put("makeDate","2024-06-25");
        profile.put("validYear","2024-06-25");
        profile.put("status","01");
        profile.put("installPosition",name);
        profile.put("x","0");
        profile.put("y","0");
        profile.put("z","0");
        map.put("profile", profile);
        Map<String,Object> properties = new HashMap<>();
        properties.put("monitorTime",DateUtil.now());

        properties.put("CO",item.get("co"));
        if (item.get("co2")!=null) properties.put("CO2",new BigDecimal(item.get("co2").toString()).multiply(new BigDecimal(10000)).setScale(0,BigDecimal.ROUND_HALF_UP));
        properties.put("SO2",item.get("so2"));
        properties.put("SO", item.get("so"));
        properties.put("CH4", item.get("ch4"));
        properties.put("O2",item.get("o2"));
        properties.put("S2H",item.get("h2s"));
        properties.put("TEMPERATURE",item.get("temp"));
        properties.put("HUMIDNESS",item.get("humi"));
        properties.put("location","1");
        properties.put("x","0");
        properties.put("y","0");
        properties.put("z","0");
        map.put("properties",properties);
        map.put("events",new Object());
        map.put("services",new Object());

        valus.add(map);
        swzkParam.put("values",valus);

        swzkHttpUtils.pushIOT(swzkParam);
    }


    private void saveVentilator(Map<String, Map<String, Object>> resultMap) {
        String now = DateUtil.now();
        SysVentilatorMonitor ventilatorMonitor = new SysVentilatorMonitor();
        ventilatorMonitor.setSn("tbm6_ventilator");
        ventilatorMonitor.setDeviceId(91L);
        ventilatorMonitor.setYn(1L);
        Integer funIsOn1 = (Integer) resultMap.get("funIsOn1").get("val");
        Integer funIsOn2 = (Integer) resultMap.get("funIsOn2").get("val");
        ventilatorMonitor.setIsOpen(funIsOn1==1 || funIsOn2==1);
        ventilatorMonitor.setPower(new BigDecimal(resultMap.get("funPower1").get("val").toString()));
        ventilatorMonitor.setDiameter(1250L);
        ventilatorMonitor.setSpeed(new BigDecimal(resultMap.get("funRotateSpeed1").get("val").toString()));
        ventilatorMonitor.setAirSupply(new BigDecimal( resultMap.get("funAirOutput").get("val").toString()));
        ventilatorMonitor.setCreatedDate(new Date());
        sysVentilatorMonitorService.save(ventilatorMonitor);

        // 最外层 Map
        Map<String, Object> data = new HashMap<>();

        // 填充基本信息
        data.put("deviceType", "2001000050");
        data.put("SN", "tbm6_ventilator");
        data.put("dataType", "200300020");
        data.put("bidCode", "YJBH-SSZGX_BD-SG-201");
        data.put("workAreaCode", "YJBH-SSZGX_GQ-01");
        data.put("deviceName", "通风机监测设备名称");

        // values 列表
        List<Map<String, Object>> valuesList = new ArrayList<>();
        Map<String, Object> valueItem = new HashMap<>();

        // reportTs
        valueItem.put("reportTs", 1572702827618L);

        // profile 子对象
        Map<String, Object> profile = new HashMap<>();
        profile.put("appType", "ventilator");
        profile.put("modelId", "2090");
        profile.put("poiCode", "w0823001");
        profile.put("name", "TBM6通风机");
        profile.put("model", "测试型号");
        profile.put("manufacture", "中铁装备制造有限公司");
        profile.put("owner", "江汉水网公司");
        profile.put("makeDate", "2020-05-22");
        profile.put("validYear", "2050-05-22");
        profile.put("installPosition", "出口段隧洞口100米处");
        profile.put("x", 0);
        profile.put("y", 0);
        profile.put("z", 0);
        valueItem.put("profile", profile);

        // properties 子对象
        Map<String, Object> properties = new HashMap<>();
        properties.put("monitorTime", now);
        properties.put("is_on", ventilatorMonitor.getIsOpen()?1:0);
        properties.put("power", ventilatorMonitor.getPower());
        properties.put("diameter", new BigDecimal(ventilatorMonitor.getDiameter()).divide(new BigDecimal(1000),0,BigDecimal.ROUND_HALF_UP));
        properties.put("rotate_speed", ventilatorMonitor.getSpeed());
        properties.put("air_output", ventilatorMonitor.getAirSupply());
        properties.put("wind_pressure", ventilatorMonitor.getWindPresssure());
        valueItem.put("properties", properties);

        // events 和 services
        valueItem.put("events", new HashMap<>());
        valueItem.put("services", new HashMap<>());

        // 添加到 values 列表
        valuesList.add(valueItem);

        // 将 values 列表加入最外层 Map
        data.put("values", valuesList);
        swzkHttpUtils.pushIOT(data);

    }

    @Autowired
    RedisCache redisCache;

    @Value("${em.path}")
    String rootPath;

    @Value("${em.python}")
    String pythonPath;

    String getCaptcha() {
        log.info("======getCaptcha=======");
        String url = "https://tmain.tbmcloud.com.cn/tbmcenter/sys/captchaImage";
        try (HttpResponse resp = HttpUtil.createGet(url).execute()) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(resp.body());
                JSONObject data = jsonObject.getJSONObject("data");
                String img = data.getString("img").replaceAll("\"", "");
                String uuid = data.getString("uuid");
                byte[] imageBytes = Base64.getDecoder().decode(img);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(bis);
                bis.close();
                File outputFile = new File(rootPath+"/tbm6_captcha.png");
                ImageIO.write(image, "png", outputFile);

                String captcha = callPaddleOCR(rootPath + "/tbm6_captcha.png");
                captcha = captcha.substring(captcha.lastIndexOf("\n")).trim();
                log.info("======output:{}=======",captcha);

                if(!captcha.contains("=")){
                    getCaptcha();
                }
                String[] split = captcha.split("\\=");
                captcha = split[0]+"=?";
                if( !captcha.contains("+")&&!captcha.contains("-")&&!captcha.contains("*")&&!captcha.contains("/")){
                    getCaptcha();
                }
               // log.info("captcha: {}", captcha);
                int calculate = calculate(captcha);
                log.info("======calculate:{}=======",calculate);
                return calculate +","+uuid;
            } catch (Exception e) {
                //log.warn(e.getMessage(), e);
            }
        }
        return StringUtils.EMPTY;
    }


    public String callPaddleOCR(String imagePath) throws Exception {
        // Python 脚本路径
        String pythonScriptPath = rootPath + "/ocrr.py";  // 替换为实际路径
        // 构建命令
        ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScriptPath, imagePath);

        // 启动进程
        Process process = processBuilder.start();

        // 读取 Python 脚本的标准输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }

        // 等待脚本执行完成
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python script exited with error code: " + exitCode);
        }

        return result.toString().trim();
    }


        /**
         * 计算数学表达式并返回结果
         *
         * @param expression 数学表达式，例如 "9 - 3 = ?"
         * @return 计算结果
         * @throws IllegalArgumentException 如果表达式格式不正确
         */
        public static int calculate(String expression) throws IllegalArgumentException {
            // 去掉空格
            expression = expression.replaceAll("\\s", "");

            // 检查表达式是否符合格式：A 操作符 B = ?
            if (!expression.matches("\\d+[\\+\\-\\*/]\\d+=\\?")) {
                throw new IllegalArgumentException("Invalid expression format. Expected format: 'A + B = ?'");
            }

            // 分离操作数和操作符
            String[] parts = expression.split("[\\+\\-\\*/=]");
            int operand1 = Integer.parseInt(parts[0]); // 第一个操作数
            int operand2 = Integer.parseInt(parts[1]); // 第二个操作数
            char operator = expression.charAt(parts[0].length()); // 操作符

            // 根据操作符计算结果
            int result;
            switch (operator) {
                case '+':
                    result = operand1 + operand2;
                    break;
                case '-':
                    result = operand1 - operand2;
                    break;
                case '*':
                    result = operand1 * operand2;
                    break;
                case '/':
                    if (operand2 == 0) {
                        throw new IllegalArgumentException("Division by zero is not allowed.");
                    }
                    result = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
            return result;
        }


    String getToken() {
        String token = redisCache.getCacheObject("Tbm6Token");
        if (StringUtils.isBlank(token)) {
            token = refreshToken();
        }
        return token;
    }

    String refreshToken() {
        log.info("======refreshToken=======");
        String token = "";
        try {
            token = getLoginToken();

            redisCache.setCacheObject("Tbm6Token", token, 1, TimeUnit.DAYS);
        } catch (Exception e) {
            // log.warn(e.getMessage(), e);
        }
        return token;
    }

    String getLoginToken() {
        log.info("======getLoginToken=======");
        String token = "";
        String captcha = getCaptcha();
        String[] split = captcha.split(",");
        log.info("======split:{},{}=======",split[0],split[1]);
        String url = "https://tmain.tbmcloud.com.cn/tbmcenter/sys/loginByCaptcha";
        final HttpRequest req = HttpRequest.post(url);
        req.form("username", "yjbh06");
        req.form("password", "Yjbh06_2024@");
        req.form("uuid", split[1]);
        req.form("code",split[0]);
        HttpResponse execute = req.execute();
        String body = execute.body();
        log.info("======body:{}=======",body);
        token = JSON.parseObject(JSON.parse(body).toString()).getString("token");
        log.info("======token:{}=======",token);
        return token;
    }

}


