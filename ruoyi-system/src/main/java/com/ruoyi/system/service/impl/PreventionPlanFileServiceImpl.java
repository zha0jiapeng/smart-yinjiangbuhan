package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.PreventionPlanFile;
import com.ruoyi.system.mapper.PreventionPlanFileMapper;
import com.ruoyi.system.service.PreventionPlanFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PreventionPlanFile)表服务实现类
 *
 * @author makejava
 * @since 2022-12-17 13:22:15
 */
@Service("preventionPlanFileService")
public class PreventionPlanFileServiceImpl implements PreventionPlanFileService {
    @Resource
    private PreventionPlanFileMapper preventionPlanFileMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionPlanFile queryById(Integer id) {
        return this.preventionPlanFileMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionPlanFile 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionPlanFile> queryByPage(PreventionPlanFile preventionPlanFile) {
        return this.preventionPlanFileMapper.queryAllByLimit(preventionPlanFile);
    }

    /**
     * 新增数据
     *
     * @param preventionPlanFile 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlanFile insert(PreventionPlanFile preventionPlanFile) {
        this.preventionPlanFileMapper.insert(preventionPlanFile);
        return preventionPlanFile;
    }

    /**
     * 修改数据
     *
     * @param preventionPlanFile 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionPlanFile update(PreventionPlanFile preventionPlanFile) {
        this.preventionPlanFileMapper.update(preventionPlanFile);
        return this.queryById(preventionPlanFile.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionPlanFileMapper.deleteById(id) > 0;
    }
}
