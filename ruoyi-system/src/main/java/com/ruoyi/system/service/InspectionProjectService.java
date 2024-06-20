package com.ruoyi.system.service;

import com.ruoyi.system.entity.InspectionProject;

import java.util.List;

/**
 * (InspectionProject)表服务接口
 *
 * @author makejava
 * @since 2022-11-25 17:10:49
 */
public interface InspectionProjectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InspectionProject queryById(Integer id);

    /**
     * 分页查询
     *
     * @param inspectionProject 筛选条件
     * @return 查询结果
     */
    List<InspectionProject> queryByPage(InspectionProject inspectionProject);

    /**
     * 新增数据
     *
     * @param inspectionProject 实例对象
     * @return 实例对象
     */
    InspectionProject insert(InspectionProject inspectionProject);

    /**
     * 修改数据
     *
     * @param inspectionProject 实例对象
     * @return 实例对象
     */
    InspectionProject update(InspectionProject inspectionProject);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
