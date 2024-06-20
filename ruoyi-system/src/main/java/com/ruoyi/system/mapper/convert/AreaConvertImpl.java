/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Area;
import com.ruoyi.system.pojo.dto.AreaDTO;
import com.ruoyi.system.pojo.vo.AreaVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AreaConvertImpl implements AreaConvert {
    public AreaConvertImpl() {
    }
    @Override
    public AreaDTO do2dto(Area source) {
        if (source == null) {
            return null;
        } else {
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setCreateBy(source.getCreateBy());
            areaDTO.setCreateTime(source.getCreateTime());
            areaDTO.setUpdateBy(source.getUpdateBy());
            areaDTO.setUpdateTime(source.getUpdateTime());
            areaDTO.setId(source.getId());
            areaDTO.setAreaNo(source.getAreaNo());
            areaDTO.setAreaName(source.getAreaName());
            areaDTO.setWarehouseId(source.getWarehouseId());
            areaDTO.setRemark(source.getRemark());
            return areaDTO;
        }
    }
    @Override
    public Area dto2do(AreaDTO source) {
        if (source == null) {
            return null;
        } else {
            Area area = new Area();
            area.setCreateBy(source.getCreateBy());
            area.setCreateTime(source.getCreateTime());
            area.setUpdateBy(source.getUpdateBy());
            area.setUpdateTime(source.getUpdateTime());
            area.setId(source.getId());
            area.setAreaNo(source.getAreaNo());
            area.setAreaName(source.getAreaName());
            area.setWarehouseId(source.getWarehouseId());
            area.setRemark(source.getRemark());
            return area;
        }
    }
    @Override
    public List<AreaVO> dos2vos(List<Area> list) {
        if (list == null) {
            return null;
        } else {
            List<AreaVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Area area = (Area)var3.next();
                list1.add(this.areaToAreaVO(area));
            }

            return list1;
        }
    }

    public AreaVO areaToAreaVO(Area area) {
        if (area == null) {
            return null;
        } else {
            AreaVO areaVO = new AreaVO();
            areaVO.setCreateBy(area.getCreateBy());
            areaVO.setCreateTime(area.getCreateTime());
            areaVO.setUpdateBy(area.getUpdateBy());
            areaVO.setUpdateTime(area.getUpdateTime());
            areaVO.setId(area.getId());
            areaVO.setAreaNo(area.getAreaNo());
            areaVO.setAreaName(area.getAreaName());
            areaVO.setWarehouseId(area.getWarehouseId());
            areaVO.setRemark(area.getRemark());
            return areaVO;
        }
    }
}
*/
