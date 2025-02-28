package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Weighbridge;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.WeighbridgeMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IWeighbridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地磅Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-23
 */
@Service
public class WeighbridgeServiceImpl extends ServiceImpl<WeighbridgeMapper, Weighbridge> implements IWeighbridgeService
{
    @Autowired(required = false)
    private WeighbridgeMapper weighbridgeMapper;

    /**
     * 查询地磅
     * 
     * @param id 地磅主键
     * @return 地磅
     */
    @Override
    public Weighbridge selectWeighbridgeById(Long id)
    {
        return weighbridgeMapper.selectById(id);
    }

    /**
     * 查询地磅列表
     * 
     * @param weighbridge 地磅
     * @return 地磅
     */
    @Override
    public List<Weighbridge> selectWeighbridgeList(Weighbridge weighbridge)
    {
        return weighbridgeMapper.selectList(new LambdaQueryWrapper<>(weighbridge));
    }

    /**
     * 新增地磅
     * 
     * @param weighbridge 地磅
     * @return 结果
     */
    @Override
    public int insertWeighbridge(Weighbridge weighbridge)
    {
        weighbridge.setCreateTime(DateUtils.getNowDate());
        return weighbridgeMapper.insert(weighbridge);
    }

    /**
     * 修改地磅
     * 
     * @param weighbridge 地磅
     * @return 结果
     */
    @Override
    public int updateWeighbridge(Weighbridge weighbridge)
    {
        weighbridge.setUpdateTime(DateUtils.getNowDate());
        return weighbridgeMapper.updateById(weighbridge);
    }

    /**
     * 批量删除地磅
     * 
     * @param ids 需要删除的地磅主键
     * @return 结果
     */
    @Override
    public int deleteWeighbridgeByIds(Long[] ids)
    {
        return weighbridgeMapper.deleteWeighbridgeByIds(ids);
    }

    /**
     * 删除地磅信息
     * 
     * @param id 地磅主键
     * @return 结果
     */
    @Override
    public int deleteWeighbridgeById(Long id)
    {
        return weighbridgeMapper.deleteWeighbridgeById(id);
    }
}
