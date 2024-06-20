package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.pojo.dto.InventoryDTO;
import com.ruoyi.system.pojo.vo.InventoryVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 库存  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface InventoryConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    InventoryDTO do2dto(Inventory source);

    /**
     * @param source DTO
     * @return DO
     */
    Inventory dto2do(InventoryDTO source);

    List<InventoryVO> dos2vos(List<Inventory> list);

    Inventory inventoryHistory2invertory(InventoryHistory it);
}
