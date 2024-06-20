package com.ruoyi.web.controller.basic.yinjiangbuhan.enums;

import lombok.Getter;
import lombok.Setter;

public enum IndexType {
    TEMP(0,0),
    HUMI(1,0),
    NOISE(2,0),
    PM25(3,1),
    PM10(4,1),
    DUST(5,1),
    AIR_PRESSURE(6,1),
    WIND_SPEED(7,1),
    WIND_DIRECTION(8,-1),
    OXYGEN(9,0),
    methane(10,0),
    CARBON_MONOXIDE(11,1),
    HYDROGEN_SULFIDE(12,0),
    CARBON_DIOXIDE(13,1),//二氧化碳
    SULFUR_DIOXIDE(14,0), //二氧化硫
    ammonia(15,0), //氨气
    NITRIC_OXIDE(16,0),//一氧化氮
    ILLUMINATION(17,1), //光照
    NEGATIVE_PRESSURE(18,1);//

    IndexType(Integer type, Integer flag) {
        this.type = type;
        this.flag = flag;
    }

    @Setter
    @Getter
    private Integer type;

    @Setter
    @Getter
    private Integer flag;

    public static Integer getFlagByType(Integer type){
        IndexType[] values = values();
        for (IndexType value : values) {
            if(value.type.equals(type)){
                return value.flag;
            }
        }
        return null;
    }
    public static String getNameByType(Integer type){
        IndexType[] values = values();
        for (IndexType value : values) {
            if(value.type.equals(type)){
                return value.toString();
            }
        }
        return null;
    }


}
