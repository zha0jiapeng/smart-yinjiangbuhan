package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Delivery;
import com.ruoyi.system.pojo.dto.DeliveryDTO;
import com.ruoyi.system.pojo.vo.DeliveryVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 发货记录  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface DeliveryConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    DeliveryDTO do2dto(Delivery source);

    /**
     * @param source DTO
     * @return DO
     */
    Delivery dto2do(DeliveryDTO source);

    List<DeliveryVO> dos2vos(List<Delivery> list);
}
