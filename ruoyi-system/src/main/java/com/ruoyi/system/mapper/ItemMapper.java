package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物料Mapper接口
 * 
 * @author zcc
 */
public interface ItemMapper extends BaseMapper<Item> {
    /**
     * 查询物料列表
     *
     * @param item 物料
     * @return 物料集合
     */
    List<Item> selectByEntity(Item item);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);

    void updateInfo(Item update);

    List<Item> queryDateList(String expiryDate);
}
