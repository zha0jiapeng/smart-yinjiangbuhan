package com.ruoyi.system.pojo.dto;

import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

/**
 * 承运商 DTO 对象
 *
 * @author zcc
 */
@Data
public class AppWmsOrderDTO extends BaseAudit {

    private String scanType;// 扫码类型

    private Integer inOrOut; // 出入库 下拉框 (出库1 2入库)

    private Integer inOrOutType;// 入库类型 采购入库/外协入库/退货入库

    private Long supplierCode;// 供应商 接口 下拉框

    private Long customerId; //客户

    private String orderCode; // 订单号 输入框

    private String remark; // 备注 输入框

    private String productCode;// 商品编码 url带过来

    private String productName; // 商品名称 url带过来

    private String inNumber; // 入库数量 填写

    private String houseCode;// 仓库/库区/货架/下拉框选择
}
