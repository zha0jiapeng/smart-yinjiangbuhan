package com.ruoyi.system.pojo.dto;

import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 物料 DTO 对象
 *
 * @author zcc
 */
@Data
public class ItemDTO extends BaseAudit {
    private Long id;
    private String itemNo;
    private String itemName;
    private String itemType;
    private String unit;
    private Long rackId;
    private Long areaId;
    private Long warehouseId;
    private BigDecimal quantity;
    private LocalDateTime expiryDate;
    private String remark;
}
