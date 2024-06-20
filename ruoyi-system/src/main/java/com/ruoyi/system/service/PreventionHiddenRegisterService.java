package com.ruoyi.system.service;

import com.ruoyi.system.entity.PreventionHiddenRegister;

import java.util.List;
import java.util.Map;

/**
 * 隐患录入记录表(PreventionHiddenRegister)表服务接口
 *
 * @author makejava
 * @since 2022-11-22 17:20:34
 */
public interface PreventionHiddenRegisterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionHiddenRegister queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionHiddenRegister 筛选条件
     * @return 查询结果
     */
    List<PreventionHiddenRegister> queryByPage(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 新增数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 实例对象
     */
    PreventionHiddenRegister insert(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 修改数据
     *
     * @param preventionHiddenRegister 实例对象
     * @return 实例对象
     */
    PreventionHiddenRegister update(PreventionHiddenRegister preventionHiddenRegister);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void updateHiddenState(PreventionHiddenRegister preventionHiddenRegister);

    Map<String, List<Map<String, Object>>> countHidden(Integer deptId);

    void updateRectificationUserName(PreventionHiddenRegister preventionHiddenRegister);

    void updateRectification(PreventionHiddenRegister preventionHiddenRegister);

    void updateReview(PreventionHiddenRegister preventionHiddenRegister);
}
