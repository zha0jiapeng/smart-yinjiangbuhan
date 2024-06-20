package com.ruoyi.system.pojo.vo.form;

import com.ruoyi.system.domain.InventoryMovement;
import com.ruoyi.system.pojo.vo.InventoryMovementDetailVO;
import com.ruoyi.system.pojo.vo.ItemVO;
import lombok.Data;

import java.util.List;

@Data
public class InventoryMovementFrom extends InventoryMovement {
  // 详情
  private List<InventoryMovementDetailVO> details;
  // 所有商品
  private List<ItemVO> items;
}
