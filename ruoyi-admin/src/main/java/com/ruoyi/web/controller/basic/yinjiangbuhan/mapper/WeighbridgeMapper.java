package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Weighbridge;

import java.util.List;

/**
 * 地磅Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-23
 */
public interface WeighbridgeMapper extends BaseMapper<Weighbridge>
{
    /**
     * 查询地磅
     * 
     * @param id 地磅主键
     * @return 地磅
     */
    public Weighbridge selectWeighbridgeById(Long id);

    /**
     * 查询地磅列表
     * 
     * @param weighbridge 地磅
     * @return 地磅集合
     */
    public List<Weighbridge> selectWeighbridgeList(Weighbridge weighbridge);

    /**
     * 新增地磅
     * 
     * @param weighbridge 地磅
     * @return 结果
     */
    public int insertWeighbridge(Weighbridge weighbridge);

    /**
     * 修改地磅
     * 
     * @param weighbridge 地磅
     * @return 结果
     */
    public int updateWeighbridge(Weighbridge weighbridge);

    /**
     * 删除地磅
     * 
     * @param id 地磅主键
     * @return 结果
     */
    public int deleteWeighbridgeById(Long id);

    /**
     * 批量删除地磅
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWeighbridgeByIds(Long[] ids);
}
