package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.utils.Result;

import java.util.Map;

public interface AutomaticDetectionService {


     /**
      * 获取最近60分钟内的测量数据
      * @param urlSuffix
      * @param paramMap
      * @return
      * @throws Exception
      */
     void getLatestDataByModule(String urlSuffix, Map<String, String> paramMap) throws Exception;

     /**
      * 获取最新一次的预警数据
      * @param urlSuffix
      * @param paramMap
      * @return
      */
     void getLatestPointAlarmStatistical(String urlSuffix,Map<String, String> paramMap) throws Exception;

     void getPointAlarmStatisticalByTime(Result<JSONObject> result, Map<String, String> paramMap)throws Exception;

     Result<JSONObject> requestWhmosInterface(String urlSuffix, Map<String, String> paramMap) throws Exception;
}
