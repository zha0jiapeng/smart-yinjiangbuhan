/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.domain.ReceiptOrderDetail;
import com.ruoyi.system.pojo.dto.ReceiptOrderDetailDTO;
import com.ruoyi.system.pojo.vo.ReceiptOrderDetailVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReceiptOrderDetailConvertImpl implements ReceiptOrderDetailConvert {
    public ReceiptOrderDetailConvertImpl() {
    }

    public ReceiptOrderDetailDTO do2dto(ReceiptOrderDetail source) {
        if (source == null) {
            return null;
        } else {
            ReceiptOrderDetailDTO receiptOrderDetailDTO = new ReceiptOrderDetailDTO();
            receiptOrderDetailDTO.setCreateBy(source.getCreateBy());
            receiptOrderDetailDTO.setCreateTime(source.getCreateTime());
            receiptOrderDetailDTO.setUpdateBy(source.getUpdateBy());
            receiptOrderDetailDTO.setUpdateTime(source.getUpdateTime());
            receiptOrderDetailDTO.setId(source.getId());
            receiptOrderDetailDTO.setReceiptOrderId(source.getReceiptOrderId());
            receiptOrderDetailDTO.setItemId(source.getItemId());
            receiptOrderDetailDTO.setPlanQuantity(source.getPlanQuantity());
            receiptOrderDetailDTO.setRealQuantity(source.getRealQuantity());
            receiptOrderDetailDTO.setRackId(source.getRackId());
            receiptOrderDetailDTO.setRemark(source.getRemark());
            receiptOrderDetailDTO.setWarehouseId(source.getWarehouseId());
            receiptOrderDetailDTO.setAreaId(source.getAreaId());
            receiptOrderDetailDTO.setReceiptOrderStatus(source.getReceiptOrderStatus());
            return receiptOrderDetailDTO;
        }
    }

    public ReceiptOrderDetail dto2do(ReceiptOrderDetailDTO source) {
        if (source == null) {
            return null;
        } else {
            ReceiptOrderDetail receiptOrderDetail = new ReceiptOrderDetail();
            receiptOrderDetail.setCreateBy(source.getCreateBy());
            receiptOrderDetail.setCreateTime(source.getCreateTime());
            receiptOrderDetail.setUpdateBy(source.getUpdateBy());
            receiptOrderDetail.setUpdateTime(source.getUpdateTime());
            receiptOrderDetail.setId(source.getId());
            receiptOrderDetail.setReceiptOrderId(source.getReceiptOrderId());
            receiptOrderDetail.setItemId(source.getItemId());
            receiptOrderDetail.setPlanQuantity(source.getPlanQuantity());
            receiptOrderDetail.setRealQuantity(source.getRealQuantity());
            receiptOrderDetail.setRackId(source.getRackId());
            receiptOrderDetail.setRemark(source.getRemark());
            receiptOrderDetail.setWarehouseId(source.getWarehouseId());
            receiptOrderDetail.setAreaId(source.getAreaId());
            receiptOrderDetail.setReceiptOrderStatus(source.getReceiptOrderStatus());
            return receiptOrderDetail;
        }
    }

    public ReceiptOrderDetailVO toVo(ReceiptOrderDetail source) {
        if (source == null) {
            return null;
        } else {
            ReceiptOrderDetailVO receiptOrderDetailVO = new ReceiptOrderDetailVO();
            receiptOrderDetailVO.setCreateBy(source.getCreateBy());
            receiptOrderDetailVO.setCreateTime(source.getCreateTime());
            receiptOrderDetailVO.setUpdateBy(source.getUpdateBy());
            receiptOrderDetailVO.setUpdateTime(source.getUpdateTime());
            receiptOrderDetailVO.setId(source.getId());
            receiptOrderDetailVO.setReceiptOrderId(source.getReceiptOrderId());
            receiptOrderDetailVO.setItemId(source.getItemId());
            receiptOrderDetailVO.setPlanQuantity(source.getPlanQuantity());
            receiptOrderDetailVO.setRealQuantity(source.getRealQuantity());
            receiptOrderDetailVO.setRackId(source.getRackId());
            receiptOrderDetailVO.setRemark(source.getRemark());
            receiptOrderDetailVO.setWarehouseId(source.getWarehouseId());
            receiptOrderDetailVO.setAreaId(source.getAreaId());
            receiptOrderDetailVO.setReceiptOrderStatus(source.getReceiptOrderStatus());
            receiptOrderDetailVO.setDelFlag(source.getDelFlag());
            return receiptOrderDetailVO;
        }
    }

    public List<ReceiptOrderDetailVO> dos2vos(List<ReceiptOrderDetail> list) {
        if (list == null) {
            return null;
        } else {
            List<ReceiptOrderDetailVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                ReceiptOrderDetail receiptOrderDetail = (ReceiptOrderDetail)var3.next();
                list1.add(this.toVo(receiptOrderDetail));
            }

            return list1;
        }
    }

    public List<ReceiptOrderDetail> vos2dos(List<ReceiptOrderDetailVO> details) {
        if (details == null) {
            return null;
        } else {
            List<ReceiptOrderDetail> list = new ArrayList(details.size());
            Iterator var3 = details.iterator();

            while(var3.hasNext()) {
                ReceiptOrderDetailVO receiptOrderDetailVO = (ReceiptOrderDetailVO)var3.next();
                list.add(this.receiptOrderDetailVOToReceiptOrderDetail(receiptOrderDetailVO));
            }

            return list;
        }
    }

    public InventoryHistory do2InventoryHistory(ReceiptOrderDetailVO it) {
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

    protected ReceiptOrderDetail receiptOrderDetailVOToReceiptOrderDetail(ReceiptOrderDetailVO receiptOrderDetailVO) {
        if (receiptOrderDetailVO == null) {
            return null;
        } else {
            ReceiptOrderDetail receiptOrderDetail = new ReceiptOrderDetail();
            receiptOrderDetail.setCreateBy(receiptOrderDetailVO.getCreateBy());
            receiptOrderDetail.setCreateTime(receiptOrderDetailVO.getCreateTime());
            receiptOrderDetail.setUpdateBy(receiptOrderDetailVO.getUpdateBy());
            receiptOrderDetail.setUpdateTime(receiptOrderDetailVO.getUpdateTime());
            receiptOrderDetail.setId(receiptOrderDetailVO.getId());
            receiptOrderDetail.setReceiptOrderId(receiptOrderDetailVO.getReceiptOrderId());
            receiptOrderDetail.setItemId(receiptOrderDetailVO.getItemId());
            receiptOrderDetail.setPlanQuantity(receiptOrderDetailVO.getPlanQuantity());
            receiptOrderDetail.setRealQuantity(receiptOrderDetailVO.getRealQuantity());
            receiptOrderDetail.setRackId(receiptOrderDetailVO.getRackId());
            receiptOrderDetail.setDelFlag(receiptOrderDetailVO.getDelFlag());
            receiptOrderDetail.setRemark(receiptOrderDetailVO.getRemark());
            receiptOrderDetail.setWarehouseId(receiptOrderDetailVO.getWarehouseId());
            receiptOrderDetail.setAreaId(receiptOrderDetailVO.getAreaId());
            receiptOrderDetail.setReceiptOrderStatus(receiptOrderDetailVO.getReceiptOrderStatus());
            return receiptOrderDetail;
        }
    }
}
*/
