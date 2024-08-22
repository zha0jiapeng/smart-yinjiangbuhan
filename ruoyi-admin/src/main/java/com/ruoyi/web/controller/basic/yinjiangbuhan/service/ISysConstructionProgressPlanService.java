package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressPlan;

import java.util.List;

/**
 * 施工进度计划Service接口
 * 
 * @author mashir0
 * @date 2024-08-22
 */
public interface ISysConstructionProgressPlanService extends IService<SysConstructionProgressPlan>
{
    /**
     * 查询施工进度计划
     * 
     * @param id 施工进度计划主键
     * @return 施工进度计划
     */
    public SysConstructionProgressPlan selectSysConstructionProgressPlanById(Long id);

    /**
     * 查询施工进度计划列表
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 施工进度计划集合
     */
    public List<SysConstructionProgressPlan> selectSysConstructionProgressPlanList(SysConstructionProgressPlan sysConstructionProgressPlan);

    /**
     * 新增施工进度计划
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 结果
     */
    public int insertSysConstructionProgressPlan(SysConstructionProgressPlan sysConstructionProgressPlan);

    /**
     * 修改施工进度计划
     * 
     * @param sysConstructionProgressPlan 施工进度计划
     * @return 结果
     */
    public int updateSysConstructionProgressPlan(SysConstructionProgressPlan sysConstructionProgressPlan);

    /**
     * 批量删除施工进度计划
     * 
     * @param ids 需要删除的施工进度计划主键集合
     * @return 结果
     */
    public int deleteSysConstructionProgressPlanByIds(Long[] ids);

    /**
     * 删除施工进度计划信息
     * 
     * @param id 施工进度计划主键
     * @return 结果
     */
    public int deleteSysConstructionProgressPlanById(Long id);

    SysConstructionProgressPlan getTotalByYear(String year);
}
