package com.ruoyi.quartz.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Component;


@Component("syncTask")
public class SyncTask {


    /**
     * 雨量信息定时任务
     */
    public void getRainnew() {

        String result1 = HttpUtil.get("http://127.0.0.1:8097/system/rain/getRainnew");
    }


    /**
     * 扬尘
     */
    public void getDustdetectionPushSwzk() {
        String result1 = HttpUtil.get("http://127.0.0.1:8097/dustdetection/pushSwzk");
    }


}
