package com.ruoyi.web.controller.basic.view.bim;

import com.ruoyi.system.domain.bim.TopBimDomain;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("data/analysis")
public class DateAnalysisController {

    @PostMapping("/analysis")
    public Result<?> top() {
        log.info("DateAnalysisController analysis 方法请求开始!");
        TopBimDomain topBimDomain = new TopBimDomain();
        topBimDomain.setTianqi("3");
        topBimDomain.setQiya("5");
        topBimDomain.setWendu("7");
        topBimDomain.setShidu("2");
        topBimDomain.setFengxiang("2");
        topBimDomain.setFengsu("3");
        topBimDomain.setFengsu("1");
        log.info("DateAnalysisController analysis 方法请求结束!");
        return Result.OK(topBimDomain);
    }

}
