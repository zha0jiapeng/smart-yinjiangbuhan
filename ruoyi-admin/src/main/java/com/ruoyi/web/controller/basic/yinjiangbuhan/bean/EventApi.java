package com.ruoyi.web.controller.basic.yinjiangbuhan.bean;

import com.alibaba.fastjson.JSON;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.hik.Event;

import java.util.HashMap;
import java.util.Map;


/**
 * Auto Create on 2021-10-15 02:26:00
 */
public class EventApi {
    /**
     * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
     */
    static {

        ArtemisConfig.host = "192.168.1.207:443";
        ArtemisConfig.appKey = "22014652"; // 秘钥appkey
        ArtemisConfig.appSecret = "cBBBy87Ook7AEIQAgmsZ";
    }

    /**
     * STEP2：设置OpenAPI接口的上下文
     */
    private static final String ARTEMIS_PATH = "/artemis";



    //按事件类型订阅事件
    public String eventSubscriptionByEventTypes(Event event) {
        String getDataApi = ARTEMIS_PATH + "/api/eventService/v1/eventSubscriptionByEventTypes";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getDataApi);
            }
        };
        String body = JSON.toJSONString(event);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json");
        return result;
    }
}
