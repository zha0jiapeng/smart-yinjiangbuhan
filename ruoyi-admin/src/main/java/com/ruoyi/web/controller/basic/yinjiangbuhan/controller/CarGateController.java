package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.service.CarAccessService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/car")
public class CarGateController {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Autowired
    private CarAccessService carAccessService;


    @PostMapping("/carAccess")
    public Map<String,Object> carAccess(@RequestParam Map<String,Object> request) {
       // log.info("carAccess:{}",JSON.toJSONString(request));
        Object type = request.get("type");
        if(!"online".equals(type.toString())) return null;

        CarAccess carAccess = new CarAccess();
        carAccess.setCarCode(request.get("plate_num").toString());
        if("in".equals(request.get("vdc_type"))){
            carAccess.setCarInDate(new Date());
        }else{
            carAccess.setCarOutDate(new Date());
        }
        carAccess.setCreatedDate(new Date());
        carAccess.setModifyDate(new Date());
        carAccess.setYn(YnEnum.Y.getCode());
        carAccessService.insert(carAccess);
        pushCarAccess(request);
        return request;
    }

    private void pushCarAccess(Map<String, Object> request) {
        // Root map.
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("deviceType", "2001000011");
        rootMap.put("SN", "in".equals(request.get("vdc_type"))?"DSC101DKDZ001":"DSC101DKDZ002");
        rootMap.put("dataType", "200300004");
        rootMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        rootMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        rootMap.put("deviceName", "8#洞车辆门禁");

        // Values list with one item
        Map<String, Object> valuesItem = new HashMap<>();
        valuesItem.put("reportTs", DateUtil.current());

        // Profile map
        Map<String, Object> profile = new HashMap<>();
        profile.put("appType", "parking");
        profile.put("modelId", "2054");
        profile.put("poiCode", "w0708003");
        profile.put("name", "车辆门禁");
        profile.put("model", "Y500");
        profile.put("manufacture", "机械制造公司");
        profile.put("owner", "江汉水网集团");
        profile.put("makeDate", "2020-05-22");
        profile.put("validYear", "2050");
        profile.put("state", "01");
        profile.put("installPosition", "出口段隧洞口100米处");
        profile.put("x", 0);
        profile.put("y", 0);
        profile.put("z", 0);
        valuesItem.put("profile", profile);

        // Properties map (empty)
        Map<String, Object> properties = new HashMap<>();
        valuesItem.put("properties", properties);

        // Events map with pass event
        Map<String, Object> events = new HashMap<>();
        Map<String, Object> pass = new HashMap<>();
        pass.put("eventType", 1);
        pass.put("eventTs", DateUtil.current());
        pass.put("describe", "");
        pass.put("plateNumber", request.get("plate_num"));
        pass.put("passTime", DateUtil.now());
        pass.put("passDirection", "in".equals(request.get("vdc_type")) ? "02" : "01");
        String picture = request.get("picture").toString().replaceAll("\\-", "\\+")
                .replaceAll("\\_", "\\/").replaceAll("\\.", "\\=");
        log.info("base64:{}",picture);
        // Decode the Base64 string to a byte array
        byte[] imageBytes = Base64.getDecoder().decode(picture);
        String imageName = DateUtil.format(DateUtil.date(), "yyyyMMdd")+"/"+request.get("start_time")+".jpg";
        // Define the path where the image will be saved
        String imagePath = "/home/user/carAccessImg/"+imageName;

        // Write the byte array to a file
        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }

        pass.put("passPic", "http://192.168.1.154:8097/image/"+imageName);
        events.put("pass", pass);
        valuesItem.put("events", events);

        // Services map (empty)
        Map<String, Object> services = new HashMap<>();
        valuesItem.put("services", services);

        // Adding valuesItem to the values list
        rootMap.put("values", new Map[]{valuesItem});

        // Now rootMap contains the structured data
        swzkHttpUtils.pushIOT(rootMap);

    }

}
