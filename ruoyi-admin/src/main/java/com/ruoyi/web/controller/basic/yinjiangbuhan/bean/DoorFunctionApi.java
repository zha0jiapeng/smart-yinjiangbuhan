package com.ruoyi.web.controller.basic.yinjiangbuhan.bean;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

public class DoorFunctionApi {
    /**
     * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
     */
    static {
        ArtemisConfig.host = "192.168.1.207:443";
        ArtemisConfig.appKey = "29016549"; // 秘钥appkey
        ArtemisConfig.appSecret = "2kTiCvwc7xvxHuddO7gT";
    }
    /**
     * STEP2：设置OpenAPI接口的上下文
     */
    private static final String ARTEMIS_PATH = "/artemis";
    //门禁点反控
    public  String getCameraList(Map<String,Object> map ){
        String doControlDataApi = ARTEMIS_PATH +"/api/resource/v2/camera/search";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",doControlDataApi);
            }
        };
        String body= JSON.toJSONString(map);
        String result = ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        return result;
    }
    public  String previewURLs(Map<String,Object> request ){
        //String doControlDataApi = ARTEMIS_PATH +"/api/video/v2/cameras/previewURLs";
        String doControlDataApi = ARTEMIS_PATH +"/api/vnsc/mls/v1/preview/openApi/getPreviewParam";
        Map<String,String> path = new HashMap<String,String>(){
            {
                put("https://",doControlDataApi);
            }
        };
        String body= JSON.toJSONString(request);
        String result = ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        return result;
    }


    //查询门禁点事件v2
    public  String events(EventsRequest eventsRequest ){
        String eventsDataApi = ARTEMIS_PATH +"/api/acs/v2/door/events";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",eventsDataApi);
            }
        };
        String body=JSON.toJSONString(eventsRequest);
        String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        return result;
    }

    public JSONObject previewURLS(Map<String,Object> request){
        String eventsDataApi = ARTEMIS_PATH +"/api/video/v2/cameras/previewURLs";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",eventsDataApi);
            }
        };
        String body=JSON.toJSONString(request);
        String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public JSONObject search(Map<String,Object> request){
        String eventsDataApi = ARTEMIS_PATH +"/api/resource/v2/acsDevice/search";
        Map<String,String> path = new HashMap<String,String>(2){
            {
                put("https://",eventsDataApi);
            }
        };
        String body=JSON.toJSONString(request);
        String result =ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    //添加人员信息
    public  String personSingleAdd(Map<String,Object> request){
        String doControlDataApi = ARTEMIS_PATH +"/api/resource/v2/person/single/add";
        Map<String,String> path = new HashMap<String,String>(){
            {
                put("https://",doControlDataApi);
            }
        };
        String body= JSON.toJSONString(request);
        String result = ArtemisHttpUtil.doPostStringArtemis(path,body,null,null,"application/json");
        return result;
    }

}
