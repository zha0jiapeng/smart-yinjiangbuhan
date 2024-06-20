package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionUnit;

import java.util.List;

/**
 * (PreventionUnit)表服务接口
 *
 * @author makejava
 * @since 2022-12-02 16:53:56
 */
public interface PreventionUnitService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionUnit queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionUnit 筛选条件
     * @return 查询结果
     */
    List<PreventionUnit> queryByPage(PreventionUnit preventionUnit);

    /**
     * 新增数据
     *
     * @param preventionUnit 实例对象
     * @return 实例对象
     */
    PreventionUnit insert(PreventionUnit preventionUnit);

    /**
     * 修改数据
     *
     * @param preventionUnit 实例对象
     * @return 实例对象
     */
    PreventionUnit update(PreventionUnit preventionUnit);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
