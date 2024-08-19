package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;


import java.util.List;

/**
 * 雨量计Service接口
 * 
 * @author ruoyi
 * @date 2024-08-19
 */
public interface IRainService  extends IService<Rain>
{
    /**
     * 查询雨量计
     * 
     * @param id 雨量计主键
     * @return 雨量计
     */
    public Rain selectRainById(Long id);

    /**
     * 查询雨量计列表
     * 
     * @param rain 雨量计
     * @return 雨量计集合
     */
    public List<Rain> selectRainList(Rain rain);

    /**
     * 新增雨量计
     * 
     * @param rain 雨量计
     * @return 结果
     */
    public int insertRain(Rain rain);

    /**
     * 修改雨量计
     * 
     * @param rain 雨量计
     * @return 结果
     */
    public int updateRain(Rain rain);

    /**
     * 批量删除雨量计
     * 
     * @param ids 需要删除的雨量计主键集合
     * @return 结果
     */
    public int deleteRainByIds(Long[] ids);

    /**
     * 删除雨量计信息
     * 
     * @param id 雨量计主键
     * @return 结果
     */
    public int deleteRainById(Long id);
}
