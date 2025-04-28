package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SpecialOperationTypes;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.SpecialOperationTypesMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISpecialOperationTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 特种作业类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
@Service
public class SpecialOperationTypesServiceImpl extends ServiceImpl<SpecialOperationTypesMapper, SpecialOperationTypes> implements ISpecialOperationTypesService
{
    @Autowired(required = false)
    private SpecialOperationTypesMapper specialOperationTypesMapper;

    /**
     * 查询特种作业类型
     * 
     * @param id 特种作业类型主键
     * @return 特种作业类型
     */
    @Override
    public SpecialOperationTypes selectSpecialOperationTypesById(Long id)
    {
        return specialOperationTypesMapper.selectById(id);
    }

    /**
     * 查询特种作业类型列表
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 特种作业类型
     */
    @Override
    public List<SpecialOperationTypes> selectSpecialOperationTypesList(SpecialOperationTypes specialOperationTypes)
    {
        return specialOperationTypesMapper.selectList(new LambdaQueryWrapper<>(specialOperationTypes));
    }

    /**
     * 新增特种作业类型
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 结果
     */
    @Override
    public int insertSpecialOperationTypes(SpecialOperationTypes specialOperationTypes)
    {
        specialOperationTypes.setCreateTime(DateUtils.getNowDate());
        return specialOperationTypesMapper.insert(specialOperationTypes);
    }

    /**
     * 修改特种作业类型
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 结果
     */
    @Override
    public int updateSpecialOperationTypes(SpecialOperationTypes specialOperationTypes)
    {
        specialOperationTypes.setUpdateTime(DateUtils.getNowDate());
        return specialOperationTypesMapper.updateById(specialOperationTypes);
    }

    /**
     * 批量删除特种作业类型
     * 
     * @param ids 需要删除的特种作业类型主键
     * @return 结果
     */
    @Override
    public int deleteSpecialOperationTypesByIds(Long[] ids)
    {
        return specialOperationTypesMapper.deleteSpecialOperationTypesByIds(ids);
    }

    /**
     * 删除特种作业类型信息
     * 
     * @param id 特种作业类型主键
     * @return 结果
     */
    @Override
    public int deleteSpecialOperationTypesById(Long id)
    {
        return specialOperationTypesMapper.deleteSpecialOperationTypesById(id);
    }
}
