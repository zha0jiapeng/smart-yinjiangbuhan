package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.PreventionDanger;
import com.ruoyi.system.mapper.PreventionDangerMapper;
import com.ruoyi.system.service.PreventionDangerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PreventionDanger)表服务实现类
 *
 * @author makejava
 * @since 2022-11-19 10:46:00
 */
@Service("preventionDangerService")
public class PreventionDangerServiceImpl implements PreventionDangerService {
    @Resource
    private PreventionDangerMapper preventionDangerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionDanger queryById(Integer id) {
        return this.preventionDangerDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionDanger 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionDanger> queryByPage(PreventionDanger preventionDanger) {
        return preventionDangerDao.queryAllByLimit(preventionDanger);
    }

    /**
     * 新增数据
     *
     * @param preventionDanger 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionDanger insert(PreventionDanger preventionDanger) {
        this.preventionDangerDao.insert(preventionDanger);
        return preventionDanger;
    }

    /**
     * 修改数据
     *
     * @param preventionDanger 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionDanger update(PreventionDanger preventionDanger) {
        this.preventionDangerDao.update(preventionDanger);
        return this.queryById(preventionDanger.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionDangerDao.deleteById(id) > 0;
    }
}
