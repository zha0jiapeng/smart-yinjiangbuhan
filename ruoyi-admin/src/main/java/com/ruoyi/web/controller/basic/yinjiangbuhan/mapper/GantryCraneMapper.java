package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.GantryCrane;

import java.util.List;

/**
 * 龙门吊Mapper接口
 * 
 * @author ruoyi
 * @date 2025-04-21
 */
public interface GantryCraneMapper extends BaseMapper<GantryCrane>
{
    /**
     * 查询龙门吊
     * 
     * @param id 龙门吊主键
     * @return 龙门吊
     */
    public GantryCrane selectGantryCraneById(Long id);

    /**
     * 查询龙门吊列表
     * 
     * @param gantryCrane 龙门吊
     * @return 龙门吊集合
     */
    public List<GantryCrane> selectGantryCraneList(GantryCrane gantryCrane);

    /**
     * 新增龙门吊
     * 
     * @param gantryCrane 龙门吊
     * @return 结果
     */
    public int insertGantryCrane(GantryCrane gantryCrane);

    /**
     * 修改龙门吊
     * 
     * @param gantryCrane 龙门吊
     * @return 结果
     */
    public int updateGantryCrane(GantryCrane gantryCrane);

    /**
     * 删除龙门吊
     * 
     * @param id 龙门吊主键
     * @return 结果
     */
    public int deleteGantryCraneById(Long id);

    /**
     * 批量删除龙门吊
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGantryCraneByIds(Long[] ids);
}
