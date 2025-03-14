package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;

import java.util.List;

/**
 * 薪资Service接口
 * 
 * @author ruoyi
 * @date 2025-03-09
 */
public interface IMigrantWorkerPayrollService  extends IService<MigrantWorkerPayroll>
{
    /**
     * 查询薪资
     * 
     * @param id 薪资主键
     * @return 薪资
     */
    public MigrantWorkerPayroll selectMigrantWorkerPayrollById(Long id);

    /**
     * 查询薪资列表
     * 
     * @param migrantWorkerPayroll 薪资
     * @return 薪资集合
     */
    public List<MigrantWorkerPayroll> selectMigrantWorkerPayrollList(MigrantWorkerPayroll migrantWorkerPayroll);
    public List<MigrantWorkerPayroll> selectMigrantWorkerPayrollPaymentDateList(MigrantWorkerPayroll migrantWorkerPayroll);
    public boolean saveMigrantWorkerPayrollList(List<MigrantWorkerPayroll> migrantWorkerPayrolls);

    /**selectMigrantWorkerPayrollPaymentDateList
     * 新增薪资
     * 
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    public AjaxResult insertMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll);

    /**
     * 修改薪资
     * 
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    public int updateMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll);

    /**
     * 批量删除薪资
     * 
     * @param ids 需要删除的薪资主键集合
     * @return 结果
     */
    public int deleteMigrantWorkerPayrollByIds(Long[] ids);

    /**
     * 删除薪资信息
     * 
     * @param id 薪资主键
     * @return 结果
     */
    public int deleteMigrantWorkerPayrollById(Long id);
}
