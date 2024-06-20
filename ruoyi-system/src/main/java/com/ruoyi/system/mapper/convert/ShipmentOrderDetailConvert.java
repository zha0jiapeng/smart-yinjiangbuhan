package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.domain.ShipmentOrderDetail;
import com.ruoyi.system.pojo.dto.ShipmentOrderDetailDTO;
import com.ruoyi.system.pojo.vo.ShipmentOrderDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
/**
 * 出库单详情  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface ShipmentOrderDetailConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    ShipmentOrderDetailDTO do2dto(ShipmentOrderDetail source);

    /**
     * @param source DTO
     * @return DO
     */
    ShipmentOrderDetail dto2do(ShipmentOrderDetailDTO source);

    List<ShipmentOrderDetailVO> dos2vos(List<ShipmentOrderDetail> list);

    List<ShipmentOrderDetail> vos2dos(List<ShipmentOrderDetailVO> details);

    @Mapping(target = "quantity", source = "realQuantity")
    InventoryHistory do2InventoryHistory(ShipmentOrderDetailVO it);
}
