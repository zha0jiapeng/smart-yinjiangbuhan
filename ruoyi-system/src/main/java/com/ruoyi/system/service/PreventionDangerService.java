package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionDanger;

import java.util.List;

/**
 * (PreventionDanger)表服务接口
 *
 * @author makejava
 * @since 2022-11-19 10:46:00
 */
public interface PreventionDangerService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionDanger queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionDanger 筛选条件
     * @return 查询结果
     */
    List<PreventionDanger> queryByPage(PreventionDanger preventionDanger);

    /**
     * 新增数据
     *
     * @param preventionDanger 实例对象
     * @return 实例对象
     */
    PreventionDanger insert(PreventionDanger preventionDanger);

    /**
     * 修改数据
     *
     * @param preventionDanger 实例对象
     * @return 实例对象
     */
    PreventionDanger update(PreventionDanger preventionDanger);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
