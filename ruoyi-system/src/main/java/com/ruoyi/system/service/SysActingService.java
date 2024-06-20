package com.ruoyi.system.service;

import com.ruoyi.system.entity.SysActing;

import java.util.List;

/**
 * 代办表(SysActing)表服务接口
 *
 * @author makejava
 * @since 2022-11-29 15:23:34
 */
public interface SysActingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysActing queryById(Integer id);

    /**
     * 分页查询
     *
     * @param sysActing 筛选条件
     * @return 查询结果
     */
    List<SysActing> queryByPage(SysActing sysActing);

    /**
     * 新增数据
     *
     * @param sysActing 实例对象
     * @return 实例对象
     */
    SysActing insert(SysActing sysActing);

    /**
     * 批量新增数据
     *
     * @param sysActing 实例对象
     * @return 实例对象
     */
    void   insertBatch(List<SysActing> sysActing);

    /**
     * 修改数据
     *
     * @param sysActing 实例对象
     * @return 实例对象
     */
    SysActing update(SysActing sysActing);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void completeActing(String titleName, String titleType, Integer id);

    void updateExecutorUser(SysActing sysActing);
}
