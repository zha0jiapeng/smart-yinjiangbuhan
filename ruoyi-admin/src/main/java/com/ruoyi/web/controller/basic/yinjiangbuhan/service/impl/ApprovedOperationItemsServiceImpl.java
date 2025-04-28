package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ApprovedOperationItems;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.ApprovedOperationItemsMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IApprovedOperationItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 准操项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
@Service
public class ApprovedOperationItemsServiceImpl extends ServiceImpl<ApprovedOperationItemsMapper, ApprovedOperationItems> implements IApprovedOperationItemsService
{
    @Autowired(required = false)
    private ApprovedOperationItemsMapper approvedOperationItemsMapper;

    /**
     * 查询准操项目
     * 
     * @param id 准操项目主键
     * @return 准操项目
     */
    @Override
    public ApprovedOperationItems selectApprovedOperationItemsById(Long id)
    {
        return approvedOperationItemsMapper.selectById(id);
    }

    /**
     * 查询准操项目列表
     * 
     * @param approvedOperationItems 准操项目
     * @return 准操项目
     */
    @Override
    public List<ApprovedOperationItems> selectApprovedOperationItemsList(ApprovedOperationItems approvedOperationItems)
    {
        return approvedOperationItemsMapper.selectList(new LambdaQueryWrapper<>(approvedOperationItems));
    }

    /**
     * 新增准操项目
     * 
     * @param approvedOperationItems 准操项目
     * @return 结果
     */
    @Override
    public int insertApprovedOperationItems(ApprovedOperationItems approvedOperationItems)
    {
        approvedOperationItems.setCreateTime(DateUtils.getNowDate());
        return approvedOperationItemsMapper.insert(approvedOperationItems);
    }

    /**
     * 修改准操项目
     * 
     * @param approvedOperationItems 准操项目
     * @return 结果
     */
    @Override
    public int updateApprovedOperationItems(ApprovedOperationItems approvedOperationItems)
    {
        approvedOperationItems.setUpdateTime(DateUtils.getNowDate());
        return approvedOperationItemsMapper.updateById(approvedOperationItems);
    }

    /**
     * 批量删除准操项目
     * 
     * @param ids 需要删除的准操项目主键
     * @return 结果
     */
    @Override
    public int deleteApprovedOperationItemsByIds(Long[] ids)
    {
        return approvedOperationItemsMapper.deleteApprovedOperationItemsByIds(ids);
    }

    /**
     * 删除准操项目信息
     * 
     * @param id 准操项目主键
     * @return 结果
     */
    @Override
    public int deleteApprovedOperationItemsById(Long id)
    {
        return approvedOperationItemsMapper.deleteApprovedOperationItemsById(id);
    }
}
