package com.ruoyi.web.controller.basic.view.wms;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.InOrOutEnum;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.Item;
import com.ruoyi.system.domain.ReceiptOrder;
import com.ruoyi.system.mapper.ReceiptOrderMapper;
import com.ruoyi.system.pojo.dto.AppWmsOrderDTO;
import com.ruoyi.system.pojo.vo.ItemVO;
import com.ruoyi.system.pojo.vo.ReceiptOrderDetailVO;
import com.ruoyi.system.pojo.vo.ShipmentOrderDetailVO;
import com.ruoyi.system.pojo.vo.form.ReceiptOrderForm;
import com.ruoyi.system.pojo.vo.form.ShipmentOrderFrom;
import com.ruoyi.system.service.impl.ItemService;
import com.ruoyi.system.service.impl.ReceiptOrderService;
import com.ruoyi.system.service.impl.ShipmentOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(description = "app出入库订单")
@RestController
@RequestMapping("/app")
public class AppWmsOrderController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ReceiptOrderService receiptOrderService;
    @Autowired
    private ShipmentOrderService shipmentOrderService;
    @Autowired
    private ReceiptOrderMapper receiptOrderMapper;


    @PostMapping("/save/order")
    public ResponseEntity<Boolean> saveOrder(@RequestBody AppWmsOrderDTO appWmsOrderDTO) {
        log.info("app 创建出入库订单 :{}", JSON.toJSON(appWmsOrderDTO));

        if (appWmsOrderDTO.getInOrOut().equals(InOrOutEnum.OUT.getCode())) {
            ShipmentOrderFrom shipmentOrder = buildShipmentOrder(appWmsOrderDTO);
            log.info("新增出库单 参数:{}", JSON.toJSON(shipmentOrder));
            shipmentOrderService.addOrUpdate(shipmentOrder);
        }

        if (appWmsOrderDTO.getInOrOut().equals(InOrOutEnum.IN.getCode())) {
            ReceiptOrderForm receiptOrderForm = buildReceiptOrderForm(appWmsOrderDTO);
            log.info("新增入库单 参数:{}", JSON.toJSON(receiptOrderForm));
            receiptOrderService.addOrUpdate(receiptOrderForm);
        }

        return ResponseEntity.ok(true);
    }

    private ReceiptOrderForm buildReceiptOrderForm(AppWmsOrderDTO appWmsOrderDTO) {
        ReceiptOrderForm receiptOrderForm = new ReceiptOrderForm();
        //入库单号
        String inOrderCode = "R-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        inOrderCode += simpleDateFormat.format(new Date());
        receiptOrderForm.setReceiptOrderNo(inOrderCode);
        //入库类型
        receiptOrderForm.setReceiptOrderType(appWmsOrderDTO.getInOrOutType());
        //供应商编码
        receiptOrderForm.setSupplierId(appWmsOrderDTO.getSupplierCode());
        //订单号
        receiptOrderForm.setOrderNo(appWmsOrderDTO.getOrderCode());
        //入库状态 - 默认未发货
        receiptOrderForm.setReceiptOrderStatus(0);
        //备注
        receiptOrderForm.setRemark(appWmsOrderDTO.getRemark());


        //商品详情
        List<ItemVO> items = new ArrayList<>();
        ItemVO itemVO = queryProduct(appWmsOrderDTO.getProductCode());
        items.add(itemVO);
        //receiptOrderForm.setItems(items);

        //入库单详情
        List<ReceiptOrderDetailVO> details = new ArrayList<>();
        ReceiptOrderDetailVO receiptOrderDetailVO = new ReceiptOrderDetailVO();
        //商品信息
        receiptOrderDetailVO.setItemId(Long.valueOf(appWmsOrderDTO.getProductCode()));

        //仓库信息
        receiptOrderDetailVO.setRackId(itemVO.getRackId());
        receiptOrderDetailVO.setAreaId(itemVO.getAreaId());
        receiptOrderDetailVO.setWarehouseId(itemVO.getWarehouseId());

        //申报信息
        receiptOrderDetailVO.setPlanQuantity(new BigDecimal(appWmsOrderDTO.getInNumber()));
        receiptOrderDetailVO.setReceiptOrderStatus(0);
        receiptOrderDetailVO.setDelFlag(0);


        details.add(receiptOrderDetailVO);
        receiptOrderForm.setDetails(details);

        receiptOrderForm.setDelFlag(0);

        return receiptOrderForm;
    }

    private ShipmentOrderFrom buildShipmentOrder(AppWmsOrderDTO appWmsOrderDTO) {
        ShipmentOrderFrom shipmentOrder = new ShipmentOrderFrom();
        //出库单号
        String outOrderCode = "E-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        outOrderCode += simpleDateFormat.format(new Date());
        shipmentOrder.setShipmentOrderNo(outOrderCode);
        //出库类型
        shipmentOrder.setShipmentOrderType(appWmsOrderDTO.getInOrOutType());
        //客户
        shipmentOrder.setCustomerId(appWmsOrderDTO.getCustomerId());
        //订单号
        shipmentOrder.setOrderNo(appWmsOrderDTO.getOrderCode());
        //备注
        shipmentOrder.setRemark(appWmsOrderDTO.getRemark());
        //出库状态 未发货
        shipmentOrder.setShipmentOrderStatus(11);

        //商品详情
        List<ItemVO> items = new ArrayList<>();
        ItemVO itemVO = queryProduct(appWmsOrderDTO.getProductCode());
        items.add(itemVO);

        //出库单详情
        List<ShipmentOrderDetailVO> details = new ArrayList<>();
        ShipmentOrderDetailVO orderDetailVO = new ShipmentOrderDetailVO();
        //商品信息
        orderDetailVO.setItemId(Long.valueOf(appWmsOrderDTO.getProductCode()));

        //仓库信息
        orderDetailVO.setRackId(itemVO.getRackId());
        orderDetailVO.setAreaId(itemVO.getAreaId());
        orderDetailVO.setWarehouseId(itemVO.getWarehouseId());

        //申报信息
        orderDetailVO.setPlanQuantity(new BigDecimal(appWmsOrderDTO.getInNumber()));
        orderDetailVO.setShipmentOrderStatus(11);
        orderDetailVO.setDelFlag(0);

        details.add(orderDetailVO);
        shipmentOrder.setDetails(details);

        shipmentOrder.setDelFlag(0);
        return shipmentOrder;
    }

    public ItemVO queryProduct(String productCode) {
        Item item = itemService.selectById(Long.valueOf(productCode));
        return itemService.toVo(item);
    }

    @PostMapping("/in/warehousing/config")
    public AjaxResult inWarehousingConfig(@RequestBody AppWmsOrderDTO appWmsOrderDTO) {
        QueryWrapper<ReceiptOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_no", appWmsOrderDTO.getOrderCode());
        queryWrapper.in("del_flag", YnEnum.N.getCode());
        ReceiptOrder receiptOrder = receiptOrderMapper.selectOne(queryWrapper);
        if (null == receiptOrder) {
            return AjaxResult.success(new ReceiptOrder());
        }
        return AjaxResult.success(receiptOrder);
    }

}
