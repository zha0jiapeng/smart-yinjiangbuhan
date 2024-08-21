package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressLog;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SysConstructionProgressLogMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysConstructionProgressLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 施工日志Service业务层处理
 * 
 * @author mashir0
 * @date 2024-08-20
 */
@Service
public class SysConstructionProgressLogServiceImpl extends ServiceImpl<SysConstructionProgressLogMapper, SysConstructionProgressLog> implements ISysConstructionProgressLogService
{
    @Autowired(required = false)
    private SysConstructionProgressLogMapper sysConstructionProgressLogMapper;

    /**
     * 查询施工日志
     * 
     * @param id 施工日志主键
     * @return 施工日志
     */
    @Override
    public SysConstructionProgressLog selectSysConstructionProgressLogById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询施工日志列表
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 施工日志
     */
    @Override
    public List<SysConstructionProgressLog> selectSysConstructionProgressLogList(SysConstructionProgressLog sysConstructionProgressLog)
    {
        LambdaQueryWrapper<SysConstructionProgressLog> queryWrapper = new LambdaQueryWrapper<>(sysConstructionProgressLog);
        return sysConstructionProgressLogMapper.selectList(queryWrapper);
    }

    /**
     * 新增施工日志
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 结果
     */
    @Override
    public int insertSysConstructionProgressLog(SysConstructionProgressLog sysConstructionProgressLog)
    {
        return sysConstructionProgressLogMapper.insert(sysConstructionProgressLog);
    }

    /**
     * 修改施工日志
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 结果
     */
    @Override
    public int updateSysConstructionProgressLog(SysConstructionProgressLog sysConstructionProgressLog)
    {
        return sysConstructionProgressLogMapper.updateById(sysConstructionProgressLog);
    }

    /**
     * 批量删除施工日志
     * 
     * @param ids 需要删除的施工日志主键
     * @return 结果
     */
    @Override
    public int deleteSysConstructionProgressLogByIds(Long[] ids)
    {
        return sysConstructionProgressLogMapper.deleteBatchIds(new ArrayList<>(Arrays.asList(ids)));
    }

    /**
     * 删除施工日志信息
     * 
     * @param id 施工日志主键
     * @return 结果
     */
    @Override
    public int deleteSysConstructionProgressLogById(Long id)
    {
        return sysConstructionProgressLogMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> getSum() {
        return sysConstructionProgressLogMapper.getSum();
    }
}
