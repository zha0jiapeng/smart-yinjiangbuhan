package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.pojo.dto.InventoryHistoryDTO;
import com.ruoyi.system.pojo.vo.InventoryHistoryVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 库存记录  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface InventoryHistoryConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    InventoryHistoryDTO do2dto(InventoryHistory source);

    /**
     * @param source DTO
     * @return DO
     */
    InventoryHistory dto2do(InventoryHistoryDTO source);

    List<InventoryHistoryVO> dos2vos(List<InventoryHistory> list);
}
