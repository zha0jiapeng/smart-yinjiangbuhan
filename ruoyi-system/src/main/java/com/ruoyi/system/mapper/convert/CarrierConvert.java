package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Carrier;
import com.ruoyi.system.pojo.dto.CarrierDTO;
import com.ruoyi.system.pojo.vo.CarrierVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 承运商  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface CarrierConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    CarrierDTO do2dto(Carrier source);

    /**
     * @param source DTO
     * @return DO
     */
    Carrier dto2do(CarrierDTO source);

    List<CarrierVO> dos2vos(List<Carrier> list);
}
