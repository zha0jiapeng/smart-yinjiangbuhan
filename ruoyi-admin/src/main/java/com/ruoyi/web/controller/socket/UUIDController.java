package com.ruoyi.web.controller.socket;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.basic.Worker;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("uuid")
public class UUIDController {
    @Resource
    private RedisCache redisCache;

    @PostMapping("/send")
    public Result<?> send(@RequestBody Worker worker) {

        Set<String> dataSet = new HashSet<>();
        dataSet.add(worker.getCardCode());
        redisCache.setCacheSet("uuid", dataSet);

        return Result.ok();
    }


}
