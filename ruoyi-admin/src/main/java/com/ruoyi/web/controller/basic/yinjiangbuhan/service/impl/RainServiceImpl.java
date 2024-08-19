package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.RainMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IRainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 雨量计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-19
 */
@Service
public class RainServiceImpl extends ServiceImpl<RainMapper, Rain> implements IRainService
{
    @Autowired(required = false)
    private RainMapper rainMapper;

    /**
     * 查询雨量计
     * 
     * @param id 雨量计主键
     * @return 雨量计
     */
    @Override
    public Rain selectRainById(Long id)
    {
        return rainMapper.selectRainById(id);
    }

    /**
     * 查询雨量计列表
     * 
     * @param rain 雨量计
     * @return 雨量计
     */
    @Override
    public List<Rain> selectRainList(Rain rain)
    {
        return rainMapper.selectRainList(rain);
    }

    /**
     * 新增雨量计
     * 
     * @param rain 雨量计
     * @return 结果
     */
    @Override
    public int insertRain(Rain rain)
    {
        rain.setCreateTime(DateUtils.getNowDate());
        return rainMapper.insertRain(rain);
    }

    /**
     * 修改雨量计
     * 
     * @param rain 雨量计
     * @return 结果
     */
    @Override
    public int updateRain(Rain rain)
    {
        rain.setUpdateTime(DateUtils.getNowDate());
        return rainMapper.updateRain(rain);
    }

    /**
     * 批量删除雨量计
     * 
     * @param ids 需要删除的雨量计主键
     * @return 结果
     */
    @Override
    public int deleteRainByIds(Long[] ids)
    {
        return rainMapper.deleteRainByIds(ids);
    }

    /**
     * 删除雨量计信息
     * 
     * @param id 雨量计主键
     * @return 结果
     */
    @Override
    public int deleteRainById(Long id)
    {
        return rainMapper.deleteRainById(id);
    }
}
