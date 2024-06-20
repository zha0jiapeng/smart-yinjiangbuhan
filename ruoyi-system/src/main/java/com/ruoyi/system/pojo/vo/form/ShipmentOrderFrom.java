package com.ruoyi.system.pojo.vo.form;

import com.ruoyi.system.domain.ShipmentOrder;
import com.ruoyi.system.pojo.vo.ItemVO;
import com.ruoyi.system.pojo.vo.ShipmentOrderDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class ShipmentOrderFrom extends ShipmentOrder {
  // 出库单详情
  private List<ShipmentOrderDetailVO> details;
  // 所有商品
  private List<ItemVO> items;
}
