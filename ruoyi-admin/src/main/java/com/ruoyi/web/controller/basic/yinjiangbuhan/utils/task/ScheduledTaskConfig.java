package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ScheduledTaskConfig {

    private final ScheduledTaskService scheduledTaskService;

    public ScheduledTaskConfig(ScheduledTaskService scheduledTaskService) {
        this.scheduledTaskService = scheduledTaskService;
    }

    @Bean(name = "customScheduledTaskService")
    public ScheduledTaskService customScheduledTaskService() {
        return new ScheduledTaskService();
    }

    @PostConstruct
    public void init() {
        scheduledTaskService.start();
    }
}