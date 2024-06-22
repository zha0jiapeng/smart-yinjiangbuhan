package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;


import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.TuhuguancheUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/carLocation")
public class CarLocationController {


    @RequestMapping("/location")
    public Map carLocation(){
        return TuhuguancheUtil.getDeviceLocation();
    }
}
