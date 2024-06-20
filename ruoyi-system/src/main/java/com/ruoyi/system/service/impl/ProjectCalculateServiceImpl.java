package com.ruoyi.system.service.impl;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.mapper.ProjectCalculateMapper;
import com.ruoyi.system.service.ProjectCalculateService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 三维进度统计数据(ProjectCalculate)表服务实现类
 * @since 2023-06-01 15:02:19
 */
@Service("projectCalculateService")
public class ProjectCalculateServiceImpl implements ProjectCalculateService {
    @Resource
    private ProjectCalculateMapper projectCalculateDao;

    /**
     * 通过ID查询单条数据
     * @param id 主键
     */
    @Override
    public ProjectCalculate queryById(Integer id) {
        return this.projectCalculateDao.queryById(id);
    }

    /**
     * 分页查询
     * @return 查询结果
     */
    @Override
    public List<ProjectCalculate> queryByPage(ProjectCalculate projectCalculate) {
        return projectCalculateDao.queryAllByLimit(projectCalculate);
    }

    /**
     * 新增数据
     * @param projectCalculate 实例对象
     */
    @Override
    public ProjectCalculate insert(ProjectCalculate projectCalculate) {
        this.projectCalculateDao.insert(projectCalculate);
        return projectCalculate;
    }

    /**
     * 修改数据
     * @param projectCalculate 实例对象
     */
    @Override
    public ProjectCalculate update(ProjectCalculate projectCalculate) {
        this.projectCalculateDao.update(projectCalculate);
        return this.queryById(projectCalculate.getId());
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectCalculateDao.deleteById(id) > 0;
    }
}
