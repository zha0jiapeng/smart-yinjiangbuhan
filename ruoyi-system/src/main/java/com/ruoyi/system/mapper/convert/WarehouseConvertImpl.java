/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Warehouse;
import com.ruoyi.system.pojo.dto.WarehouseDTO;
import com.ruoyi.system.pojo.vo.WarehouseVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WarehouseConvertImpl implements WarehouseConvert {
    public WarehouseConvertImpl() {
    }

    public WarehouseDTO do2dto(Warehouse source) {
        if (source == null) {
            return null;
        } else {
            WarehouseDTO warehouseDTO = new WarehouseDTO();
            warehouseDTO.setCreateBy(source.getCreateBy());
            warehouseDTO.setCreateTime(source.getCreateTime());
            warehouseDTO.setUpdateBy(source.getUpdateBy());
            warehouseDTO.setUpdateTime(source.getUpdateTime());
            warehouseDTO.setId(source.getId());
            warehouseDTO.setWarehouseNo(source.getWarehouseNo());
            warehouseDTO.setWarehouseName(source.getWarehouseName());
            warehouseDTO.setRemark(source.getRemark());
            return warehouseDTO;
        }
    }

    public Warehouse dto2do(WarehouseDTO source) {
        if (source == null) {
            return null;
        } else {
            Warehouse warehouse = new Warehouse();
            warehouse.setCreateBy(source.getCreateBy());
            warehouse.setCreateTime(source.getCreateTime());
            warehouse.setUpdateBy(source.getUpdateBy());
            warehouse.setUpdateTime(source.getUpdateTime());
            warehouse.setId(source.getId());
            warehouse.setWarehouseNo(source.getWarehouseNo());
            warehouse.setWarehouseName(source.getWarehouseName());
            warehouse.setRemark(source.getRemark());
            return warehouse;
        }
    }

    public List<WarehouseVO> dos2vos(List<Warehouse> list) {
        if (list == null) {
            return null;
        } else {
            List<WarehouseVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Warehouse warehouse = (Warehouse)var3.next();
                list1.add(this.warehouseToWarehouseVO(warehouse));
            }

            return list1;
        }
    }

    protected WarehouseVO warehouseToWarehouseVO(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        } else {
            WarehouseVO warehouseVO = new WarehouseVO();
            warehouseVO.setCreateBy(warehouse.getCreateBy());
            warehouseVO.setCreateTime(warehouse.getCreateTime());
            warehouseVO.setUpdateBy(warehouse.getUpdateBy());
            warehouseVO.setUpdateTime(warehouse.getUpdateTime());
            warehouseVO.setId(warehouse.getId());
            warehouseVO.setWarehouseNo(warehouse.getWarehouseNo());
            warehouseVO.setWarehouseName(warehouse.getWarehouseName());
            warehouseVO.setRemark(warehouse.getRemark());
            return warehouseVO;
        }
    }
}
*/
