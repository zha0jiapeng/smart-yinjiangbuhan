package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PreventionCheckTaskConfigVO;
import com.ruoyi.system.entity.PreventionCheckTaskConfig;

import java.util.List;

/**
 * 双重预防-排查任务配置表(PreventionCheckTaskConfig)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 13:58:19
 */
public interface PreventionCheckTaskConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionCheckTaskConfig queryById(Integer id);

    /**
     * 分页查询
     * @return 查询结果
     */
    List<PreventionCheckTaskConfigVO> queryByPage(PreventionCheckTaskConfigVO preventionCheckTaskConfigVO);

    /**
     * 新增数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 实例对象
     */
    PreventionCheckTaskConfig insert(PreventionCheckTaskConfig preventionCheckTaskConfig);

    /**
     * 修改数据
     *
     * @param preventionCheckTaskConfig 实例对象
     * @return 实例对象
     */
    PreventionCheckTaskConfig update(PreventionCheckTaskConfig preventionCheckTaskConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void insertBatch(List<PreventionCheckTaskConfig> preventionCheckTaskConfigList);

}
