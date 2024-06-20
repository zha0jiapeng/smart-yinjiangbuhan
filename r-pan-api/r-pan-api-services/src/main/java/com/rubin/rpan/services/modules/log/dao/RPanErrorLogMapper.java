package com.rubin.rpan.services.modules.log.dao;

import com.rubin.rpan.services.modules.log.entity.RPanErrorLog;
import org.springframework.stereotype.Repository;

/**
 * 系统错误日志持久层接口
 */
@Repository(value = "rPanErrorLogMapper")
public interface RPanErrorLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RPanErrorLog record);

    int insertSelective(RPanErrorLog record);

    RPanErrorLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RPanErrorLog record);

    int updateByPrimaryKey(RPanErrorLog record);

}