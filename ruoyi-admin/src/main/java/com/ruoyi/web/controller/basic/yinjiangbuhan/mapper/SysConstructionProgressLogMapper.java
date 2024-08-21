package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressLog;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 施工日志Mapper接口
 * 
 * @author mashir0
 * @date 2024-08-20
 */
public interface SysConstructionProgressLogMapper extends BaseMapper<SysConstructionProgressLog>
{
    @Select("select * from sys_construction_progress_log")
    Map<String, Object> getSum();
}
