package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryMovement;
import com.ruoyi.system.pojo.dto.InventoryMovementDTO;
import com.ruoyi.system.pojo.vo.InventoryMovementVO;
import com.ruoyi.system.pojo.vo.form.InventoryMovementFrom;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 库存移动  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface InventoryMovementConvert {

  /**
   * @param source DO
   * @return DTO
   */
  InventoryMovementDTO do2dto(InventoryMovement source);

  /**
   * @param source DTO
   * @return DO
   */
  InventoryMovement dto2do(InventoryMovementDTO source);

  List<InventoryMovementVO> dos2vos(List<InventoryMovement> list);

  InventoryMovementFrom do2form(InventoryMovement order);
}
