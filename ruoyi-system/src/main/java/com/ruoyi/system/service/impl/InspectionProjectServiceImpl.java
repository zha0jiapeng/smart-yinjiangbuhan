package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.InspectionProject;
import com.ruoyi.system.mapper.InspectionProjectMapper;
import com.ruoyi.system.service.InspectionProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (InspectionProject)表服务实现类
 *
 * @author makejava
 * @since 2022-11-25 17:10:49
 */
@Service("inspectionProjectService")
public class InspectionProjectServiceImpl implements InspectionProjectService {
    @Resource
    private InspectionProjectMapper inspectionProjectMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionProject queryById(Integer id) {
        return this.inspectionProjectMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionProject 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionProject> queryByPage(InspectionProject inspectionProject) {
        return this.inspectionProjectMapper.queryAllByLimit(inspectionProject);
    }

    /**
     * 新增数据
     *
     * @param inspectionProject 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionProject insert(InspectionProject inspectionProject) {
        this.inspectionProjectMapper.insert(inspectionProject);
        return inspectionProject;
    }

    /**
     * 修改数据
     *
     * @param inspectionProject 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionProject update(InspectionProject inspectionProject) {
        this.inspectionProjectMapper.update(inspectionProject);
        return this.queryById(inspectionProject.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionProjectMapper.deleteById(id) > 0;
    }
}
