/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.domain.ShipmentOrderDetail;
import com.ruoyi.system.pojo.dto.ShipmentOrderDetailDTO;
import com.ruoyi.system.pojo.vo.ShipmentOrderDetailVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ShipmentOrderDetailConvertImpl implements ShipmentOrderDetailConvert {
    public ShipmentOrderDetailConvertImpl() {
    }

    public ShipmentOrderDetailDTO do2dto(ShipmentOrderDetail source) {
        if (source == null) {
            return null;
        } else {
            ShipmentOrderDetailDTO shipmentOrderDetailDTO = new ShipmentOrderDetailDTO();
            shipmentOrderDetailDTO.setCreateBy(source.getCreateBy());
            shipmentOrderDetailDTO.setCreateTime(source.getCreateTime());
            shipmentOrderDetailDTO.setUpdateBy(source.getUpdateBy());
            shipmentOrderDetailDTO.setUpdateTime(source.getUpdateTime());
            shipmentOrderDetailDTO.setId(source.getId());
            shipmentOrderDetailDTO.setShipmentOrderId(source.getShipmentOrderId());
            shipmentOrderDetailDTO.setItemId(source.getItemId());
            shipmentOrderDetailDTO.setPlanQuantity(source.getPlanQuantity());
            shipmentOrderDetailDTO.setRealQuantity(source.getRealQuantity());
            shipmentOrderDetailDTO.setRackId(source.getRackId());
            shipmentOrderDetailDTO.setRemark(source.getRemark());
            shipmentOrderDetailDTO.setWarehouseId(source.getWarehouseId());
            shipmentOrderDetailDTO.setAreaId(source.getAreaId());
            shipmentOrderDetailDTO.setShipmentOrderStatus(source.getShipmentOrderStatus());
            return shipmentOrderDetailDTO;
        }
    }

    public ShipmentOrderDetail dto2do(ShipmentOrderDetailDTO source) {
        if (source == null) {
            return null;
        } else {
            ShipmentOrderDetail shipmentOrderDetail = new ShipmentOrderDetail();
            shipmentOrderDetail.setCreateBy(source.getCreateBy());
            shipmentOrderDetail.setCreateTime(source.getCreateTime());
            shipmentOrderDetail.setUpdateBy(source.getUpdateBy());
            shipmentOrderDetail.setUpdateTime(source.getUpdateTime());
            shipmentOrderDetail.setId(source.getId());
            shipmentOrderDetail.setShipmentOrderId(source.getShipmentOrderId());
            shipmentOrderDetail.setItemId(source.getItemId());
            shipmentOrderDetail.setPlanQuantity(source.getPlanQuantity());
            shipmentOrderDetail.setRealQuantity(source.getRealQuantity());
            shipmentOrderDetail.setRackId(source.getRackId());
            shipmentOrderDetail.setRemark(source.getRemark());
            shipmentOrderDetail.setWarehouseId(source.getWarehouseId());
            shipmentOrderDetail.setAreaId(source.getAreaId());
            shipmentOrderDetail.setShipmentOrderStatus(source.getShipmentOrderStatus());
            return shipmentOrderDetail;
        }
    }

    public List<ShipmentOrderDetailVO> dos2vos(List<ShipmentOrderDetail> list) {
        if (list == null) {
            return null;
        } else {
            List<ShipmentOrderDetailVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                ShipmentOrderDetail shipmentOrderDetail = (ShipmentOrderDetail)var3.next();
                list1.add(this.shipmentOrderDetailToShipmentOrderDetailVO(shipmentOrderDetail));
            }

            return list1;
        }
    }

    public List<ShipmentOrderDetail> vos2dos(List<ShipmentOrderDetailVO> details) {
        if (details == null) {
            return null;
        } else {
            List<ShipmentOrderDetail> list = new ArrayList(details.size());
            Iterator var3 = details.iterator();

            while(var3.hasNext()) {
                ShipmentOrderDetailVO shipmentOrderDetailVO = (ShipmentOrderDetailVO)var3.next();
                list.add(this.shipmentOrderDetailVOToShipmentOrderDetail(shipmentOrderDetailVO));
            }

            return list;
        }
    }

    public InventoryHistory do2InventoryHistory(ShipmentOrderDetailVO it) {
        if (it == null) {
            return null;
        } else {
            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setQuantity(it.getRealQuantity());
            inventoryHistory.setCreateBy(it.getCreateBy());
            inventoryHistory.setCreateTime(it.getCreateTime());
            inventoryHistory.setUpdateBy(it.getUpdateBy());
            inventoryHistory.setUpdateTime(it.getUpdateTime());
            inventoryHistory.setId(it.getId());
            inventoryHistory.setItemId(it.getItemId());
            inventoryHistory.setWarehouseId(it.getWarehouseId());
            inventoryHistory.setAreaId(it.getAreaId());
            inventoryHistory.setRackId(it.getRackId());
            inventoryHistory.setRemark(it.getRemark());
            inventoryHistory.setDelFlag(it.getDelFlag());
            return inventoryHistory;
        }
    }

    protected ShipmentOrderDetailVO shipmentOrderDetailToShipmentOrderDetailVO(ShipmentOrderDetail shipmentOrderDetail) {
        if (shipmentOrderDetail == null) {
            return null;
        } else {
            ShipmentOrderDetailVO shipmentOrderDetailVO = new ShipmentOrderDetailVO();
            shipmentOrderDetailVO.setCreateBy(shipmentOrderDetail.getCreateBy());
            shipmentOrderDetailVO.setCreateTime(shipmentOrderDetail.getCreateTime());
            shipmentOrderDetailVO.setUpdateBy(shipmentOrderDetail.getUpdateBy());
            shipmentOrderDetailVO.setUpdateTime(shipmentOrderDetail.getUpdateTime());
            shipmentOrderDetailVO.setId(shipmentOrderDetail.getId());
            shipmentOrderDetailVO.setShipmentOrderId(shipmentOrderDetail.getShipmentOrderId());
            shipmentOrderDetailVO.setItemId(shipmentOrderDetail.getItemId());
            shipmentOrderDetailVO.setPlanQuantity(shipmentOrderDetail.getPlanQuantity());
            shipmentOrderDetailVO.setRealQuantity(shipmentOrderDetail.getRealQuantity());
            shipmentOrderDetailVO.setRackId(shipmentOrderDetail.getRackId());
            shipmentOrderDetailVO.setRemark(shipmentOrderDetail.getRemark());
            shipmentOrderDetailVO.setWarehouseId(shipmentOrderDetail.getWarehouseId());
            shipmentOrderDetailVO.setDelFlag(shipmentOrderDetail.getDelFlag());
            shipmentOrderDetailVO.setAreaId(shipmentOrderDetail.getAreaId());
            shipmentOrderDetailVO.setShipmentOrderStatus(shipmentOrderDetail.getShipmentOrderStatus());
            return shipmentOrderDetailVO;
        }
    }

    protected ShipmentOrderDetail shipmentOrderDetailVOToShipmentOrderDetail(ShipmentOrderDetailVO shipmentOrderDetailVO) {
        if (shipmentOrderDetailVO == null) {
            return null;
        } else {
            ShipmentOrderDetail shipmentOrderDetail = new ShipmentOrderDetail();
            shipmentOrderDetail.setCreateBy(shipmentOrderDetailVO.getCreateBy());
            shipmentOrderDetail.setCreateTime(shipmentOrderDetailVO.getCreateTime());
            shipmentOrderDetail.setUpdateBy(shipmentOrderDetailVO.getUpdateBy());
            shipmentOrderDetail.setUpdateTime(shipmentOrderDetailVO.getUpdateTime());
            shipmentOrderDetail.setId(shipmentOrderDetailVO.getId());
            shipmentOrderDetail.setShipmentOrderId(shipmentOrderDetailVO.getShipmentOrderId());
            shipmentOrderDetail.setItemId(shipmentOrderDetailVO.getItemId());
            shipmentOrderDetail.setPlanQuantity(shipmentOrderDetailVO.getPlanQuantity());
            shipmentOrderDetail.setRealQuantity(shipmentOrderDetailVO.getRealQuantity());
            shipmentOrderDetail.setRackId(shipmentOrderDetailVO.getRackId());
            shipmentOrderDetail.setDelFlag(shipmentOrderDetailVO.getDelFlag());
            shipmentOrderDetail.setRemark(shipmentOrderDetailVO.getRemark());
            shipmentOrderDetail.setWarehouseId(shipmentOrderDetailVO.getWarehouseId());
            shipmentOrderDetail.setAreaId(shipmentOrderDetailVO.getAreaId());
            shipmentOrderDetail.setShipmentOrderStatus(shipmentOrderDetailVO.getShipmentOrderStatus());
            return shipmentOrderDetail;
        }
    }
}
*/
