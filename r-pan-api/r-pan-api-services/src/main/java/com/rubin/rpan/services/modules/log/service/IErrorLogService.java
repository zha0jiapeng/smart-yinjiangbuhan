package com.rubin.rpan.services.modules.log.service;

public interface IErrorLogService {

    void save(String logContent, Long userId);

}
