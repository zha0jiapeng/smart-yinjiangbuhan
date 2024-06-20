package com.ruoyi.system.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 入库单 数据视图对象
 * 
 * @author zcc
 */
@Data
public class ReceiptOrderVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 入库单号 */
    @Excel(name = "入库单号")
    private String receiptOrderNo;
   /** 入库类型 */
    @Excel(name = "入库类型")
    private Integer receiptOrderType;
   /** 供应商 */
    @Excel(name = "供应商")
    private Long supplierId;
   /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;
   /** 入库状态 */
    @Excel(name = "入库状态")
    private Integer receiptOrderStatus;
   /** 备注 */
    @Excel(name = "备注")
    private String remark;

    private Integer detailCount;

    @ApiModelProperty("计划数量")
    @Excel(name = "计划数量")
    private BigDecimal planQuantity;

    @ApiModelProperty("实际数量")
    @Excel(name = "实际数量")
    private BigDecimal realQuantity;
    /**
     * 物料
     */
    @Excel(name = "物料")
    private String itemName;
    /**
     * 入库时间
     */
    private String receiptDate;
}
