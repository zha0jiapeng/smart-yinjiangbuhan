package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 设备Controller
 * 
 * @author mashir0
 * @date 2024-06-23
 */
@RestController
@RequestMapping("/door")
public class DoorController extends BaseController
{

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public JSONObject list(String code,String startTime,String endTime,Integer pageNo, Integer pageSize)
    {
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
        EventsRequest eventsRequest = new EventsRequest(); //查询门禁事件
        eventsRequest.setPageNo(pageNo); // 显示最后一个人
        eventsRequest.setPageSize(pageSize);
        eventsRequest.setStartTime(DoorEvent.getISO8601TimestampFromDateStr(startTime));
        eventsRequest.setEndTime(DoorEvent.getISO8601TimestampFromDateStr(endTime));
        ArrayList<String> indexcodList = new ArrayList<>();
        indexcodList.add(code);
        eventsRequest.setDoorIndexCodes(indexcodList); // 所有门禁标识
        ArrayList<Integer> eventList = new ArrayList<>();
        eventList.add(196893);
        eventsRequest.setEventTypes(eventList); // 成功识别事件
        String doorCount = doorFunctionApi.events(eventsRequest);//查询门禁事件V2
        JSONObject jsonObject = JSONObject.parseObject(doorCount);
        JSONArray list = (JSONArray) ((JSONObject) jsonObject.get("data")).get("list");
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()){
            Object o = iterator.next();
            JSONObject object = (JSONObject) o;
            if(object.get("personName") == null){
                iterator.remove();
            }
            object.put("picUri","https://192.168.1.207"+object.get("picUri"));
        }
        return jsonObject;
    }




}
