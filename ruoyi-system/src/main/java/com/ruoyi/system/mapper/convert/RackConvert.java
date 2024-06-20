package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Rack;
import com.ruoyi.system.pojo.dto.RackDTO;
import com.ruoyi.system.pojo.vo.RackVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 货架  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface RackConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    RackDTO do2dto(Rack source);

    /**
     * @param source DTO
     * @return DO
     */
    Rack dto2do(RackDTO source);

    List<RackVO> dos2vos(List<Rack> list);
}
