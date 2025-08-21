package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Rain;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 雨量计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-19
 */
public interface RainMapper extends BaseMapper<Rain>
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

    int insertBatch(@Param("list") List<Rain> rainList);

    /**
     * 修改雨量计
     * 
     * @param rain 雨量计
     * @return 结果
     */
    public int updateRain(Rain rain);

    /**
     * 删除雨量计
     * 
     * @param id 雨量计主键
     * @return 结果
     */
    public int deleteRainById(Long id);

    /**
     * 批量删除雨量计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRainByIds(Long[] ids);
}
