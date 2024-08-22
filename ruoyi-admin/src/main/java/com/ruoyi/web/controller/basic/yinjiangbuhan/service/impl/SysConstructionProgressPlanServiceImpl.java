package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressPlan;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SysConstructionProgressPlanMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISysConstructionProgressPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 施工进度计划Service业务层处理
 * 
 * @author mashir0
 * @date 2024-08-22
 */
@Service
public class SysConstructionProgressPlanServiceImpl extends ServiceImpl<SysConstructionProgressPlanMapper, SysConstructionProgressPlan> implements ISysConstructionProgressPlanService
{
    @Autowired(required = false)
    private SysConstructionProgressPlanMapper sysConstructionProgressPlanMapper;

    /**
     * 查询施工进度计划
     * 
     * @param id 施工进度计划主键
     * @return 施工进度计划
     */
    @Override
    public SysConstructionProgressPlan selectSysConstructionProgressPlanById(Long id)
    {
        return sysConstructionProgressPlanMapper.selectSysConstructionProgressPlanById(id);
    }

    /**
     * 查询施工进度计划列表
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 施工进度计划
     */
    @Override
    public List<SysConstructionProgressPlan> selectSysConstructionProgressPlanList(SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        return sysConstructionProgressPlanMapper.selectSysConstructionProgressPlanList(sysConstructionProgressPlan);
    }

    /**
     * 新增施工进度计划
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 结果
     */
    @Override
    public int insertSysConstructionProgressPlan(SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        return sysConstructionProgressPlanMapper.insertSysConstructionProgressPlan(sysConstructionProgressPlan);
    }

    /**
     * 修改施工进度计划
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 结果
     */
    @Override
    public int updateSysConstructionProgressPlan(SysConstructionProgressPlan sysConstructionProgressPlan)
    {
        return sysConstructionProgressPlanMapper.updateSysConstructionProgressPlan(sysConstructionProgressPlan);
    }

    /**
     * 批量删除施工进度计划
     * 
     * @param ids 需要删除的施工进度计划主键
     * @return 结果
     */
    @Override
    public int deleteSysConstructionProgressPlanByIds(Long[] ids)
    {
        return sysConstructionProgressPlanMapper.deleteSysConstructionProgressPlanByIds(ids);
    }

    /**
     * 删除施工进度计划信息
     * 
     * @param id 施工进度计划主键
     * @return 结果
     */
    @Override
    public int deleteSysConstructionProgressPlanById(Long id)
    {
        return sysConstructionProgressPlanMapper.deleteSysConstructionProgressPlanById(id);
    }

    @Override
    public SysConstructionProgressPlan getTotalByYear(Integer year) {
        return sysConstructionProgressPlanMapper.getTotalByYear(year);
    }
}
