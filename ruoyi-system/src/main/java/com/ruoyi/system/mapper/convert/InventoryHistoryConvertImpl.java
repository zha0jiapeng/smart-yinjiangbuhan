/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.pojo.dto.InventoryHistoryDTO;
import com.ruoyi.system.pojo.vo.InventoryHistoryVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InventoryHistoryConvertImpl implements InventoryHistoryConvert {
    public InventoryHistoryConvertImpl() {
    }

    public InventoryHistoryDTO do2dto(InventoryHistory source) {
        if (source == null) {
            return null;
        } else {
            InventoryHistoryDTO inventoryHistoryDTO = new InventoryHistoryDTO();
            inventoryHistoryDTO.setCreateBy(source.getCreateBy());
            inventoryHistoryDTO.setCreateTime(source.getCreateTime());
            inventoryHistoryDTO.setUpdateBy(source.getUpdateBy());
            inventoryHistoryDTO.setUpdateTime(source.getUpdateTime());
            inventoryHistoryDTO.setId(source.getId());
            inventoryHistoryDTO.setFormId(source.getFormId());
            inventoryHistoryDTO.setFormType(source.getFormType());
            inventoryHistoryDTO.setItemId(source.getItemId());
            inventoryHistoryDTO.setRackId(source.getRackId());
            inventoryHistoryDTO.setWarehouseId(source.getWarehouseId());
            inventoryHistoryDTO.setAreaId(source.getAreaId());
            inventoryHistoryDTO.setQuantity(source.getQuantity());
            inventoryHistoryDTO.setRemark(source.getRemark());
            return inventoryHistoryDTO;
        }
    }

    public InventoryHistory dto2do(InventoryHistoryDTO source) {
        if (source == null) {
            return null;
        } else {
            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setCreateBy(source.getCreateBy());
            inventoryHistory.setCreateTime(source.getCreateTime());
            inventoryHistory.setUpdateBy(source.getUpdateBy());
            inventoryHistory.setUpdateTime(source.getUpdateTime());
            inventoryHistory.setId(source.getId());
            inventoryHistory.setFormId(source.getFormId());
            inventoryHistory.setFormType(source.getFormType());
            inventoryHistory.setItemId(source.getItemId());
            inventoryHistory.setWarehouseId(source.getWarehouseId());
            inventoryHistory.setAreaId(source.getAreaId());
            inventoryHistory.setRackId(source.getRackId());
            inventoryHistory.setQuantity(source.getQuantity());
            inventoryHistory.setRemark(source.getRemark());
            return inventoryHistory;
        }
    }

    public List<InventoryHistoryVO> dos2vos(List<InventoryHistory> list) {
        if (list == null) {
            return null;
        } else {
            List<InventoryHistoryVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                InventoryHistory inventoryHistory = (InventoryHistory)var3.next();
                list1.add(this.inventoryHistoryToInventoryHistoryVO(inventoryHistory));
            }

            return list1;
        }
    }

    protected InventoryHistoryVO inventoryHistoryToInventoryHistoryVO(InventoryHistory inventoryHistory) {
        if (inventoryHistory == null) {
            return null;
        } else {
            InventoryHistoryVO inventoryHistoryVO = new InventoryHistoryVO();
            inventoryHistoryVO.setCreateBy(inventoryHistory.getCreateBy());
            inventoryHistoryVO.setCreateTime(inventoryHistory.getCreateTime());
            inventoryHistoryVO.setUpdateBy(inventoryHistory.getUpdateBy());
            inventoryHistoryVO.setUpdateTime(inventoryHistory.getUpdateTime());
            inventoryHistoryVO.setId(inventoryHistory.getId());
            inventoryHistoryVO.setFormId(inventoryHistory.getFormId());
            inventoryHistoryVO.setFormType(inventoryHistory.getFormType());
            inventoryHistoryVO.setItemId(inventoryHistory.getItemId());
            inventoryHistoryVO.setRackId(inventoryHistory.getRackId());
            inventoryHistoryVO.setWarehouseId(inventoryHistory.getWarehouseId());
            inventoryHistoryVO.setAreaId(inventoryHistory.getAreaId());
            inventoryHistoryVO.setQuantity(inventoryHistory.getQuantity());
            inventoryHistoryVO.setRemark(inventoryHistory.getRemark());
            return inventoryHistoryVO;
        }
    }
}
*/
