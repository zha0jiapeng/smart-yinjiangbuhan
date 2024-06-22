package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import lombok.Data;

/**
 * 扬尘检测数据实体
 * @author hu_p
 * @date 2024/6/21
 */
@Data
public class DustDetectionData {

    String deviceId; // 设备ID
    Float pm25; // pm2.5
    Float pm10; // pm10
    Float noise; // 噪声
    Float tem; // 温度
    Float hum; // 湿度
    Float wp; // 风力
    Float ws; // 风速
    Integer wd; // 风向，8 方位，取值 0-7,0 表示北风，1 东北风 or 360 度，0 度表示正北，90 度正东
    Float tsp; // tsp
    Float atm; // 大气压
    String datatime; // 请求时间
}
