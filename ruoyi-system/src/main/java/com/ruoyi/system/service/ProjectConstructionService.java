package com.ruoyi.system.service;
import com.ruoyi.system.entity.ProjectConstruction;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 项目施工报表(ProjectConstruction)表服务接口
 * @since 2023-06-01 14:05:22
 */
public interface ProjectConstructionService {
    /**
     * 通过ID查询单条数据
     * @return 实例对象
     */
    ProjectConstruction queryById(Integer id);

    /**
     * 分页查询
     * @return 查询结果
     */
    List<ProjectConstruction> queryByPage(ProjectConstruction projectConstruction);

    List<ProjectConstruction> queryAll();

    List<ProjectConstruction> reportChart(ProjectConstruction projectConstruction);

    /**
     * 新增数据
     * @return 实例对象
     */
    ProjectConstruction insert(ProjectConstruction projectConstruction);

    /**
     * 修改数据
     * @return 实例对象
     */
    ProjectConstruction update(ProjectConstruction projectConstruction);

    String importExcel(List<ProjectConstruction> projectConstructions, Boolean isUpdateSupport, String operName) throws ParseException;

    /**
     * 通过主键删除数据
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void countConstructionScheduleList();
}
