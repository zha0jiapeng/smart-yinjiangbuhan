package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Warehouse;
import com.ruoyi.system.pojo.dto.WarehouseDTO;
import com.ruoyi.system.pojo.vo.WarehouseVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 仓库  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface WarehouseConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    WarehouseDTO do2dto(Warehouse source);

    /**
     * @param source DTO
     * @return DO
     */
    Warehouse dto2do(WarehouseDTO source);

    List<WarehouseVO> dos2vos(List<Warehouse> list);
}
