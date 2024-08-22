package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressPlan;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 施工进度计划Mapper接口
 * 
 * @author mashir0
 * @date 2024-08-22
 */
public interface SysConstructionProgressPlanMapper extends BaseMapper<SysConstructionProgressPlan>
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
     * 删除施工进度计划
     * 
     * @param id 施工进度计划主键
     * @return 结果
     */
    public int deleteSysConstructionProgressPlanById(Long id);

    /**
     * 批量删除施工进度计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysConstructionProgressPlanByIds(Long[] ids);

    @Select("SELECT " +
            "SUM(main_hole_digging_length) AS total_main_hole_digging_length, " +
            "SUM(main_hole_lining_length) AS total_main_hole_lining_length, " +
            "SUM(side_hole_digging_length) AS total_side_hole_digging_length, " +
            "SUM(side_hole_lining_length) AS total_side_hole_lining_length, " +
            "SUM(total_investment) AS total_investment_2024 " +
            "FROM sys_construction_progress_plan " +
            "WHERE month LIKE CONCAT(#{year}, '%')")
    SysConstructionProgressPlan getTotalByYear(String year);
}
