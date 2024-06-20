package com.ruoyi.system.service;
import com.ruoyi.system.entity.ProjectCalculate;
import java.util.List;

/**
 * 三维进度统计数据(ProjectCalculate)表服务接口
 * @since 2023-06-01 15:02:19
 */
public interface ProjectCalculateService {
    /**
     * 通过ID查询单条数据
     * @param id 主键
     */
    ProjectCalculate queryById(Integer id);

    /**
     * 分页查询
     * @return 查询结果
     */
    List<ProjectCalculate> queryByPage(ProjectCalculate projectCalculate);

    /**
     * 新增数据
     * @param projectCalculate 实例对象
     */
    ProjectCalculate insert(ProjectCalculate projectCalculate);

    /**
     * 修改数据
     * @param projectCalculate 实例对象
     */
    ProjectCalculate update(ProjectCalculate projectCalculate);

    /**
     * 通过主键删除数据
     * @param id 主键
     */
    boolean deleteById(Integer id);
}
