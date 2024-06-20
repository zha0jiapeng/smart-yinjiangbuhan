package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 货区Mapper接口
 * 
 * @author zcc
 */
public interface AreaMapper extends BaseMapper<Area> {
    /**
     * 查询货区列表
     *
     * @param area 货区
     * @return 货区集合
     */
    List<Area> selectByEntity(Area area);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
