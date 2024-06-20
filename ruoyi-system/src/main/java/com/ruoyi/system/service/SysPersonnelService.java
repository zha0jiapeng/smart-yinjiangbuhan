package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.SysPersonnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 人员信息表(SysPersonnel)表服务接口
 *
 * @author makejava
 * @since 2022-12-25 14:31:15
 */
public interface SysPersonnelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPersonnel queryById(Integer id);

    /**
     * 分页查询
     *
     * @param sysPersonnel 筛选条件
     * @return 查询结果
     */
    List<SysPersonnel> queryByPage(SysPersonnel sysPersonnel);

    /**
     * 新增数据
     *
     * @param sysPersonnel 实例对象
     * @return 实例对象
     */
    SysPersonnel insert(SysPersonnel sysPersonnel);

    /**
     * 修改数据
     *
     * @param sysPersonnel 实例对象
     * @return 实例对象
     */
    SysPersonnel update(SysPersonnel sysPersonnel);



    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
