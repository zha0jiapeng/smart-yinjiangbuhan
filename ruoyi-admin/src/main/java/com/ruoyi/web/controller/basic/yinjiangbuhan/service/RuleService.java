package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ModelAction;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Order;

import java.util.List;

/**
 * Created by yangqinghua on 2022/2/26.
 */
public interface RuleService {
    /**
     * 通过规则引擎处理
     */
    void executeSignRule(Order order);
}
