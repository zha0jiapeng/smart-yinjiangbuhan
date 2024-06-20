package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.ReceiptOrder;
import com.ruoyi.system.pojo.dto.ReceiptOrderDTO;
import com.ruoyi.system.pojo.vo.ReceiptOrderVO;
import com.ruoyi.system.pojo.vo.form.ReceiptOrderForm;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 入库单  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface ReceiptOrderConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    ReceiptOrderDTO do2dto(ReceiptOrder source);

    /**
     * @param source DTO
     * @return DO
     */
    ReceiptOrder dto2do(ReceiptOrderDTO source);

    List<ReceiptOrderVO> dos2vos(List<ReceiptOrder> list);

    ReceiptOrderForm do2form(ReceiptOrder bean);
}
