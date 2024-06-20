/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Rack;
import com.ruoyi.system.pojo.dto.RackDTO;
import com.ruoyi.system.pojo.vo.RackVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RackConvertImpl implements RackConvert {
    public RackConvertImpl() {
    }

    public RackDTO do2dto(Rack source) {
        if (source == null) {
            return null;
        } else {
            RackDTO rackDTO = new RackDTO();
            rackDTO.setCreateBy(source.getCreateBy());
            rackDTO.setCreateTime(source.getCreateTime());
            rackDTO.setUpdateBy(source.getUpdateBy());
            rackDTO.setUpdateTime(source.getUpdateTime());
            rackDTO.setId(source.getId());
            rackDTO.setRackNo(source.getRackNo());
            rackDTO.setRackName(source.getRackName());
            rackDTO.setAreaId(source.getAreaId());
            rackDTO.setWarehouseId(source.getWarehouseId());
            rackDTO.setRemark(source.getRemark());
            return rackDTO;
        }
    }

    public Rack dto2do(RackDTO source) {
        if (source == null) {
            return null;
        } else {
            Rack rack = new Rack();
            rack.setCreateBy(source.getCreateBy());
            rack.setCreateTime(source.getCreateTime());
            rack.setUpdateBy(source.getUpdateBy());
            rack.setUpdateTime(source.getUpdateTime());
            rack.setId(source.getId());
            rack.setRackNo(source.getRackNo());
            rack.setRackName(source.getRackName());
            rack.setAreaId(source.getAreaId());
            rack.setWarehouseId(source.getWarehouseId());
            rack.setRemark(source.getRemark());
            return rack;
        }
    }

    public List<RackVO> dos2vos(List<Rack> list) {
        if (list == null) {
            return null;
        } else {
            List<RackVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Rack rack = (Rack)var3.next();
                list1.add(this.rackToRackVO(rack));
            }

            return list1;
        }
    }

    protected RackVO rackToRackVO(Rack rack) {
        if (rack == null) {
            return null;
        } else {
            RackVO rackVO = new RackVO();
            rackVO.setCreateBy(rack.getCreateBy());
            rackVO.setCreateTime(rack.getCreateTime());
            rackVO.setUpdateBy(rack.getUpdateBy());
            rackVO.setUpdateTime(rack.getUpdateTime());
            rackVO.setId(rack.getId());
            rackVO.setRackNo(rack.getRackNo());
            rackVO.setRackName(rack.getRackName());
            rackVO.setAreaId(rack.getAreaId());
            rackVO.setWarehouseId(rack.getWarehouseId());
            rackVO.setRemark(rack.getRemark());
            return rackVO;
        }
    }
}
*/
