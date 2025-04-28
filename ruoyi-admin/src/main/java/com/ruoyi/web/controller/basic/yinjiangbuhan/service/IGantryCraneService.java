package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.GantryCrane;

import java.util.List;

/**
 * 龙门吊Service接口
 * 
 * @author ruoyi
 * @date 2025-04-21
 */
public interface IGantryCraneService  extends IService<GantryCrane>
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
     * 批量删除龙门吊
     * 
     * @param ids 需要删除的龙门吊主键集合
     * @return 结果
     */
    public int deleteGantryCraneByIds(Long[] ids);

    /**
     * 删除龙门吊信息
     * 
     * @param id 龙门吊主键
     * @return 结果
     */
    public int deleteGantryCraneById(Long id);
}
