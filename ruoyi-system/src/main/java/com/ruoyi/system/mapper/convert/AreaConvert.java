package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Area;
import com.ruoyi.system.pojo.dto.AreaDTO;
import com.ruoyi.system.pojo.vo.AreaVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 货区  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface AreaConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    AreaDTO do2dto(Area source);

    /**
     * @param source DTO
     * @return DO
     */
    Area dto2do(AreaDTO source);

    List<AreaVO> dos2vos(List<Area> list);
}
