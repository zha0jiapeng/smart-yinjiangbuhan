package com.ruoyi.web.controller.basic.view.whmos;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.pojo.vo.PointAlarmStatisticalInfo;
import com.ruoyi.system.pojo.vo.PointAlarmStatisticalVO;
import com.ruoyi.system.pojo.vo.PointDataVO;
import com.ruoyi.system.service.AutomaticDetectionService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 交叉工程
 */
@Slf4j
@RestController
@RequestMapping("automatic/detection")
public class AutomaticDetectionController extends BaseController {

    @Autowired
    private AutomaticDetectionService automaticDetectionService;

    //深圳塘朗山项目Code
    private String projectCode = "91d54218-eb97-4be5-b799-39d1e176062c";
    @Resource
    private RedisCache redisCache;
    /**
     * 获取位移最新测量数据 (60分钟内)
     * @param name
     * @return
     * @throws Exception
     */
   @GetMapping("/getLatestDataByModule")
    public AjaxResult getLatestDataByModule(@RequestParam String name) throws Exception {
       if(StringUtils.isBlank(name)){
           return AjaxResult.error("参数不能为空");
       }
       List<PointDataVO> data = redisCache.getCacheList(CacheConstants.BSL_POINT_DATA + "NEW");
       String pointName = "";
       List<PointDataVO> pointDataVOList;
       PointDataVO pointData = new PointDataVO();
       if(CollectionUtils.isEmpty(data)){
           pointData.setNewData("false");
           log.info("---------请求的数据data 为空-----------");
           return  AjaxResult.success(pointData);
       }
       if(name.contains("YD")){
           pointName = name.replace("YD","").replace("_",".");
           String finalPointName = "Y"+pointName;
           //根据点名称过滤，根据时间倒序排序
           pointDataVOList = data.stream().filter(pointDataVO -> finalPointName.equals(pointDataVO.getPointName()) && "右线".equals(pointDataVO.getPgroupName())).sorted(Comparator.comparing(PointDataVO::getPlanTime).reversed())
                   .collect(Collectors.toList());
       }else if(name.contains("ZD")){
           pointName = name.replace("ZD","").replace("_",".");
           String finalPointName = pointName;
           //根据点名称过滤，根据时间倒序排序
           pointDataVOList = data.stream().filter(pointDataVO -> finalPointName.equals(pointDataVO.getPointName()) && "塘朗山左".equals(pointDataVO.getPgroupName())).sorted(Comparator.comparing(PointDataVO::getPlanTime).reversed())
                   .collect(Collectors.toList());
       }else{
           return AjaxResult.error("点名称不符合规则");
       }
       if(CollectionUtils.isNotEmpty(pointDataVOList)){
           //取时间最新的一条数据
           pointData = pointDataVOList.get(0);
       }else{
           pointData.setNewData("false");
       }
       return AjaxResult.success(pointData);

    }

    /**
     * 获取最新一次测量数据的预警统计
     * @return
     */
    @GetMapping("/getLatestPointAlarmStatistical")
    public AjaxResult getLatestPointAlarmStatistical() throws Exception {
        PointAlarmStatisticalInfo pointAlarmStatisticalInfo =
                redisCache.getCacheObject(CacheConstants.BSL_ALARM_DATA + "PointAlarmStatisticalInfo");
        return AjaxResult.success(pointAlarmStatisticalInfo);
    }

    /**
     * 根据时间段统计预警信息
     * @return
     */
    @GetMapping("/getPointAlarmStatisticalByTime")
    public AjaxResult getPointAlarmStatisticalByTime(@RequestParam String type) throws Exception {

        if(StringUtils.isBlank(type)||!"DWMY".contains(type)){
            return AjaxResult.error("参数不合法");
        }
        //直接从redis取数据
        List<PointAlarmStatisticalVO> alarmStatisticalVOList = redisCache.getCacheList(CacheConstants.BSL_ALARM_DATA +"AlarmStatisticalVOList:"+ type);
        return AjaxResult.success(alarmStatisticalVOList);
    }

    /**
     * 根据类型判断日期参数
     * @param type
     * @param paramMap
     * @return
     */
    private void getDateParam(String type, Map<String, String> paramMap) {
        switch (type){
            case "D":
                //统计今日的，按小时统计
                paramMap.put("startTime", DateUtils.getDoStartDay());
                paramMap.put("endTime",DateUtils.getDoEndDay());
                break;
            case "W":
                //统计本周的
                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfWeek()));
                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfWeek()));
                break;
            case "M":
                //统计本月的
                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfMonth()));
                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfMonth()));
                break;
            case "Y":
                //统计本年的
                paramMap.put("startTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getBeginDayOfYear()));
                paramMap.put("endTime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateToUtils.getEndDayOfYear()));
                break;
        }
    }

}
