package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.PreventionUnit;
import com.ruoyi.system.mapper.PreventionUnitMapper;
import com.ruoyi.system.service.PreventionUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PreventionUnit)表服务实现类
 *
 * @author makejava
 * @since 2022-12-02 16:53:56
 */
@Service("preventionUnitService")
public class PreventionUnitServiceImpl implements PreventionUnitService {
    @Resource
    private PreventionUnitMapper preventionUnitMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionUnit queryById(Integer id) {
        return this.preventionUnitMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionUnit 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionUnit> queryByPage(PreventionUnit preventionUnit) {
        return this.preventionUnitMapper.queryAllByLimit(preventionUnit);
    }

    /**
     * 新增数据
     *
     * @param preventionUnit 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionUnit insert(PreventionUnit preventionUnit) {
        this.preventionUnitMapper.insert(preventionUnit);
        return preventionUnit;
    }

    /**
     * 修改数据
     *
     * @param preventionUnit 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionUnit update(PreventionUnit preventionUnit) {
        this.preventionUnitMapper.update(preventionUnit);
        return this.queryById(preventionUnit.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionUnitMapper.deleteById(id) > 0;
    }
}
