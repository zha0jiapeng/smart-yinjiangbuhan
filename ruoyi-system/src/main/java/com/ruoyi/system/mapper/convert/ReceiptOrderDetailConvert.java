package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.domain.ReceiptOrderDetail;
import com.ruoyi.system.pojo.dto.ReceiptOrderDetailDTO;
import com.ruoyi.system.pojo.vo.ReceiptOrderDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 入库单详情  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface ReceiptOrderDetailConvert {

    /**
     * @param source DO
     * @return DTO
     */
    ReceiptOrderDetailDTO do2dto(ReceiptOrderDetail source);

    /**
     * @param source DTO
     * @return DO
     */
    ReceiptOrderDetail dto2do(ReceiptOrderDetailDTO source);

    /**
     * @param source DO
     * @return VO
     */
    ReceiptOrderDetailVO toVo(ReceiptOrderDetail source);

    List<ReceiptOrderDetailVO> dos2vos(List<ReceiptOrderDetail> list);

    List<ReceiptOrderDetail> vos2dos(List<ReceiptOrderDetailVO> details);

    @Mapping(target = "quantity", source = "realQuantity")
    InventoryHistory do2InventoryHistory(ReceiptOrderDetailVO it);
}
