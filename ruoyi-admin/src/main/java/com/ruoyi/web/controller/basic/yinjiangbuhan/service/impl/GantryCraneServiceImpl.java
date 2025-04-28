package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.GantryCrane;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.GantryCraneMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IGantryCraneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 龙门吊Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-04-21
 */
@Service
public class GantryCraneServiceImpl extends ServiceImpl<GantryCraneMapper, GantryCrane> implements IGantryCraneService
{
    @Autowired(required = false)
    private GantryCraneMapper gantryCraneMapper;

    /**
     * 查询龙门吊
     * 
     * @param id 龙门吊主键
     * @return 龙门吊
     */
    @Override
    public GantryCrane selectGantryCraneById(Long id)
    {
        return gantryCraneMapper.selectById(id);
    }

    /**
     * 查询龙门吊列表
     * 
     * @param gantryCrane 龙门吊
     * @return 龙门吊
     */
    @Override
    public List<GantryCrane> selectGantryCraneList(GantryCrane gantryCrane)
    {
        return gantryCraneMapper.selectList(new LambdaQueryWrapper<>(gantryCrane));
    }

    /**
     * 新增龙门吊
     * 
     * @param gantryCrane 龙门吊
     * @return 结果
     */
    @Override
    public int insertGantryCrane(GantryCrane gantryCrane)
    {
        gantryCrane.setCreateTime(DateUtils.getNowDate());
        return gantryCraneMapper.insert(gantryCrane);
    }

    /**
     * 修改龙门吊
     * 
     * @param gantryCrane 龙门吊
     * @return 结果
     */
    @Override
    public int updateGantryCrane(GantryCrane gantryCrane)
    {
        gantryCrane.setUpdateTime(DateUtils.getNowDate());
        return gantryCraneMapper.updateById(gantryCrane);
    }

    /**
     * 批量删除龙门吊
     * 
     * @param ids 需要删除的龙门吊主键
     * @return 结果
     */
    @Override
    public int deleteGantryCraneByIds(Long[] ids)
    {
        return gantryCraneMapper.deleteGantryCraneByIds(ids);
    }

    /**
     * 删除龙门吊信息
     * 
     * @param id 龙门吊主键
     * @return 结果
     */
    @Override
    public int deleteGantryCraneById(Long id)
    {
        return gantryCraneMapper.deleteGantryCraneById(id);
    }
}
