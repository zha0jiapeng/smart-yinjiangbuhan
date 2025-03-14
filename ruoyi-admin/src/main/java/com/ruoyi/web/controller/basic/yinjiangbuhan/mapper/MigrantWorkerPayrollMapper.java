package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 薪资Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-09
 */
public interface MigrantWorkerPayrollMapper extends BaseMapper<MigrantWorkerPayroll>
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

    /**
     * 新增薪资
     * 
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    public int insertMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll);

    /**
     * 修改薪资
     * 
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    public int updateMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll);

    /**
     * 删除薪资
     * 
     * @param id 薪资主键
     * @return 结果
     */
    public int deleteMigrantWorkerPayrollById(Long id);

    /**
     * 批量删除薪资
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMigrantWorkerPayrollByIds(Long[] ids);


    /**
     * 批量插入薪资
     *
     * @param migrantWorkerPayrolls
     * @return 结果
     */
    int insertBatch(@Param("list") List<MigrantWorkerPayroll> migrantWorkerPayrolls);
}
