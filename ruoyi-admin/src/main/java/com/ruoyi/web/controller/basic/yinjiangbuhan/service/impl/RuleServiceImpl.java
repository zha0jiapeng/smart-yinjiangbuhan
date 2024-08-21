package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ModelAction;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Order;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by yangqinghua on 2022/2/26.
 */

@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService {

    private final KieBase kieBase;

    /**
     * 通过规则引擎处理
     *
     * @return
     */
    @Override
    public void executeSignRule(Order order) {
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
    }


}
