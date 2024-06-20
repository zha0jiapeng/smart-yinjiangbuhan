package com.rubin.rpan.services.modules.log.service.impl;

import com.rubin.rpan.services.modules.log.constant.LogConstant;
import com.rubin.rpan.services.modules.log.dao.RPanErrorLogMapper;
import com.rubin.rpan.services.modules.log.entity.RPanErrorLog;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 系统错误日志业务层
 */
@Service(value = "errorLogService")
public class ErrorLogServiceImpl implements IErrorLogService {

    @Autowired
    @Qualifier(value = "rPanErrorLogMapper")
    private RPanErrorLogMapper rPanErrorLogMapper;

    /**
     * 保存系统错误日志
     *
     * @param logContent
     * @param userId
     */
    @Override
    public void save(String logContent, Long userId) {
        if (StringUtils.isBlank(logContent) || Objects.isNull(userId)) {
            return;
        }
        RPanErrorLog rPanErrorLog = new RPanErrorLog();
        rPanErrorLog.setLogContent(logContent);
        rPanErrorLog.setLogStatus(LogConstant.LogStatusEnum.PENDING.getCode());
        rPanErrorLog.setCreateUser(userId);
        rPanErrorLog.setCreateTime(new Date());
        rPanErrorLog.setUpdateUser(userId);
        rPanErrorLog.setUpdateTime(new Date());
        rPanErrorLogMapper.insertSelective(rPanErrorLog);
    }

}
