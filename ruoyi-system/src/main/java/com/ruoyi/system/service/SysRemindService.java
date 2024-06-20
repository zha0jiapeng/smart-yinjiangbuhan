package com.ruoyi.system.service;

import com.ruoyi.system.entity.SysRemind;

import java.util.List;

/**
 * 提醒表(SysRemind)表服务接口
 *
 * @author makejava
 * @since 2022-12-17 15:16:40
 */
public interface SysRemindService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRemind queryById(Integer id);

    /**
     * 分页查询
     *
     * @param sysRemind 筛选条件
     * @return 查询结果
     */
    List<SysRemind> queryByPage(SysRemind sysRemind);

    /**
     * 新增数据
     *
     * @param sysRemind 实例对象
     * @return 实例对象
     */
    SysRemind insert(SysRemind sysRemind);

    SysRemind insert(String type,String content,Integer userId);

    /**
     * 修改数据
     *
     * @param sysRemind 实例对象
     * @return 实例对象
     */
    SysRemind update(SysRemind sysRemind);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
