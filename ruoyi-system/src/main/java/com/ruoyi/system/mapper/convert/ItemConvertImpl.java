/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Item;
import com.ruoyi.system.pojo.dto.ItemDTO;
import com.ruoyi.system.pojo.vo.ItemVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ItemConvertImpl implements ItemConvert {
    public ItemConvertImpl() {
    }
    @Override
    public ItemDTO do2dto(Item source) {
        if (source == null) {
            return null;
        } else {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCreateBy(source.getCreateBy());
            itemDTO.setCreateTime(source.getCreateTime());
            itemDTO.setUpdateBy(source.getUpdateBy());
            itemDTO.setUpdateTime(source.getUpdateTime());
            itemDTO.setId(source.getId());
            itemDTO.setItemNo(source.getItemNo());
            itemDTO.setItemName(source.getItemName());
            itemDTO.setItemType(source.getItemType());
            itemDTO.setUnit(source.getUnit());
            itemDTO.setRackId(source.getRackId());
            itemDTO.setAreaId(source.getAreaId());
            itemDTO.setWarehouseId(source.getWarehouseId());
            itemDTO.setQuantity(source.getQuantity());
            itemDTO.setExpiryDate(source.getExpiryDate());
            itemDTO.setRemark(source.getRemark());
            return itemDTO;
        }
    }

    public Item dto2do(ItemDTO source) {
        if (source == null) {
            return null;
        } else {
            Item item = new Item();
            item.setCreateBy(source.getCreateBy());
            item.setCreateTime(source.getCreateTime());
            item.setUpdateBy(source.getUpdateBy());
            item.setUpdateTime(source.getUpdateTime());
            item.setId(source.getId());
            item.setItemNo(source.getItemNo());
            item.setItemName(source.getItemName());
            item.setItemType(source.getItemType());
            item.setUnit(source.getUnit());
            item.setRackId(source.getRackId());
            item.setAreaId(source.getAreaId());
            item.setWarehouseId(source.getWarehouseId());
            item.setQuantity(source.getQuantity());
            item.setExpiryDate(source.getExpiryDate());
            item.setRemark(source.getRemark());
            return item;
        }
    }

    public ItemVO toVo(Item source) {
        if (source == null) {
            return null;
        } else {
            ItemVO itemVO = new ItemVO();
            itemVO.setCreateBy(source.getCreateBy());
            itemVO.setCreateTime(source.getCreateTime());
            itemVO.setUpdateBy(source.getUpdateBy());
            itemVO.setUpdateTime(source.getUpdateTime());
            itemVO.setId(source.getId());
            itemVO.setItemNo(source.getItemNo());
            itemVO.setItemName(source.getItemName());
            itemVO.setItemType(source.getItemType());
            itemVO.setUnit(source.getUnit());
            itemVO.setRackId(source.getRackId());
            itemVO.setAreaId(source.getAreaId());
            itemVO.setWarehouseId(source.getWarehouseId());
            itemVO.setQuantity(source.getQuantity());
            itemVO.setExpiryDate(source.getExpiryDate());
            itemVO.setRemark(source.getRemark());
            itemVO.setDelFlag(source.getDelFlag());
            return itemVO;
        }
    }

    public List<ItemVO> dos2vos(List<Item> list) {
        if (list == null) {
            return null;
        } else {
            List<ItemVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Item item = (Item)var3.next();
                list1.add(this.toVo(item));
            }

            return list1;
        }
    }
}
*/
