package com.ruoyi.web.controller.basic.yinjiangbuhan.enums;

import java.util.Arrays;
import java.util.List;

public enum MaterialType {
    CEMENT("水泥", Arrays.asList("水泥", "水泥P.O42.5", "水泥P.O52.5低碱", "水泥P.042.5低碱")),
    FLY_ASH("粉煤灰", Arrays.asList("粉煤灰", "粉煤灰", "硅灰", "粉煤灰")),
    WATER("水", Arrays.asList("水", "拌合水", "地表水")),
    MINERAL_POWDER("矿粉", Arrays.asList("矿粉")),
    ADDITIVES("外加剂", Arrays.asList("减水剂", "防腐剂", "膨胀剂", "气密剂", "速凝剂", "引气剂", "聚羧酸高性能减水剂")),
    COARSE_AGGREGATE("粗骨料", Arrays.asList("粗骨料", "碎石", "小石5-10")),
    FINE_AGGREGATE("细骨料", Arrays.asList("细骨料", "砂子"));

    private final String typeName;
    private final List<String> names;

    MaterialType(String typeName, List<String> names) {
        this.typeName = typeName;
        this.names = names;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<String> getNames() {
        return names;
    }
}