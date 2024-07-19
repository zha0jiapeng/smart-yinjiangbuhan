package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.DoorFunctionApi;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.EventsRequest;
import com.ruoyi.web.controller.basic.yinjiangbuhan.scheduling.DoorEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public AjaxResult list(String startTime,String endTime)
    {
        DoorFunctionApi doorFunctionApi = new DoorFunctionApi();
        EventsRequest eventsRequest = new EventsRequest(); //查询门禁事件
        eventsRequest.setPageNo(1); // 显示最后一个人
        eventsRequest.setPageSize(400);
        eventsRequest.setStartTime(DoorEvent.getISO8601TimestampFromDateStr(startTime));
        eventsRequest.setEndTime(DoorEvent.getISO8601TimestampFromDateStr(endTime));
//           ArrayList<String> indexcodList = new ArrayList<String>();
//           indexcodList.add("ec8d96058dcb4dcca04468080c9570aa");
//           eventsRequest.setDoorIndexCodes(indexcodList); // 所有门禁标识
        String doorcount = doorFunctionApi.events(eventsRequest);//查询门禁事件V2
        JSONObject jsonObject = JSONObject.parseObject(doorcount);
        JSONArray list = (JSONArray) ((JSONObject) jsonObject.get("data")).get("list");
        for (Object o : list) {
            JSONObject object = (JSONObject) o;
            object.put("picUri","https://192.168.1.207"+object.get("picUri"));
        }
        return AjaxResult.success(list);
    }
}
