package com.ruoyi.system.pojo.dto;

import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;
/**
 * 库存记录 DTO 对象
 *
 * @author zcc
 */
@Data
public class InventoryHistoryDTO extends BaseAudit {
    private Long id;
    private Long formId;
    private Integer formType;
    private Long itemId;
    private Long rackId;
    private Long warehouseId;
    private Long areaId;
    private BigDecimal quantity;
    private String remark;
}
