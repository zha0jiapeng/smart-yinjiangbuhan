package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.SwitchConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.basic.IotTspCopy;
import com.ruoyi.system.mapper.IotTspMapper;
import com.ruoyi.system.service.IotTspService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class IotTspServiceImpl extends ServiceImpl<IotTspMapper, IotTsp> implements IotTspService {
    @Autowired
    private RedisCache redisCache;
    @Resource
    private IotTspMapper iotTspMapper;

    @Override
    public List<IotTsp> queryByPage(IotTsp iotTsp) {
        return iotTspMapper.queryAllByLimit(iotTsp);
    }


    @Override
    public IotTspCopy queryByPageCopy(IotTspCopy iotTspCopy) {
        String testStartDate = "2023-05-22 15:56:13";
        String testEndDate = "2023-05-22 16:56:13";

        IotTspCopy iotTspCopyResp = new IotTspCopy();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -1);
        Date date = calendar.getTime();
        String startDate = format.format(date);

        //结束时间
        String endDate = format.format(new Date());
        List<IotTspCopy> iotTspCopies = null;

        Boolean aBoolean = redisCache.hasKey(SwitchConstants.TPS_1001);
        if (aBoolean) {
            iotTspCopies = iotTspMapper.queryAllList(startDate, endDate);
        } else {
            iotTspCopies = iotTspMapper.queryAllList(testStartDate, testEndDate);
        }

        if (CollectionUtils.isEmpty(iotTspCopies)) {
            return iotTspCopyResp;
        }

        IotTspCopy tspHeader = iotTspCopies.stream().max(Comparator.comparing(IotTspCopy::getCreatedDate)).orElse(new IotTspCopy());
        BeanUtils.copyProperties(tspHeader, iotTspCopyResp);

        List<IotTspCopy.IotTspCopyDetails> iotTspCopyDetails = new ArrayList<>();
        IotTspCopy.IotTspCopyDetails pm25 = new IotTspCopy.IotTspCopyDetails();
        pm25.setName("pm2.5");
        List<String> pm25Key = new ArrayList<>();
        List<String> pm25Value = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                pm25Key.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                pm25Value.add(tspCopy.getPmTwoFive());
            }
        }
        pm25.setKey(pm25Key);
        pm25.setValue(pm25Value);
        iotTspCopyDetails.add(pm25);

        IotTspCopy.IotTspCopyDetails pm10 = new IotTspCopy.IotTspCopyDetails();
        pm10.setName("pm10");
        List<String> pm10Key = new ArrayList<>();
        List<String> pm10Value = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                pm10Key.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                pm10Value.add(tspCopy.getPmTen());
            }
        }
        pm10.setKey(pm10Key);
        pm10.setValue(pm10Value);
        iotTspCopyDetails.add(pm10);

        IotTspCopy.IotTspCopyDetails tsp = new IotTspCopy.IotTspCopyDetails();
        tsp.setName("tsp");
        List<String> tspKey = new ArrayList<>();
        List<String> tspValue = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                tspKey.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                tspValue.add(tspCopy.getTsp());
            }
        }
        tsp.setKey(tspKey);
        tsp.setValue(tspValue);
        iotTspCopyDetails.add(tsp);

        IotTspCopy.IotTspCopyDetails temperature = new IotTspCopy.IotTspCopyDetails();
        temperature.setName("湿度");
        List<String> temperatureKey = new ArrayList<>();
        List<String> temperatureValue = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                temperatureKey.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                temperatureValue.add(tspCopy.getTemperature());
            }
        }
        temperature.setKey(temperatureKey);
        temperature.setValue(temperatureValue);
        iotTspCopyDetails.add(temperature);

        IotTspCopy.IotTspCopyDetails windSpeed = new IotTspCopy.IotTspCopyDetails();
        windSpeed.setName("风速");
        List<String> windSpeedKey = new ArrayList<>();
        List<String> windSpeedValue = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                windSpeedKey.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                windSpeedValue.add(tspCopy.getWindSpeed());
            }
        }
        windSpeed.setKey(windSpeedKey);
        windSpeed.setValue(windSpeedValue);
        iotTspCopyDetails.add(windSpeed);

        IotTspCopy.IotTspCopyDetails windDirection = new IotTspCopy.IotTspCopyDetails();
        windDirection.setName("风向");
        List<String> windDirectionKey = new ArrayList<>();
        List<String> windDirectionValue = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                windDirectionKey.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                windDirectionValue.add(tspCopy.getWindDirection());
            }
        }
        windDirection.setKey(windDirectionKey);
        windDirection.setValue(windDirectionValue);
        iotTspCopyDetails.add(windDirection);

        IotTspCopy.IotTspCopyDetails noise = new IotTspCopy.IotTspCopyDetails();
        noise.setName("噪音");
        List<String> noiseKey = new ArrayList<>();
        List<String> noiseValue = new ArrayList<>();
        for (int i = 0; i < iotTspCopies.size(); i++) {
            if (i % 3 == 0) {
                IotTspCopy tspCopy = iotTspCopies.get(i);
                noiseKey.add(new SimpleDateFormat("HH:mm:ss").format(tspCopy.getCreatedDate()));
                noiseValue.add(tspCopy.getNoise());
            }
        }
        noise.setKey(noiseKey);
        noise.setValue(noiseValue);
        iotTspCopyDetails.add(noise);

        iotTspCopyResp.setIotTspCopyDetails(iotTspCopyDetails);
        return iotTspCopyResp;
    }

    @Override
    public List<Map<String, Object>> getCurve(Integer type, String text) {
        return iotTspMapper.getCurve(type,text);
    }

}



















