package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.*;
import com.ruoyi.system.pojo.vo.PointAlarmInfo;
import com.ruoyi.system.pojo.vo.PointAlarmStatisticalInfo;
import com.ruoyi.system.pojo.vo.PointAlarmStatisticalVO;
import com.ruoyi.system.pojo.vo.PointDataVO;
import com.ruoyi.system.service.AutomaticDetectionService;
import com.ruoyi.system.utils.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutomaticDetectionServiceImpl implements AutomaticDetectionService {
    private static final Logger log = LoggerFactory.getLogger(AutomaticDetectionServiceImpl.class);

    private String urlPrefix = "http://www.whmos.cn/api/Data/";
    private String key = "d15c4f7afec24ba49ec952956057dbe8";

    @Resource
    private RedisCache redisCache;

    @Override
    public void getLatestDataByModule(String urlSuffix, Map<String, String> paramMap) throws Exception {
        log.info("----------开始获取最后一次的点测量数据，存redis-----------");
        Result<JSONObject> result = requestWhmosInterface(urlSuffix, paramMap);
        if(result.isSuccess()) {
            JSONObject jsonObject = result.getResult();
            List<PointDataVO> data = jsonObject.getJSONArray("Data").toJavaList(PointDataVO.class);
            PointDataVO pointData = new PointDataVO();
            if (CollectionUtils.isEmpty(data)) {
                pointData.setNewData("false");
                data = Lists.newArrayList();
                data.add(pointData);
                log.info("---------请求的数据data 为空-----------");
                return;
            }
            redisCache.deleteObject(CacheConstants.BSL_POINT_DATA + "NEW");
            redisCache.setCacheList(CacheConstants.BSL_POINT_DATA + "NEW", data);
            log.info("----------结束获取最后一次的点测量数据-----------");
        }
    }

    @Override
    public void getLatestPointAlarmStatistical(String urlSuffix, Map<String, String> paramMap)throws Exception {
        log.info("----------开始获取最新一次的预警统计信息，存redis-----------");
        Result<JSONObject> result = requestWhmosInterface(urlSuffix, paramMap);
        if(result.isSuccess()) {
            JSONObject jsonObject = result.getResult();
            List<PointAlarmStatisticalInfo> dataList = jsonObject.getJSONArray("Data").toJavaList(PointAlarmStatisticalInfo.class);
            if (CollectionUtils.isEmpty(dataList)) {
                return;
            }
            //获取最近24小时内的数据后取最新一次测量数据的数据
            PointAlarmStatisticalInfo pointAlarmStatisticalInfo = dataList.stream()
                    .sorted(Comparator.comparing(PointAlarmStatisticalInfo::getPlan_time).reversed()).collect(Collectors.toList()).get(0);
            //取最新一次测量数据的数据，有可能包含多个点组，需要合并一下
            if(StringUtils.isNotEmpty(pointAlarmStatisticalInfo.getPlan_time())){
                List<PointAlarmStatisticalInfo> pointAlarmStatisticalInfoList = dataList.stream().filter(info -> pointAlarmStatisticalInfo.getPlan_time().equals(info.getPlan_time())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(pointAlarmStatisticalInfoList)&&pointAlarmStatisticalInfoList.size()>1){
                    //需要合并预警信息和正常信息
                    List<PointAlarmInfo> alarmStatisticalInfoList = Lists.newArrayList();
                    List<PointDataVO> pointDataVOList = Lists.newArrayList();
                    for (PointAlarmStatisticalInfo alarmStatisticalInfo :pointAlarmStatisticalInfoList) {
                        alarmStatisticalInfoList.addAll(alarmStatisticalInfo.getAlarm_info());
                        pointDataVOList.addAll(alarmStatisticalInfo.getPoint_data());
                    }
                    pointAlarmStatisticalInfo.setAlarm_info(alarmStatisticalInfoList);
                    pointAlarmStatisticalInfo.setPoint_data(pointDataVOList);
                }
            }
            //正常数据和预警数据是分开的,前端需要一起展示所以要合并一下两个数据
            List<PointDataVO> pointDataList = pointAlarmStatisticalInfo.getPoint_data();
            if(CollectionUtils.isEmpty(pointAlarmStatisticalInfo.getAlarm_info())){
                pointAlarmStatisticalInfo.setAlarm_info(Lists.newArrayList());
            }
            if(CollectionUtils.isNotEmpty(pointDataList)){
                for (PointDataVO pointData:pointDataList) {
                    PointAlarmInfo pointAlarmInfo = new PointAlarmInfo();
                    pointAlarmInfo.setPoint_id(pointData.getPointId());
                    pointAlarmInfo.setPoint_name(pointData.getPointName());
                    pointAlarmInfo.setAlarm_type(0);
                    pointAlarmStatisticalInfo.getAlarm_info().add(pointAlarmInfo);
                }
            }
            redisCache.deleteObject(CacheConstants.BSL_ALARM_DATA+"PointAlarmStatisticalInfo");
            redisCache.setCacheObject(CacheConstants.BSL_ALARM_DATA+"PointAlarmStatisticalInfo",pointAlarmStatisticalInfo);
            log.info("----------结束获取最新一次的预警统计信息-----------");
        }
    }

    /**
     * 按照查询类型统计红黄预警数据量
     * @param result
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public void getPointAlarmStatisticalByTime( Result<JSONObject> result, Map<String, String> paramMap)throws Exception {
        log.info("------------根据时间段统计预警信息--------");
        if(result.isSuccess()) {
            JSONObject jsonObject = result.getResult();
            List<PointAlarmInfo> dataList = jsonObject.getJSONArray("Data").toJavaList(PointAlarmInfo.class);
            List<PointAlarmStatisticalVO> alarmStatisticalVOList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(dataList)) {
                Map<String, List<PointAlarmInfo>> mapCollect;
                switch (paramMap.get("type")){
                    case "D":
                        mapCollect = dataList.stream()
                                .filter(alarmInfo->DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("startTime"))>=0
                                &&DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("endTime"))<=0)
                                .collect(Collectors.groupingBy(alarmInfo -> DateUtils.parseDateToStr("HH", alarmInfo.getData_date())));
                        alarmStatisticalVOList = dealDataWithType(mapCollect,"D",24);

                        break;
                    case "W":
                        //统计本周的
                        mapCollect = dataList.stream()
                                .filter(alarmInfo->DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("startTime"))>=0
                                &&DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("endTime"))<=0)
                                .collect(Collectors.groupingBy(alarmInfo -> alarmInfo.getData_date().getDay()+""));
                        alarmStatisticalVOList = dealDataWithType(mapCollect,"W",7);
                        break;
                    case "M":
                        //统计本月的
                        mapCollect = dataList.stream()
                                .filter(alarmInfo->DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("startTime"))>=0
                                &&DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,alarmInfo.getData_date()).compareTo(paramMap.get("endTime"))<=0)
                                .collect(Collectors.groupingBy(alarmInfo -> DateUtils.parseDateToStr("dd", alarmInfo.getData_date())));
                        Calendar calendar = Calendar.getInstance();
                        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        alarmStatisticalVOList =dealDataWithType(mapCollect,"M",daysInMonth);
                        break;
                    case "Y":
                        //统计本年的
                        mapCollect = dataList.stream().collect(Collectors.groupingBy(alarmInfo ->DateUtils.parseDateToStr("MM", alarmInfo.getData_date())));
                        alarmStatisticalVOList = dealDataWithType(mapCollect,"Y",12);
                        break;
                }
                redisCache.deleteObject(CacheConstants.BSL_ALARM_DATA+"AlarmStatisticalVOList:"+paramMap.get("type"));
                redisCache.setCacheList(CacheConstants.BSL_ALARM_DATA+"AlarmStatisticalVOList:"+paramMap.get("type"),alarmStatisticalVOList);
            }
        }
    }

    /**
     * 将数据封装到实体
     * @param mapCollect
     * @param type
     * @param cycleNum
     */
    private  List<PointAlarmStatisticalVO> dealDataWithType(Map<String, List<PointAlarmInfo>> mapCollect,String type,int cycleNum) {
        List<PointAlarmStatisticalVO> alarmStatisticalVOList = Lists.newArrayList();
        for(int i = 0;i<cycleNum;i++){
            PointAlarmStatisticalVO pointAlarmStatisticalVO = new PointAlarmStatisticalVO();
            //页面横坐标显示的值
            String key = "";
            //方便计算
            int k= i+1;
            //根据这个值从map中取数据
            String gk = k+"";
            switch (type){
                case "D":
                    key = i+"";
                    if(i<10){
                        gk = "0"+i;
                    }else{
                        gk = key;
                    }
                    break;
                case "W":
                    //统计本周的
                    key = DateUtils.getWeekdayName(k);
                    break;
                case "M":
                    //统计本月的
                    if(k<10){
                        gk = "0"+gk;
                    }
                    key = k+"日";
                    break;
                case "Y":
                    //统计本年的
                    //统计本月的
                    key = k+"月";
                    break;
            }
            pointAlarmStatisticalVO.setKey(key);
            if(mapCollect.get(gk)!=null){
                Long yellowCount = mapCollect.get(gk).stream().filter(vo -> vo.getAlarm_type() == 1).collect(Collectors.counting());
                Long redCount = mapCollect.get(gk).stream().filter(vo -> vo.getAlarm_type() == 2).collect(Collectors.counting());
                pointAlarmStatisticalVO.setYellowVal(Math.toIntExact(yellowCount));
                pointAlarmStatisticalVO.setRedVal(Math.toIntExact(redCount));
            }else{
                pointAlarmStatisticalVO.setRedVal(0);
                pointAlarmStatisticalVO.setYellowVal(0);
            }
            alarmStatisticalVOList.add(pointAlarmStatisticalVO);
        }
        return alarmStatisticalVOList;
    }


    /**
     * 请求其它系统接口（交叉工程）
     *
     * @param urlSuffix
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Result<JSONObject> requestWhmosInterface(String urlSuffix, Map<String, String> paramMap) throws Exception {
        String url = urlPrefix + urlSuffix;
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Key", key);

        long start = System.currentTimeMillis(); // 记录开始时间
        log.info("交叉工程接口请求地址'{}',参数{}.", url, JSONObject.toJSONString(paramMap));
        HttpClientResult httpClientResult = HttpClientUtils.doGet(url, headMap, paramMap);
        log.info("交叉工程接口请求结果'{}',返回码{}.", httpClientResult.getContent(), httpClientResult.getCode());
        long end = System.currentTimeMillis(); // 记录结束时间
        long duration = end - start; // 计算持续时间
        System.out.println("接口请求时长：" + duration + "ms");


        if (httpClientResult.getCode() == 200) {
            JSONObject jsonObject = JSON.parseObject(httpClientResult.getContent());
            //请求成功
            int code = (int) jsonObject.get("Code");
            String message = (String) jsonObject.get("Message");
            if (code == 1) {
                return Result.OK(jsonObject);
            } else if (code == 0) {
                log.info("交叉工程接口调用识别请求地址'{}',返回信息{}", url, jsonObject);
                return Result.error("接口调用失败");
            } else {
                log.info("交叉工程接口调用识别请求地址'{}',返回信息{}", url, jsonObject);
                return Result.error(message);
            }
        }
        return Result.error("服务异常，请稍后再试");
    }


}
