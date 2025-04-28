package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.MigrantWorkerPayrollMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IMigrantWorkerPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 薪资Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Service
public class MigrantWorkerPayrollServiceImpl extends ServiceImpl<MigrantWorkerPayrollMapper, MigrantWorkerPayroll> implements IMigrantWorkerPayrollService {
    @Autowired(required = false)
    private MigrantWorkerPayrollMapper migrantWorkerPayrollMapper;


    /**
     * 查询薪资
     *
     * @param id 薪资主键
     * @return 薪资
     */
    @Override
    public MigrantWorkerPayroll selectMigrantWorkerPayrollById(Long id) {
        return migrantWorkerPayrollMapper.selectById(id);
    }

    /**
     * 查询薪资列表
     *
     * @param migrantWorkerPayroll 薪资
     * @return 薪资
     */
    @Override
    public List<MigrantWorkerPayroll> selectMigrantWorkerPayrollList(MigrantWorkerPayroll migrantWorkerPayroll) {
        return migrantWorkerPayrollMapper.selectList(new LambdaQueryWrapper<>(migrantWorkerPayroll));
    }

    /**
     * 新增薪资
     *
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    @Override
    public AjaxResult insertMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll) {
        migrantWorkerPayroll.setCreateTime(DateUtils.getNowDate());
        migrantWorkerPayroll.setDelFlag("0");
        return AjaxResult.success(migrantWorkerPayrollMapper.insert(migrantWorkerPayroll));
    }

    /**
     * 修改薪资
     *
     * @param migrantWorkerPayroll 薪资
     * @return 结果
     */
    @Override
    public int updateMigrantWorkerPayroll(MigrantWorkerPayroll migrantWorkerPayroll) {
        migrantWorkerPayroll.setUpdateTime(DateUtils.getNowDate());
        return migrantWorkerPayrollMapper.updateById(migrantWorkerPayroll);
    }

    /**
     * 批量删除薪资
     *
     * @param ids 需要删除的薪资主键
     * @return 结果
     */
    @Override
    public int deleteMigrantWorkerPayrollByIds(Long[] ids) {
        return migrantWorkerPayrollMapper.deleteMigrantWorkerPayrollByIds(ids);
    }

    /**
     * 删除薪资信息
     *
     * @param id 薪资主键
     * @return 结果
     */
    @Override
    public int deleteMigrantWorkerPayrollById(Long id) {
        return migrantWorkerPayrollMapper.deleteMigrantWorkerPayrollById(id);
    }


    /**
     * 查询薪资列表
     *
     * @param migrantWorkerPayroll 薪资
     * @return 薪资
     */
    @Override
    public List<MigrantWorkerPayroll> selectMigrantWorkerPayrollPaymentDateList(MigrantWorkerPayroll migrantWorkerPayroll) {
        LambdaQueryWrapper<MigrantWorkerPayroll> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(migrantWorkerPayroll.getGiveOutDate())) {
            String queryDate = migrantWorkerPayroll.getGiveOutDate();
            if (queryDate.matches("\\d{4}")) {
                // 如果输入是年份（如 2025）
                lambdaQueryWrapper.like(MigrantWorkerPayroll::getGiveOutDate, queryDate + "-");
            } else if (queryDate.matches("\\d{4}-\\d{2}")) {
                // 如果输入是年份和月份（如 2025-03）
                lambdaQueryWrapper.like(MigrantWorkerPayroll::getGiveOutDate, queryDate);
            } else if (queryDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // 如果输入是年份/月份/日（如 2025-03）
                lambdaQueryWrapper.like(MigrantWorkerPayroll::getGiveOutDate, queryDate);
            } else {
                throw new IllegalArgumentException("Invalid queryDate format. Use yyyy or yyyy-MM.");
            }
        }
        if (StringUtils.isNotEmpty(migrantWorkerPayroll.getUserName())) {
            lambdaQueryWrapper.like(MigrantWorkerPayroll::getUserName, migrantWorkerPayroll.getUserName());
        }
        if (StringUtils.isNotEmpty(migrantWorkerPayroll.getIdCard())) {
            lambdaQueryWrapper.like(MigrantWorkerPayroll::getIdCard, migrantWorkerPayroll.getIdCard());
        }
        return migrantWorkerPayrollMapper.selectList(lambdaQueryWrapper);
    }


    @Override
    @Transactional
    public boolean saveMigrantWorkerPayrollList(List<MigrantWorkerPayroll> migrantWorkerPayrolls) {
        int migrantWorkerPayrollsSize = migrantWorkerPayrolls.size();
        int resultSize = migrantWorkerPayrollMapper.insertBatch(migrantWorkerPayrolls);
        if (migrantWorkerPayrollsSize == resultSize) {
            return true;
        }
        return false;
    }
}
