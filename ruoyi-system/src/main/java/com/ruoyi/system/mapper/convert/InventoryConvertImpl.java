/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.pojo.dto.InventoryDTO;
import com.ruoyi.system.pojo.vo.InventoryVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InventoryConvertImpl implements InventoryConvert {
    public InventoryConvertImpl() {
    }

    public InventoryDTO do2dto(Inventory source) {
        if (source == null) {
            return null;
        } else {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setCreateBy(source.getCreateBy());
            inventoryDTO.setCreateTime(source.getCreateTime());
            inventoryDTO.setUpdateBy(source.getUpdateBy());
            inventoryDTO.setUpdateTime(source.getUpdateTime());
            inventoryDTO.setId(source.getId());
            inventoryDTO.setItemId(source.getItemId());
            inventoryDTO.setWarehouseId(source.getWarehouseId());
            inventoryDTO.setAreaId(source.getAreaId());
            inventoryDTO.setRackId(source.getRackId());
            inventoryDTO.setQuantity(source.getQuantity());
            inventoryDTO.setRemark(source.getRemark());
            return inventoryDTO;
        }
    }

    public Inventory dto2do(InventoryDTO source) {
        if (source == null) {
            return null;
        } else {
            Inventory inventory = new Inventory();
            inventory.setCreateBy(source.getCreateBy());
            inventory.setCreateTime(source.getCreateTime());
            inventory.setUpdateBy(source.getUpdateBy());
            inventory.setUpdateTime(source.getUpdateTime());
            inventory.setId(source.getId());
            inventory.setItemId(source.getItemId());
            inventory.setWarehouseId(source.getWarehouseId());
            inventory.setAreaId(source.getAreaId());
            inventory.setRackId(source.getRackId());
            inventory.setQuantity(source.getQuantity());
            inventory.setRemark(source.getRemark());
            return inventory;
        }
    }

    public List<InventoryVO> dos2vos(List<Inventory> list) {
        if (list == null) {
            return null;
        } else {
            List<InventoryVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Inventory inventory = (Inventory)var3.next();
                list1.add(this.inventoryToInventoryVO(inventory));
            }

            return list1;
        }
    }

    public Inventory inventoryHistory2invertory(InventoryHistory it) {
        if (it == null) {
            return null;
        } else {
            Inventory inventory = new Inventory();
            inventory.setCreateBy(it.getCreateBy());
            inventory.setCreateTime(it.getCreateTime());
            inventory.setUpdateBy(it.getUpdateBy());
            inventory.setUpdateTime(it.getUpdateTime());
            inventory.setId(it.getId());
            inventory.setItemId(it.getItemId());
            inventory.setWarehouseId(it.getWarehouseId());
            inventory.setAreaId(it.getAreaId());
            inventory.setRackId(it.getRackId());
            inventory.setQuantity(it.getQuantity());
            inventory.setRemark(it.getRemark());
            inventory.setDelFlag(it.getDelFlag());
            return inventory;
        }
    }

    protected InventoryVO inventoryToInventoryVO(Inventory inventory) {
        if (inventory == null) {
            return null;
        } else {
            InventoryVO inventoryVO = new InventoryVO();
            inventoryVO.setCreateBy(inventory.getCreateBy());
            inventoryVO.setCreateTime(inventory.getCreateTime());
            inventoryVO.setUpdateBy(inventory.getUpdateBy());
            inventoryVO.setUpdateTime(inventory.getUpdateTime());
            inventoryVO.setId(inventory.getId());
            inventoryVO.setItemId(inventory.getItemId());
            inventoryVO.setRackId(inventory.getRackId());
            inventoryVO.setWarehouseId(inventory.getWarehouseId());
            inventoryVO.setAreaId(inventory.getAreaId());
            inventoryVO.setQuantity(inventory.getQuantity());
            inventoryVO.setRemark(inventory.getRemark());
            return inventoryVO;
        }
    }
}
*/
