package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PreventionDeviceVO;
import com.ruoyi.system.entity.PreventionDevice;

import java.util.List;

/**
 * 双重预防-装置管理(PreventionDevice)表服务接口
 *
 * @author makejava
 * @since 2022-11-17 11:18:35
 */
public interface PreventionDeviceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionDevice queryById(Integer id);

    /**
     * 分页查询
     * @return 查询结果
     */
    List<PreventionDeviceVO> queryByPage(PreventionDevice preventionDevice);

    /**
     * 新增数据
     *
     * @param preventionDevice 实例对象
     * @return 实例对象
     */
    PreventionDevice insert(PreventionDevice preventionDevice);

    /**
     * 修改数据
     *
     * @param preventionDevice 实例对象
     * @return 实例对象
     */
    PreventionDevice update(PreventionDevice preventionDevice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    String getAllCountByDangerName(String dangerName,String dutyInfoName,String dutyType);

    List<PreventionDevice> listByLevel();

}
