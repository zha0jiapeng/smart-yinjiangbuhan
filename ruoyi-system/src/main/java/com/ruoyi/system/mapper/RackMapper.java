package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Rack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货架Mapper接口
 * 
 * @author zcc
 */
public interface RackMapper extends BaseMapper<Rack> {
    /**
     * 查询货架列表
     *
     * @param rack 货架
     * @return 货架集合
     */
    List<Rack> selectByEntity(Rack rack);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
